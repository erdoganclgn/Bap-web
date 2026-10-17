package com.mersin.bap_otomasyon.service;

import com.mersin.bap_otomasyon.dto.request.AltAlanRequest;
import com.mersin.bap_otomasyon.dto.request.TemelAlanRequest;
import com.mersin.bap_otomasyon.dto.response.AltAlanResponse;
import com.mersin.bap_otomasyon.dto.response.TemelAlanResponse;

import java.util.List;
import java.util.UUID;

public interface TemelAlanService {
    List<TemelAlanResponse> findAll();
    TemelAlanResponse findById(UUID id);
    TemelAlanResponse findByKod(String kod);
    TemelAlanResponse create(TemelAlanRequest request);
    TemelAlanResponse update(UUID id, TemelAlanRequest request);
    void delete(UUID id);

    List<AltAlanResponse> findAltAlanlar(UUID temelAlanId);
    AltAlanResponse findAltAlan(UUID temelAlanId, UUID altAlanId);
    AltAlanResponse createAltAlan(UUID temelAlanId, AltAlanRequest request);
    AltAlanResponse updateAltAlan(UUID temelAlanId, UUID altAlanId, AltAlanRequest request);
    void deleteAltAlan(UUID temelAlanId, UUID altAlanId);
}