package com.mersin.bap_otomasyon.service.impl;

import com.mersin.bap_otomasyon.entity.Parameter;
import com.mersin.bap_otomasyon.repository.ParameterRepository;
import com.mersin.bap_otomasyon.service.ParameterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ParameterServiceImpl implements ParameterService {

    private final ParameterRepository parameterRepository;

    public ParameterServiceImpl(ParameterRepository parameterRepository) {
        this.parameterRepository = parameterRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Parameter> findAll() {
        return parameterRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Parameter> findById(UUID id) {
        return parameterRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Parameter> findByGrup(String grup) {
        return parameterRepository.findByGrup(grup);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Parameter> findByGrupAndAnahtar(String grup, String anahtar) {
        return parameterRepository.findByGrupAndAnahtar(grup, anahtar);
    }

    @Override
    @Transactional(readOnly = true)
    public String getValue(String grup, String anahtar) {
        return parameterRepository.findByGrupAndAnahtar(grup, anahtar)
                .map(parameter -> parameter.getDeger())
                .orElseThrow(() -> new RuntimeException("Parametre bulunamadi: " + grup + "/" + anahtar));
    }

    @Override
    public Parameter create(Parameter parameter) {
        return parameterRepository.save(parameter);
    }

    @Override
    public Parameter update(UUID id, Parameter parameter) {
        Parameter existing = parameterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parametre bulunamadi: " + id));
        existing.setGrup(parameter.getGrup());
        existing.setAnahtar(parameter.getAnahtar());
        existing.setDeger(parameter.getDeger());
        existing.setAciklama(parameter.getAciklama());
        existing.setAktif(parameter.getAktif());
        return parameterRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        parameterRepository.deleteById(id);
    }

    @Override
    public void invalidateCache(String grup) {
        // Cache invalidation will be implemented with Redis
    }
}
