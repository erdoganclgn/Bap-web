package com.mersin.bap_otomasyon.service.impl;

import com.mersin.bap_otomasyon.dto.request.BirimRequest;
import com.mersin.bap_otomasyon.dto.response.BirimResponse;
import com.mersin.bap_otomasyon.entity.Birim;
import com.mersin.bap_otomasyon.exception.BusinessException;
import com.mersin.bap_otomasyon.repository.BirimRepository;
import com.mersin.bap_otomasyon.repository.UserRepository;
import com.mersin.bap_otomasyon.service.BirimService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BirimServiceImpl implements BirimService {

    private final BirimRepository birimRepository;
    private final UserRepository userRepository;

    public BirimServiceImpl(BirimRepository birimRepository, UserRepository userRepository) {
        this.birimRepository = birimRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BirimResponse> findAll() {
        return birimRepository.findAll().stream()
                .map(BirimResponse::from)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BirimResponse findById(UUID id) {
        return BirimResponse.from(findEntityById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public BirimResponse findByKod(String kod) {
        Birim entity = birimRepository.findByKod(kod)
                .orElseThrow(() -> new BusinessException("BAP-BRM-002",
                        "Birim bulunamadi: " + kod));
        return BirimResponse.from(entity);
    }

    @Override
    public BirimResponse create(BirimRequest request) {
        if (request.getKod() == null || request.getKod().isBlank()) {
            throw new BusinessException("BAP-BRM-004", "Birim kodu zorunludur");
        }
        if (request.getAd() == null || request.getAd().isBlank()) {
            throw new BusinessException("BAP-BRM-004", "Birim adi zorunludur");
        }
        String kod = request.getKod().trim().toUpperCase();
        if (birimRepository.existsByKod(kod)) {
            throw new BusinessException("BAP-BRM-001", "Bu birim kodu zaten kayitli: " + kod);
        }

        Birim entity = new Birim();
        applyRequest(entity, request, kod);
        return BirimResponse.from(birimRepository.save(entity));
    }

    @Override
    public BirimResponse update(UUID id, BirimRequest request) {
        Birim entity = findEntityById(id);

        if (request.getKod() != null && !request.getKod().isBlank()) {
            String kod = request.getKod().trim().toUpperCase();
            if (!kod.equals(entity.getKod()) && birimRepository.existsByKod(kod)) {
                throw new BusinessException("BAP-BRM-001", "Bu birim kodu zaten kayitli: " + kod);
            }
            applyRequest(entity, request, kod);
        } else {
            applyRequest(entity, request, entity.getKod());
        }
        return BirimResponse.from(birimRepository.save(entity));
    }

    @Override
    public void delete(UUID id) {
        Birim entity = findEntityById(id);

        long kullaniciSayisi = userRepository.findByBirimId(id).size();
        if (kullaniciSayisi > 0) {
            throw new BusinessException("BAP-BRM-003",
                    "Bu birimde " + kullaniciSayisi + " kullanici var, silinemez.");
        }
        long altBirimSayisi = birimRepository.findAll().stream()
                .filter(b -> id.equals(b.getUstBirim() != null ? b.getUstBirim().getId() : null))
                .count();
        if (altBirimSayisi > 0) {
            throw new BusinessException("BAP-BRM-003",
                    "Bu birime bagli " + altBirimSayisi + " alt birim var, silinemez.");
        }

        birimRepository.delete(entity);
    }

    private void applyRequest(Birim entity, BirimRequest request, String kod) {
        entity.setAd(request.getAd() != null ? request.getAd() : entity.getAd());
        entity.setKod(kod);
        if (request.getUstBirimId() != null) {
            Birim ustBirim = birimRepository.findById(request.getUstBirimId())
                    .orElseThrow(() -> new BusinessException("BAP-BRM-002",
                            "Ust birim bulunamadi: " + request.getUstBirimId()));
            entity.setUstBirim(ustBirim);
        }
        if (request.getBirimTuru() != null) {
            entity.setBirimTuru(request.getBirimTuru());
        }
        if (request.getAktif() != null) {
            entity.setAktif(request.getAktif());
        }
    }

    private Birim findEntityById(UUID id) {
        return birimRepository.findById(id)
                .orElseThrow(() -> new BusinessException("BAP-BRM-002",
                        "Birim bulunamadi: " + id));
    }
}