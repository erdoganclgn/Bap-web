package com.mersin.bap_otomasyon.service;

import com.mersin.bap_otomasyon.dto.request.ClosureCriteriaRequest;
import com.mersin.bap_otomasyon.dto.response.ClosureCriteriaResponse;

import java.util.List;
import java.util.UUID;

public interface ClosureCriteriaService {
    List<ClosureCriteriaResponse> findAll();
    ClosureCriteriaResponse findById(UUID id);
    ClosureCriteriaResponse findByOlcutNo(Integer olcutNo);
    ClosureCriteriaResponse create(ClosureCriteriaRequest request);
    ClosureCriteriaResponse update(UUID id, ClosureCriteriaRequest request);
    void delete(UUID id);
}