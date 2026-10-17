package com.mersin.bap_otomasyon.service.impl;

import com.mersin.bap_otomasyon.dto.request.ClosureCriteriaRequest;
import com.mersin.bap_otomasyon.dto.response.ClosureCriteriaResponse;
import com.mersin.bap_otomasyon.entity.ClosureCriteria;
import com.mersin.bap_otomasyon.exception.BusinessException;
import com.mersin.bap_otomasyon.repository.ClosureCriteriaRepository;
import com.mersin.bap_otomasyon.repository.ProjectRepository;
import com.mersin.bap_otomasyon.service.ClosureCriteriaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ClosureCriteriaServiceImpl implements ClosureCriteriaService {

    private final ClosureCriteriaRepository closureCriteriaRepository;
    private final ProjectRepository projectRepository;

    public ClosureCriteriaServiceImpl(ClosureCriteriaRepository closureCriteriaRepository,
                                      ProjectRepository projectRepository) {
        this.closureCriteriaRepository = closureCriteriaRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClosureCriteriaResponse> findAll() {
        return closureCriteriaRepository.findAll().stream()
                .map(ClosureCriteriaResponse::from)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ClosureCriteriaResponse findById(UUID id) {
        return ClosureCriteriaResponse.from(findEntityById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public ClosureCriteriaResponse findByOlcutNo(Integer olcutNo) {
        ClosureCriteria entity = closureCriteriaRepository.findByOlcutNo(olcutNo)
                .orElseThrow(() -> new BusinessException("BAP-CLC-002",
                        "Kapatma olcutu bulunamadi: " + olcutNo));
        return ClosureCriteriaResponse.from(entity);
    }

    @Override
    public ClosureCriteriaResponse create(ClosureCriteriaRequest request) {
        if (request.getOlcutNo() != null
                && closureCriteriaRepository.findByOlcutNo(request.getOlcutNo()).isPresent()) {
            throw new BusinessException("BAP-CLC-001",
                    "Bu olcut no zaten kayitli: " + request.getOlcutNo());
        }

        ClosureCriteria entity = new ClosureCriteria();
        applyRequest(entity, request);
        return ClosureCriteriaResponse.from(closureCriteriaRepository.save(entity));
    }

    @Override
    public ClosureCriteriaResponse update(UUID id, ClosureCriteriaRequest request) {
        ClosureCriteria entity = findEntityById(id);

        if (request.getOlcutNo() != null
                && !request.getOlcutNo().equals(entity.getOlcutNo())
                && closureCriteriaRepository.findByOlcutNo(request.getOlcutNo()).isPresent()) {
            throw new BusinessException("BAP-CLC-001",
                    "Bu olcut no zaten kayitli: " + request.getOlcutNo());
        }

        applyRequest(entity, request);
        return ClosureCriteriaResponse.from(closureCriteriaRepository.save(entity));
    }

    @Override
    public void delete(UUID id) {
        ClosureCriteria entity = findEntityById(id);
        long kullanimSayisi = projectRepository.findAll().stream()
                .filter(p -> entity.getId().equals(
                        p.getKapatmaOlcult() != null ? p.getKapatmaOlcult().getId() : null))
                .count();
        if (kullanimSayisi > 0) {
            throw new BusinessException("BAP-CLC-003",
                    "Bu kapatma olcutu " + kullanimSayisi + " projede kullaniliyor, silinemez.");
        }
        closureCriteriaRepository.delete(entity);
    }

    private ClosureCriteria findEntityById(UUID id) {
        return closureCriteriaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("BAP-CLC-002",
                        "Kapatma olcutu bulunamadi: " + id));
    }

    private void applyRequest(ClosureCriteria entity, ClosureCriteriaRequest request) {
        if (request.getOlcutNo() != null) {
            entity.setOlcutNo(request.getOlcutNo());
        }
        if (request.getAd() != null) {
            entity.setAd(request.getAd());
        }
        if (request.getAciklama() != null) {
            entity.setAciklama(request.getAciklama());
        }
        if (request.getGecerliAlanGruplari() != null) {
            entity.setGecerliAlanGruplari(Arrays.stream(request.getGecerliAlanGruplari())
                    .map(s -> s.trim().toUpperCase())
                    .toArray(String[]::new));
        }
        if (request.getGecerliProjeTurleri() != null) {
            entity.setGecerliProjeTurleri(request.getGecerliProjeTurleri());
        }
        if (request.getAktif() != null) {
            entity.setAktif(request.getAktif());
        }
    }
}