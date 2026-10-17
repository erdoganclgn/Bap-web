package com.mersin.bap_otomasyon.service.impl;

import com.mersin.bap_otomasyon.dto.request.AltAlanRequest;
import com.mersin.bap_otomasyon.dto.request.TemelAlanRequest;
import com.mersin.bap_otomasyon.dto.response.AltAlanResponse;
import com.mersin.bap_otomasyon.dto.response.TemelAlanResponse;
import com.mersin.bap_otomasyon.entity.AltAlan;
import com.mersin.bap_otomasyon.entity.TemelAlan;
import com.mersin.bap_otomasyon.exception.BusinessException;
import com.mersin.bap_otomasyon.repository.AltAlanRepository;
import com.mersin.bap_otomasyon.repository.ClosureCriteriaRepository;
import com.mersin.bap_otomasyon.repository.ProjectRepository;
import com.mersin.bap_otomasyon.repository.TemelAlanRepository;
import com.mersin.bap_otomasyon.service.TemelAlanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TemelAlanServiceImpl implements TemelAlanService {

    private final TemelAlanRepository temelAlanRepository;
    private final ProjectRepository projectRepository;
    private final ClosureCriteriaRepository closureCriteriaRepository;
    private final AltAlanRepository altAlanRepository;

    public TemelAlanServiceImpl(TemelAlanRepository temelAlanRepository,
                                ProjectRepository projectRepository,
                                ClosureCriteriaRepository closureCriteriaRepository,
                                AltAlanRepository altAlanRepository) {
        this.temelAlanRepository = temelAlanRepository;
        this.projectRepository = projectRepository;
        this.closureCriteriaRepository = closureCriteriaRepository;
        this.altAlanRepository = altAlanRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TemelAlanResponse> findAll() {
        return temelAlanRepository.findAll().stream()
                .map(TemelAlanResponse::from)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TemelAlanResponse findById(UUID id) {
        return TemelAlanResponse.from(findEntityById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public TemelAlanResponse findByKod(String kod) {
        TemelAlan entity = temelAlanRepository.findByKod(kod)
                .orElseThrow(() -> new BusinessException("BAP-TAL-002",
                        "Temel alan bulunamadi: " + kod));
        return TemelAlanResponse.from(entity);
    }

    @Override
    public TemelAlanResponse create(TemelAlanRequest request) {
        if (request.getKod() == null || request.getKod().isBlank()) {
            throw new BusinessException("BAP-TAL-004", "Alan kodu zorunludur");
        }
        if (request.getAd() == null || request.getAd().isBlank()) {
            throw new BusinessException("BAP-TAL-004", "Alan adi zorunludur");
        }
        String kod = request.getKod().trim().toUpperCase();
        if (temelAlanRepository.existsByKod(kod)) {
            throw new BusinessException("BAP-TAL-001", "Bu alan kodu zaten kayitli: " + kod);
        }

        TemelAlan entity = new TemelAlan();
        entity.setKod(kod);
        entity.setAd(request.getAd());
        entity.setAktif(request.getAktif() != null ? request.getAktif() : true);
        return TemelAlanResponse.from(temelAlanRepository.save(entity));
    }

    @Override
    public TemelAlanResponse update(UUID id, TemelAlanRequest request) {
        TemelAlan entity = findEntityById(id);

        if (request.getKod() != null && !request.getKod().isBlank()) {
            String kod = request.getKod().trim().toUpperCase();
            if (!kod.equals(entity.getKod()) && temelAlanRepository.existsByKod(kod)) {
                throw new BusinessException("BAP-TAL-001", "Bu alan kodu zaten kayitli: " + kod);
            }
            entity.setKod(kod);
        }
        if (request.getAd() != null) {
            entity.setAd(request.getAd());
        }
        if (request.getAktif() != null) {
            entity.setAktif(request.getAktif());
        }
        return TemelAlanResponse.from(temelAlanRepository.save(entity));
    }

    @Override
    public void delete(UUID id) {
        TemelAlan entity = findEntityById(id);
        String kod = entity.getKod();

        long projeKullanimi = projectRepository.findAll().stream()
                .filter(p -> p.getTemelAlanGrubu() != null && p.getTemelAlanGrubu().equals(kod))
                .count();
        if (projeKullanimi > 0) {
            throw new BusinessException("BAP-TAL-003",
                    "Bu temel alan " + projeKullanimi + " projede kullaniliyor, silinemez.");
        }

        boolean kriterKullanimi = closureCriteriaRepository.findAll().stream()
                .anyMatch(k -> k.getGecerliAlanGruplari() != null
                        && Arrays.stream(k.getGecerliAlanGruplari())
                                .anyMatch(g -> g.equals(kod)));
        if (kriterKullanimi) {
            throw new BusinessException("BAP-TAL-003",
                    "Bu temel alan kapatma kriterlerinde kullaniliyor, silinemez.");
        }

        temelAlanRepository.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AltAlanResponse> findAltAlanlar(UUID temelAlanId) {
        // Üst temel alanın varlığını doğrula (yoksa hata fırlatır)
        findEntityById(temelAlanId);
        return altAlanRepository.findByTemelAlanId(temelAlanId).stream()
                .map(AltAlanResponse::from)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AltAlanResponse findAltAlan(UUID temelAlanId, UUID altAlanId) {
        return AltAlanResponse.from(findAltAlanEntity(temelAlanId, altAlanId));
    }

    @Override
    public AltAlanResponse createAltAlan(UUID temelAlanId, AltAlanRequest request) {
        if (request.getKod() == null || request.getKod().isBlank()) {
            throw new BusinessException("BAP-TAL-004", "Alt alan kodu zorunludur");
        }
        if (request.getAd() == null || request.getAd().isBlank()) {
            throw new BusinessException("BAP-TAL-004", "Alt alan adi zorunludur");
        }
        TemelAlan temelAlan = findEntityById(temelAlanId);
        String kod = request.getKod().trim().toUpperCase();
        if (altAlanRepository.existsByTemelAlanIdAndKod(temelAlanId, kod)) {
            throw new BusinessException("BAP-TAL-001", "Bu temel alana ait alt alan kodu zaten kayitli: " + kod);
        }

        AltAlan entity = new AltAlan();
        entity.setTemelAlan(temelAlan);
        entity.setKod(kod);
        entity.setAd(request.getAd());
        entity.setAktif(request.getAktif() != null ? request.getAktif() : true);
        return AltAlanResponse.from(altAlanRepository.save(entity));
    }

    @Override
    public AltAlanResponse updateAltAlan(UUID temelAlanId, UUID altAlanId, AltAlanRequest request) {
        AltAlan entity = findAltAlanEntity(temelAlanId, altAlanId);

        if (request.getKod() != null && !request.getKod().isBlank()) {
            String kod = request.getKod().trim().toUpperCase();
            if (!kod.equals(entity.getKod())
                    && altAlanRepository.existsByTemelAlanIdAndKod(temelAlanId, kod)) {
                throw new BusinessException("BAP-TAL-001", "Bu temel alana ait alt alan kodu zaten kayitli: " + kod);
            }
            entity.setKod(kod);
        }
        if (request.getAd() != null) {
            entity.setAd(request.getAd());
        }
        if (request.getAktif() != null) {
            entity.setAktif(request.getAktif());
        }
        return AltAlanResponse.from(altAlanRepository.save(entity));
    }

    @Override
    public void deleteAltAlan(UUID temelAlanId, UUID altAlanId) {
        AltAlan entity = findAltAlanEntity(temelAlanId, altAlanId);
        altAlanRepository.delete(entity);
    }

    private AltAlan findAltAlanEntity(UUID temelAlanId, UUID altAlanId) {
        findEntityById(temelAlanId);
        return altAlanRepository.findById(altAlanId)
                .filter(a -> a.getTemelAlan() != null && a.getTemelAlan().getId().equals(temelAlanId))
                .orElseThrow(() -> new BusinessException("BAP-TAL-002",
                        "Alt alan bulunamadi: " + altAlanId + " (temel alan: " + temelAlanId + ")"));
    }

    private TemelAlan findEntityById(UUID id) {
        return temelAlanRepository.findById(id)
                .orElseThrow(() -> new BusinessException("BAP-TAL-002",
                        "Temel alan bulunamadi: " + id));
    }
}