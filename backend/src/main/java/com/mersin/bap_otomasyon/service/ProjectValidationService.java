package com.mersin.bap_otomasyon.service;

import com.mersin.bap_otomasyon.dto.request.CreateProjectRequest;
import com.mersin.bap_otomasyon.dto.response.ValidationResult;

import java.util.UUID;

public interface ProjectValidationService {
    ValidationResult validate(UUID userId, CreateProjectRequest request);
}