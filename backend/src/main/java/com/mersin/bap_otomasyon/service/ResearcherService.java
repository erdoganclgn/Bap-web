package com.mersin.bap_otomasyon.service;

import com.mersin.bap_otomasyon.dto.request.ResearcherRequest;
import com.mersin.bap_otomasyon.dto.response.ResearcherResponse;
import com.mersin.bap_otomasyon.dto.response.UserResearchersResponse;
import com.mersin.bap_otomasyon.entity.enums.ArastirmaciRol;

import java.util.List;
import java.util.UUID;

public interface ResearcherService {
    List<ResearcherResponse> findByProjectId(UUID projeId);

    List<ResearcherResponse> findByProjectIdAndRol(UUID projeId, ArastirmaciRol rol);

    ResearcherResponse findById(UUID projeId, UUID id);

    ResearcherResponse create(UUID projeId, ResearcherRequest request);

    ResearcherResponse update(UUID projeId, UUID id, ResearcherRequest request);

    void delete(UUID projeId, UUID id);

    UserResearchersResponse findByUserId(UUID kullaniciId);

    long countActiveByUserId(UUID kullaniciId);
}