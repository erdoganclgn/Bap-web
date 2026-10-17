package com.mersin.bap_otomasyon.service;

import com.mersin.bap_otomasyon.dto.request.BirimRequest;
import com.mersin.bap_otomasyon.dto.response.BirimResponse;

import java.util.List;
import java.util.UUID;

public interface BirimService {
    List<BirimResponse> findAll();
    BirimResponse findById(UUID id);
    BirimResponse findByKod(String kod);
    BirimResponse create(BirimRequest request);
    BirimResponse update(UUID id, BirimRequest request);
    void delete(UUID id);
}