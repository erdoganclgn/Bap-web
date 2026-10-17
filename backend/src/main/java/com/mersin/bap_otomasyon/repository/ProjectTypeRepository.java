package com.mersin.bap_otomasyon.repository;

import com.mersin.bap_otomasyon.entity.ProjectType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectTypeRepository extends JpaRepository<ProjectType, UUID> {
    Optional<ProjectType> findByKod(String kod);
    List<ProjectType> findAllByAktifTrue();
}
