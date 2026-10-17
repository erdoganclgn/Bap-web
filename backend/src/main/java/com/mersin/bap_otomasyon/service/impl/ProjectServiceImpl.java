package com.mersin.bap_otomasyon.service.impl;

import com.mersin.bap_otomasyon.dto.request.BudgetItemRequest;
import com.mersin.bap_otomasyon.dto.request.CreateProjectRequest;
import com.mersin.bap_otomasyon.dto.request.ResearcherRequest;
import com.mersin.bap_otomasyon.dto.request.ValidationError;
import com.mersin.bap_otomasyon.dto.response.ValidationResult;
import com.mersin.bap_otomasyon.entity.BudgetItem;
import com.mersin.bap_otomasyon.entity.ClosureCriteria;
import com.mersin.bap_otomasyon.entity.Project;
import com.mersin.bap_otomasyon.entity.ProjectResearcher;
import com.mersin.bap_otomasyon.entity.ProjectType;
import com.mersin.bap_otomasyon.entity.User;
import com.mersin.bap_otomasyon.entity.enums.ArastirmaciRol;
import com.mersin.bap_otomasyon.entity.enums.KalemKodu;
import com.mersin.bap_otomasyon.entity.enums.ProjeDurum;
import com.mersin.bap_otomasyon.exception.BusinessException;
import com.mersin.bap_otomasyon.repository.ClosureCriteriaRepository;
import com.mersin.bap_otomasyon.repository.ProjectRepository;
import com.mersin.bap_otomasyon.repository.ProjectResearcherRepository;
import com.mersin.bap_otomasyon.repository.ProjectTypeRepository;
import com.mersin.bap_otomasyon.repository.UserRepository;
import com.mersin.bap_otomasyon.service.ProjectService;
import com.mersin.bap_otomasyon.service.ProjectValidationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectTypeRepository projectTypeRepository;
    private final UserRepository userRepository;
    private final ProjectResearcherRepository projectResearcherRepository;
    private final ClosureCriteriaRepository closureCriteriaRepository;
    private final ProjectValidationService projectValidationService;

    private static final List<ProjeDurum> ACTIVE_EXCLUDED = List.of(
            ProjeDurum.Taslak, ProjeDurum.Reddedildi, ProjeDurum.Kapandi, ProjeDurum.Iptal
    );

    public ProjectServiceImpl(ProjectRepository projectRepository,
                              ProjectTypeRepository projectTypeRepository,
                              UserRepository userRepository,
                              ProjectResearcherRepository projectResearcherRepository,
                              ClosureCriteriaRepository closureCriteriaRepository,
                              ProjectValidationService projectValidationService) {
        this.projectRepository = projectRepository;
        this.projectTypeRepository = projectTypeRepository;
        this.userRepository = userRepository;
        this.projectResearcherRepository = projectResearcherRepository;
        this.closureCriteriaRepository = closureCriteriaRepository;
        this.projectValidationService = projectValidationService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Project findById(UUID id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proje bulunamadi: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Project findByProjeNo(String projeNo) {
        return projectRepository.findByProjeNo(projeNo)
                .orElseThrow(() -> new RuntimeException("Proje bulunamadi: " + projeNo));
    }

    @Override
    public Project create(UUID userId, CreateProjectRequest request) {
        ValidationResult validationResult = projectValidationService.validate(userId, request);
        if (!validationResult.isValid()) {
            ValidationError error = validationResult.getErrors().get(0);
            throw new BusinessException(error.getCode(), error.getMessage());
        }

        User yurutucu = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("BAP-PRJ-001", "Kullanici bulunamadi"));
        ProjectType projectType = projectTypeRepository.findByKod(request.getProjectTypeKod())
                .orElseThrow(() -> new BusinessException("BAP-PRJ-002", "Proje turu bulunamadi: " + request.getProjectTypeKod()));

        validateYurutucuUygunluk(yurutucu, projectType);
        validateEszamanliLimit(yurutucu.getId(), projectType);

        Project project = new Project();
        project.setBaslik(request.getBaslik());
        project.setOzet(request.getOzet());
        project.setAnahtarKelimeler(request.getAnahtarKelimeler());
        if (request.getTemelAlanGrubu() != null) {
            project.setTemelAlanGrubu(request.getTemelAlanGrubu().trim().toUpperCase());
        }
        project.setYurutucu(yurutucu);
        project.setProjectType(projectType);
        project.setDurum(ProjeDurum.Taslak);

        if (request.getKapatmaOlcutId() != null) {
            ClosureCriteria kapatmaOlcut = closureCriteriaRepository.findById(request.getKapatmaOlcutId())
                    .orElseThrow(() -> new BusinessException("BAP-PRJ-007",
                            "Kapatma olcutu bulunamadi: " + request.getKapatmaOlcutId()));
            project.setKapatmaOlcult(kapatmaOlcut);
        }

        BigDecimal butceToplam = BigDecimal.ZERO;
        if (request.getBudgetItems() != null) {
            for (BudgetItemRequest itemRequest : request.getBudgetItems()) {
                BudgetItem item = new BudgetItem();
                item.setProject(project);
                item.setKalemKodu(resolveKalemKodu(itemRequest.getKalemKodu()));
                item.setAciklama(itemRequest.getAciklama());
                item.setBirimFiyat(itemRequest.getBirimFiyat());
                item.setMiktar(itemRequest.getMiktar() != null ? itemRequest.getMiktar() : BigDecimal.ONE);
                item.setToplamTutar(item.getBirimFiyat().multiply(item.getMiktar()));
                item.setKdvOrani(itemRequest.getKdvOrani() != null ? itemRequest.getKdvOrani() : BigDecimal.ZERO);
                if (project.getBudgetItems() == null) {
                    project.setBudgetItems(new java.util.ArrayList<>());
                }
                project.getBudgetItems().add(item);
                butceToplam = butceToplam.add(item.getToplamTutar());
            }
        }
        project.setButceToplam(butceToplam);

        if (project.getProjeNo() == null) {
            project.setProjeNo(generateProjeNo());
        }

        Project saved = projectRepository.save(project);

        if (request.getResearchers() != null) {
            for (ResearcherRequest researcherRequest : request.getResearchers()) {
                ProjectResearcher researcher = new ProjectResearcher();
                researcher.setProject(saved);
                researcher.setUser(userRepository.findById(researcherRequest.getUserId()).orElse(null));
                researcher.setRol(ArastirmaciRol.valueOf(researcherRequest.getRol()));
                if (researcherRequest.getBaslamaTarihi() != null) {
                    researcher.setBaslamaTarihi(researcherRequest.getBaslamaTarihi());
                }
                researcher.setBitisTarihi(researcherRequest.getBitisTarihi());
                projectResearcherRepository.save(researcher);
            }
        }

        return saved;
    }

    private KalemKodu resolveKalemKodu(String str) {
        for (KalemKodu kod : KalemKodu.values()) {
            if (kod.getValue().equals(str) || kod.name().equals(str)) {
                return kod;
            }
        }
        throw new IllegalArgumentException("Gecersiz kalem kodu: " + str);
    }

    private void validateYurutucuUygunluk(User yurutucu, ProjectType projectType) {
        String kod = projectType.getKod();

        if (kod.equals("TP1") || kod.equals("TP2") || kod.equals("TP3")) {
            if (!isOgretimUyesi(yurutucu.getUnvan())) {
                throw new BusinessException("BAP-PRJ-003",
                        kod + " proje turu icin yurutucunun ogretim uyesi (Prof. / Doc. / Dr. Ogr. Uyesi) olmasi gerekir");
            }
        }
    }

    private boolean isOgretimUyesi(String unvan) {
        if (unvan == null || unvan.isBlank()) {
            return false;
        }
        String u = normalizeUnvan(unvan);
        return u.contains("profesor")
                || u.contains("prof. ")
                || u.contains("docent")
                || u.equals("doc")
                || u.startsWith("doc. ")
                || u.contains("doktor ogretim uyesi")
                || u.contains("dr. ogr. uye")
                || u.contains("ogr. uyesi");
    }

    private String normalizeUnvan(String unvan) {
        String s = unvan.toLowerCase();
        return s.replace("ç", "c")
                .replace("ğ", "g")
                .replace("ı", "i")
                .replace("ö", "o")
                .replace("ş", "s")
                .replace("ü", "u");
    }

    private void validateEszamanliLimit(UUID userId, ProjectType projectType) {
        long activeCount = projectRepository.countActiveByYurutucuId(userId, ACTIVE_EXCLUDED);
        if (activeCount >= projectType.getEszamanliLimit()) {
            throw new BusinessException("BAP-PRJ-006",
                    "Es zamanli proje limiti asildi. Aktif proje sayisi: " + activeCount
                            + ", limit: " + projectType.getEszamanliLimit());
        }
    }

    @Override
    public Project update(UUID id, Project project) {
        Project existing = findById(id);
        if (existing.getDurum() != ProjeDurum.Taslak) {
            throw new RuntimeException("Sadece taslak projeler guncellenebilir");
        }
        existing.setBaslik(project.getBaslik());
        existing.setOzet(project.getOzet());
        existing.setAnahtarKelimeler(project.getAnahtarKelimeler());
        existing.setTemelAlanGrubu(project.getTemelAlanGrubu());
        return projectRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        Project project = findById(id);
        if (project.getDurum() != ProjeDurum.Taslak) {
            throw new RuntimeException("Sadece taslak projeler silinebilir");
        }
        projectRepository.delete(project);
    }

    @Override
    public Project submitForReview(UUID projectId) {
        Project project = findById(projectId);
        if (project.getDurum() != ProjeDurum.Taslak) {
            throw new RuntimeException("Proje taslak durumunda degil");
        }
        project.setDurum(ProjeDurum.BasvuruYapildi);
        return projectRepository.save(project);
    }

    @Override
    public Project approveByExecutive(UUID projectId) {
        Project project = findById(projectId);
        if (project.getDurum() != ProjeDurum.BasvuruYapildi) {
            throw new RuntimeException("Proje yurutucu onayina hazir degil");
        }
        project.setDurum(ProjeDurum.Inceleniyor);
        return projectRepository.save(project);
    }

    @Override
    public Project reviewByUnit(UUID projectId, boolean approved, String reason) {
        Project project = findById(projectId);
        if (approved) {
            project.setDurum(ProjeDurum.Komisyonda);
        } else {
            project.setDurum(ProjeDurum.EksikIade);
        }
        return projectRepository.save(project);
    }

    @Override
    public Project submitToCommission(UUID projectId) {
        Project project = findById(projectId);
        project.setDurum(ProjeDurum.Komisyonda);
        return projectRepository.save(project);
    }

    @Override
    public Project changeState(UUID projectId, ProjeDurum newState) {
        Project project = findById(projectId);
        project.setDurum(newState);
        return projectRepository.save(project);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> findByYurutucu(UUID yurutucuId) {
        return projectRepository.findByYurutucuId(yurutucuId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> findByDurum(ProjeDurum durum) {
        return projectRepository.findByDurum(durum);
    }

    @Override
    @Transactional(readOnly = true)
    public long countActiveByYurutucu(UUID userId) {
        return projectRepository.countActiveByYurutucuId(userId, ACTIVE_EXCLUDED);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkEszamanliLimit(UUID userId, String projectTypeKod) {
        ProjectType projectType = projectTypeRepository.findByKod(projectTypeKod)
                .orElseThrow(() -> new RuntimeException("Proje turu bulunamadi"));
        long activeCount = projectRepository.countActiveByYurutucuId(userId, ACTIVE_EXCLUDED);
        return activeCount < projectType.getEszamanliLimit();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkCarprazKisit(UUID userId) {
        List<Project> activeProjects = projectRepository.findActiveByYurutucuId(userId, ACTIVE_EXCLUDED);
        return activeProjects.stream().noneMatch(p -> {
            String kod = p.getProjectType().getKod();
            return kod.startsWith("AP") && Integer.parseInt(kod.substring(2)) <= 4;
        });
    }

    @Override
    @Transactional(readOnly = true)
    public long countKontenjanIstilaEden(UUID userId) {
        return projectRepository.countContenantIstilaEdenByYurutucu(userId);
    }

    private String generateProjeNo() {
        String yil = String.valueOf(java.time.Year.now().getValue());
        String prefix = "BAP-" + yil + "-";
        int max = 0;
        for (String no : projectRepository.findAllProjeNos()) {
            if (no != null && no.startsWith(prefix)) {
                try {
                    max = Math.max(max, Integer.parseInt(no.substring(prefix.length())));
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return prefix + String.format("%03d", max + 1);
    }
}
