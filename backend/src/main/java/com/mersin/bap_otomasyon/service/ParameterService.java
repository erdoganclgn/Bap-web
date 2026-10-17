package com.mersin.bap_otomasyon.service;

import com.mersin.bap_otomasyon.entity.Parameter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParameterService {
    List<Parameter> findAll();
    List<Parameter> findByGrup(String grup);
    Optional<Parameter> findById(UUID id);
    Optional<Parameter> findByGrupAndAnahtar(String grup, String anahtar);
    String getValue(String grup, String anahtar);
    Parameter create(Parameter parameter);
    Parameter update(UUID id, Parameter parameter);
    void delete(UUID id);
    void invalidateCache(String grup);
}
