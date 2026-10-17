package com.mersin.bap_otomasyon.service;

import com.mersin.bap_otomasyon.dto.request.ProjectTypeCreateRequest;
import com.mersin.bap_otomasyon.dto.request.ProjectTypeUpdateRequest;
import com.mersin.bap_otomasyon.dto.response.ProjectTypeResponse;

import java.util.List;

public interface ProjectTypeService {
    List<ProjectTypeResponse> findAll();
    ProjectTypeResponse findByKod(String kod);
    ProjectTypeResponse create(ProjectTypeCreateRequest request);
    ProjectTypeResponse update(String kod, ProjectTypeUpdateRequest request);
    void delete(String kod);
}
