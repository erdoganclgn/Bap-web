package com.mersin.bap_otomasyon.service.impl;

import com.mersin.bap_otomasyon.dto.request.ResearcherRequest;
import com.mersin.bap_otomasyon.dto.response.ResearcherResponse;
import com.mersin.bap_otomasyon.dto.response.UserResearchersResponse;
import com.mersin.bap_otomasyon.entity.Project;
import com.mersin.bap_otomasyon.entity.ProjectResearcher;
import com.mersin.bap_otomasyon.entity.User;
import com.mersin.bap_otomasyon.entity.enums.ArastirmaciRol;
import com.mersin.bap_otomasyon.entity.enums.ProjeDurum;
import com.mersin.bap_otomasyon.exception.BusinessException;
import com.mersin.bap_otomasyon.repository.ProjectRepository;
import com.mersin.bap_otomasyon.repository.ProjectResearcherRepository;
import com.mersin.bap_otomasyon.repository.UserRepository;
import com.mersin.bap_otomasyon.service.ResearcherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ResearcherServiceImpl implements ResearcherService {

    private static final int MAX_ARASTIRMACI_PROJE = 5;
    private static final int MAX_BURSIYER_PROJE = 2;

    private static final List<ProjeDurum> AKTIF_DIS = List.of(
            ProjeDurum.Taslak, ProjeDurum.Reddedildi, ProjeDurum.Kapandi, ProjeDurum.Iptal
    );

    private final ProjectResearcherRepository projectResearcherRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ResearcherServiceImpl(ProjectResearcherRepository projectResearcherRepository,
                                 ProjectRepository projectRepository,
                                 UserRepository userRepository) {
        this.projectResearcherRepository = projectResearcherRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResearcherResponse> findByProjectId(UUID projeId) {
        projeYoksaHata(projeId);
        return projectResearcherRepository.findByProjectId(projeId).stream()
                .map(ResearcherResponse::from)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResearcherResponse> findByProjectIdAndRol(UUID projeId, ArastirmaciRol rol) {
        projeYoksaHata(projeId);
        return projectResearcherRepository.findByProjectId(projeId).stream()
                .filter(pr -> pr.getRol() == rol)
                .map(ResearcherResponse::from)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ResearcherResponse findById(UUID projeId, UUID id) {
        ProjectResearcher entity = findEntityInProject(projeId, id);
        return ResearcherResponse.from(entity);
    }

    @Override
    public ResearcherResponse create(UUID projeId, ResearcherRequest request) {
        Project project = projeBul(projeId);
        if (request.getUserId() == null) {
            throw new BusinessException("BAP-RES-002", "Araştırmacı userId zorunludur");
        }
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BusinessException("BAP-RES-002",
                        "Kullanıcı bulunamadı: " + request.getUserId()));
        ArastirmaciRol rol = parseRol(request.getRol());

        if (projectResearcherRepository.existsByProjectIdAndUserId(projeId, user.getId())) {
            throw new BusinessException("BAP-RES-004",
                    "Bu kullanıcı zaten proje ekibinde yer alıyor");
        }
        if (project.getYurutucu() != null && user.getId().equals(project.getYurutucu().getId())) {
            throw new BusinessException("BAP-RES-005",
                    "Proje yürütücüsü ayrıca araştırmacı olarak eklenemez");
        }

        validateRolKurallari(project, user, rol, null);

        ProjectResearcher researcher = new ProjectResearcher();
        researcher.setProject(project);
        researcher.setUser(user);
        researcher.setRol(rol);
        researcher.setBaslamaTarihi(request.getBaslamaTarihi() != null
                ? request.getBaslamaTarihi() : LocalDate.now());
        researcher.setBitisTarihi(request.getBitisTarihi());
        return ResearcherResponse.from(projectResearcherRepository.save(researcher));
    }

    @Override
    public ResearcherResponse update(UUID projeId, UUID id, ResearcherRequest request) {
        ProjectResearcher entity = findEntityInProject(projeId, id);
        Project project = entity.getProject();

        if (request.getRol() != null) {
            ArastirmaciRol yeniRol = parseRol(request.getRol());
            if (yeniRol != entity.getRol()) {
                validateRolKurallari(project, entity.getUser(), yeniRol, entity);
                entity.setRol(yeniRol);
            }
        }
        if (request.getBaslamaTarihi() != null) {
            entity.setBaslamaTarihi(request.getBaslamaTarihi());
        }
        if (request.getBitisTarihi() != null) {
            entity.setBitisTarihi(request.getBitisTarihi());
        }
        return ResearcherResponse.from(projectResearcherRepository.save(entity));
    }

    @Override
    public void delete(UUID projeId, UUID id) {
        ProjectResearcher entity = findEntityInProject(projeId, id);
        projectResearcherRepository.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResearchersResponse findByUserId(UUID kullaniciId) {
        if (!userRepository.existsById(kullaniciId)) {
            throw new BusinessException("BAP-RES-002", "Kullanıcı bulunamadı: " + kullaniciId);
        }
        List<ResearcherResponse> researchers = projectResearcherRepository.findByUserId(kullaniciId).stream()
                .map(ResearcherResponse::from)
                .toList();
        return new UserResearchersResponse(kullaniciId, researchers, countActiveByUserId(kullaniciId));
    }

    @Override
    @Transactional(readOnly = true)
    public long countActiveByUserId(UUID kullaniciId) {
        long count = 0;
        List<ProjectResearcher> gorrevleri = projectResearcherRepository.findByUserId(kullaniciId);
        for (ProjectResearcher pr : gorrevleri) {
            if (pr.getRol() != ArastirmaciRol.Arastirmaci) {
                continue;
            }
            Project p = pr.getProject();
            if (AKTIF_DIS.contains(p.getDurum())) {
                continue;
            }
            String kod = p.getProjectType() != null ? p.getProjectType().getKod() : null;
            if ("TP1".equals(kod) || "AP9".equals(kod)) {
                continue;
            }
            count++;
        }
        return count;
    }

    private void validateRolKurallari(Project project, User user, ArastirmaciRol rol,
                                      ProjectResearcher haric) {
        if (rol == ArastirmaciRol.Bursiyer) {
            boolean bursiyerOlabilir = project.getProjectType() != null
                    && Boolean.TRUE.equals(project.getProjectType().getBursiyerOlabilir());
            if (!bursiyerOlabilir) {
                throw new BusinessException("BAP-RES-006",
                        "Bu proje türünde bursiyer çalıştırılamaz");
            }
            long mevcutBursiyer = projectResearcherRepository.findByProjectId(project.getId()).stream()
                    .filter(pr -> pr.getRol() == ArastirmaciRol.Bursiyer)
                    .filter(pr -> haric == null || !pr.getId().equals(haric.getId()))
                    .count();
            if (mevcutBursiyer >= MAX_BURSIYER_PROJE) {
                throw new BusinessException("BAP-RES-007",
                        "Bir projede en fazla " + MAX_BURSIYER_PROJE + " bursiyer görevlendirilebilir");
            }
            boolean baskaProjedeBursiyer = projectResearcherRepository.findByUserId(user.getId()).stream()
                    .filter(pr -> pr.getRol() == ArastirmaciRol.Bursiyer)
                    .filter(pr -> haric == null || !pr.getId().equals(haric.getId()))
                    .anyMatch(pr -> !pr.getProject().getId().equals(project.getId()));
            if (baskaProjedeBursiyer) {
                throw new BusinessException("BAP-RES-008",
                        "Bir bursiyer aynı anda birden fazla projede görev alamaz");
            }
        } else if (rol == ArastirmaciRol.Arastirmaci) {
            if (countActiveByUserId(user.getId()) >= MAX_ARASTIRMACI_PROJE) {
                throw new BusinessException("BAP-RES-009",
                        "Aynı anda en fazla " + MAX_ARASTIRMACI_PROJE
                                + " araştırma projesinde araştırmacı olarak görev alınabilir");
            }
        }
    }

    private ArastirmaciRol parseRol(String rol) {
        if (rol == null || rol.isBlank()) {
            throw new BusinessException("BAP-RES-003", "Araştırmacı rolü zorunludur");
        }
        try {
            return Arrays.stream(ArastirmaciRol.values())
                    .filter(r -> r.name().equalsIgnoreCase(rol.trim()))
                    .findFirst()
                    .orElseThrow(() -> new BusinessException("BAP-RES-003",
                            "Geçersiz araştırmacı rolü: " + rol + " (beklenen: Arastirmaci, Bursiyer)"));
        } catch (Exception e) {
            throw new BusinessException("BAP-RES-003",
                    "Geçersiz araştırmacı rolü: " + rol + " (beklenen: Arastirmaci, Bursiyer)");
        }
    }

    private ProjectResearcher findEntityInProject(UUID projeId, UUID id) {
        ProjectResearcher entity = projectResearcherRepository.findById(id)
                .orElseThrow(() -> new BusinessException("BAP-RES-010",
                        "Proje araştırmacısı bulunamadı: " + id));
        if (!entity.getProject().getId().equals(projeId)) {
            throw new BusinessException("BAP-RES-010",
                    "Bu araştırmacı belirtilen projede kayıtlı değil");
        }
        return entity;
    }

    private Project projeBul(UUID projeId) {
        return projectRepository.findById(projeId)
                .orElseThrow(() -> new BusinessException("BAP-RES-001",
                        "Proje bulunamadı: " + projeId));
    }

    private void projeYoksaHata(UUID projeId) {
        if (!projectRepository.existsById(projeId)) {
            throw new BusinessException("BAP-RES-001", "Proje bulunamadı: " + projeId);
        }
    }
}