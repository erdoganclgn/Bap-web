package com.mersin.bap_otomasyon.service;

import com.mersin.bap_otomasyon.dto.request.CreateProjectRequest;
import com.mersin.bap_otomasyon.entity.Project;
import com.mersin.bap_otomasyon.entity.enums.ProjeDurum;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    List<Project> findAll();
    Project findById(UUID id);
    Project findByProjeNo(String projeNo);
    Project create(UUID userId, CreateProjectRequest request);
    Project update(UUID id, Project project);
    void delete(UUID id);
    Project submitForReview(UUID projectId);
    Project approveByExecutive(UUID projectId);
    Project reviewByUnit(UUID projectId, boolean approved, String reason);
    Project submitToCommission(UUID projectId);
    Project changeState(UUID projectId, ProjeDurum newState);
    List<Project> findByYurutucu(UUID yurutucuId);
    List<Project> findByDurum(ProjeDurum durum);
    long countActiveByYurutucu(UUID userId);
    boolean checkEszamanliLimit(UUID userId, String projectTypeKod);
    boolean checkCarprazKisit(UUID userId);
    long countKontenjanIstilaEden(UUID userId);
}
