package com.mersin.bap_otomasyon.service.impl;

import com.mersin.bap_otomasyon.dto.request.BudgetItemRequest;
import com.mersin.bap_otomasyon.dto.request.CreateProjectRequest;
import com.mersin.bap_otomasyon.dto.request.ResearcherRequest;
import com.mersin.bap_otomasyon.dto.request.ValidationError;
import com.mersin.bap_otomasyon.dto.response.ValidationResult;
import com.mersin.bap_otomasyon.entity.ClosureCriteria;
import com.mersin.bap_otomasyon.entity.ProjectType;
import com.mersin.bap_otomasyon.entity.User;
import com.mersin.bap_otomasyon.entity.enums.KalemKodu;
import com.mersin.bap_otomasyon.repository.ClosureCriteriaRepository;
import com.mersin.bap_otomasyon.repository.ProjectTypeRepository;
import com.mersin.bap_otomasyon.repository.TemelAlanRepository;
import com.mersin.bap_otomasyon.repository.UserRepository;
import com.mersin.bap_otomasyon.service.ProjectValidationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ProjectValidationServiceImpl implements ProjectValidationService {

    private final UserRepository userRepository;
    private final ProjectTypeRepository projectTypeRepository;
    private final ClosureCriteriaRepository closureCriteriaRepository;
    private final TemelAlanRepository temelAlanRepository;

    private static final List<String> OGRETIM_UYESI_UNVANLAR = List.of(
            "profesor", "prof. ", "docent", "doc. ", "doktor ogretim uyesi", "dr. ogr. uye", "ogr. uyesi"
    );

    public ProjectValidationServiceImpl(UserRepository userRepository,
                                        ProjectTypeRepository projectTypeRepository,
                                        ClosureCriteriaRepository closureCriteriaRepository,
                                        TemelAlanRepository temelAlanRepository) {
        this.userRepository = userRepository;
        this.projectTypeRepository = projectTypeRepository;
        this.closureCriteriaRepository = closureCriteriaRepository;
        this.temelAlanRepository = temelAlanRepository;
    }

    @Override
    public ValidationResult validate(UUID userId, CreateProjectRequest request) {
        ValidationResult result = new ValidationResult();

        User yurutucu = userRepository.findById(userId).orElse(null);
        if (yurutucu == null) {
            result.addError(new ValidationError("V-PRJ-100", "yurutucuId", "Yurutucu bulunamadi"));
        }

        ProjectType projectType = projectTypeRepository.findByKod(request.getProjectTypeKod()).orElse(null);
        if (projectType == null) {
            result.addError(new ValidationError("V-PRJ-101", "projectTypeKod", "Proje turu bulunamadi: " + request.getProjectTypeKod()));
        }

        if (yurutucu != null && projectType != null) {
            validateYurutucuUygunluk(yurutucu, projectType, result);
        }

        validateTemelAlanGrubu(request, result);
        validateAnahtarKelimeler(request, result);
        validateBudget(request, projectType, result);
        validateResearchers(request, result);
        validateKapatmaKriteri(request, projectType, result);

        result.setValid(result.getErrors().isEmpty());
        return result;
    }

    private void validateYurutucuUygunluk(User yurutucu, ProjectType projectType, ValidationResult result) {
        String kod = projectType.getKod();
        if (!kod.equals("TP1") && !kod.equals("TP2") && !kod.equals("TP3")) {
            return;
        }
        if (!isOgretimUyesi(yurutucu.getUnvan())) {
            result.addError(new ValidationError("V-PRJ-100", "yurutucu",
                    kod + " proje turu icin yurutucunun ogretim uyesi (Prof. / Doc. / Dr. Ogr. Uyesi) olmasi gerekir"));
        }
    }

    private boolean isOgretimUyesi(String unvan) {
        if (unvan == null || unvan.isBlank()) {
            return false;
        }
        String u = normalizeUnvan(unvan);
        return OGRETIM_UYESI_UNVANLAR.stream().anyMatch(u::contains);
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

    private void validateTemelAlanGrubu(CreateProjectRequest request, ValidationResult result) {
        String alan = request.getTemelAlanGrubu();
        if (alan == null || alan.isBlank()) {
            result.addError(new ValidationError("V-PRJ-102", "temelAlanGrubu", "Temel alan grubu zorunludur"));
            return;
        }
        String kod = alan.trim().toUpperCase();
        boolean varVeAktif = temelAlanRepository.findByKod(kod)
                .map(k -> !Boolean.FALSE.equals(k.getAktif()))
                .orElse(false);
        if (!varVeAktif) {
            result.addError(new ValidationError("V-PRJ-102", "temelAlanGrubu",
                    "Gecersiz veya pasif temel alan grubu: " + alan));
        }
    }

    private void validateAnahtarKelimeler(CreateProjectRequest request, ValidationResult result) {
        String[] kelimeler = request.getAnahtarKelimeler();
        if (kelimeler == null || kelimeler.length == 0) {
            result.addError(new ValidationError("V-PRJ-103", "anahtarKelimeler", "En az bir anahtar kelime zorunludur"));
        }
    }

    private void validateBudget(CreateProjectRequest request, ProjectType projectType, ValidationResult result) {
        List<BudgetItemRequest> items = request.getBudgetItems();
        if (items == null || items.isEmpty()) {
            result.addError(new ValidationError("V-PRJ-104", "budgetItems", "En az bir butce kalemi zorunludur"));
            return;
        }

        BigDecimal toplam = BigDecimal.ZERO;
        Set<String> gorulenKodlar = new HashSet<>();
        for (BudgetItemRequest item : items) {
            String kodStr = item.getKalemKodu();
            if (kodStr == null || kodStr.isBlank()) {
                result.addError(new ValidationError("V-PRJ-105", "budgetItems", "Kalem kodu zorunludur"));
                continue;
            }
            boolean gecerli = Arrays.stream(KalemKodu.values())
                    .anyMatch(k -> k.getValue().equals(kodStr) || k.name().equals(kodStr));
            if (!gecerli) {
                result.addError(new ValidationError("V-PRJ-105", "budgetItems", "Gecersiz kalem kodu: " + kodStr));
                continue;
            }
            if (!gorulenKodlar.add(kodStr)) {
                result.addError(new ValidationError("V-PRJ-105", "budgetItems", "Ayni kalem kodu birden fazla kez tanimlanamaz: " + kodStr));
            }
            if (item.getBirimFiyat() == null || item.getBirimFiyat().signum() <= 0) {
                result.addError(new ValidationError("V-PRJ-105", "budgetItems", "Birim fiyat pozitif olmalidir"));
                continue;
            }
            BigDecimal miktar = item.getMiktar() != null ? item.getMiktar() : BigDecimal.ONE;
            if (miktar.signum() <= 0) {
                result.addError(new ValidationError("V-PRJ-105", "budgetItems", "Miktar pozitif olmalidir"));
                continue;
            }
            toplam = toplam.add(item.getBirimFiyat().multiply(miktar));
        }

        if (projectType != null && toplam.compareTo(projectType.getButceTavani()) > 0) {
            result.addError(new ValidationError("V-PRJ-106", "budgetItems",
                    "Butce toplami proje turunun tavanini asiyor. Toplam: " + toplam
                            + ", tavan: " + projectType.getButceTavani()));
        }
    }

    private void validateResearchers(CreateProjectRequest request, ValidationResult result) {
        Set<UUID> gorulenler = new HashSet<>();
        if (request.getResearchers() != null) {
            for (ResearcherRequest r : request.getResearchers()) {
                if (r.getUserId() == null) {
                    result.addError(new ValidationError("V-PRJ-108", "researchers", "Arastirmaci userId zorunludur"));
                    continue;
                }
                if (!gorulenler.add(r.getUserId())) {
                    result.addError(new ValidationError("V-PRJ-108", "researchers",
                            "Ayni arastirmaci birden fazla kez eklenemez: " + r.getUserId()));
                    continue;
                }
                boolean var = userRepository.existsById(r.getUserId());
                if (!var) {
                    result.addError(new ValidationError("V-PRJ-108", "researchers",
                            "Arastirmaci bulunamadi: " + r.getUserId()));
                }
                String rol = r.getRol();
                if (rol == null || (!rol.equals("Arastirmaci") && !rol.equals("Bursiyer"))) {
                    result.addError(new ValidationError("V-PRJ-108", "researchers", "Gecersiz arastirmaci rolu: " + rol));
                }
            }
        }
    }

    private void validateKapatmaKriteri(CreateProjectRequest request, ProjectType projectType, ValidationResult result) {
        if (projectType == null || request.getTemelAlanGrubu() == null) {
            return;
        }
        String alan = request.getTemelAlanGrubu().trim().toUpperCase();

        if (request.getKapatmaOlcutId() != null) {
            ClosureCriteria secili = closureCriteriaRepository.findById(request.getKapatmaOlcutId()).orElse(null);
            if (secili == null) {
                result.addError(new ValidationError("V-PRJ-109", "kapatmaOlcutId",
                        "Secilen kapatma olcutu bulunamadi: " + request.getKapatmaOlcutId()));
                return;
            }
            if (Boolean.FALSE.equals(secili.getAktif())) {
                result.addError(new ValidationError("V-PRJ-109", "kapatmaOlcutId",
                        "Secilen kapatma olcutu pasif: " + secili.getAd()));
            }
            if (!contains(secili.getGecerliProjeTurleri(), projectType.getKod())) {
                result.addError(new ValidationError("V-PRJ-109", "kapatmaOlcutId",
                        "Secilen kapatma olcutu '" + secili.getAd() + "' bu proje turu (" + projectType.getKod()
                                + ") icin gecerli degil"));
            }
            if (!containsStr(secili.getGecerliAlanGruplari(), alan)) {
                result.addError(new ValidationError("V-PRJ-109", "kapatmaOlcutId",
                        "Secilen kapatma olcutu '" + secili.getAd() + "' bu alan grubu (" + alan
                                + ") icin gecerli degil"));
            }
            return;
        }

        List<ClosureCriteria> kriterler = closureCriteriaRepository.findAll();
        boolean eslesmeVar = kriterler.stream().anyMatch(k -> !Boolean.FALSE.equals(k.getAktif())
                && contains(k.getGecerliProjeTurleri(), projectType.getKod())
                && containsStr(k.getGecerliAlanGruplari(), alan));
        if (!eslesmeVar) {
            result.addError(new ValidationError("V-PRJ-107", "temelAlanGrubu",
                    "Bu proje turu ve alan grubu icin gecerli bir kapatma kriteri tanimli degil. Proje kapanamaz."));
        }
    }

    private boolean contains(String[] arr, String value) {
        return arr != null && Arrays.asList(arr).contains(value);
    }

    private boolean containsStr(Object[] arr, String value) {
        if (arr == null) {
            return false;
        }
        for (Object o : arr) {
            if (value.equals(String.valueOf(o))) {
                return true;
            }
        }
        return false;
    }
}