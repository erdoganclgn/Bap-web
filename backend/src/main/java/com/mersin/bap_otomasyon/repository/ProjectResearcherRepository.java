package com.mersin.bap_otomasyon.repository;

import com.mersin.bap_otomasyon.entity.ProjectResearcher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectResearcherRepository extends JpaRepository<ProjectResearcher, UUID> {
    List<ProjectResearcher> findByProjectId(UUID projeId);

    List<ProjectResearcher> findByUserId(UUID kullaniciId);

    Optional<ProjectResearcher> findByProjectIdAndUserId(UUID projeId, UUID kullaniciId);

    long countByUserId(UUID kullaniciId);

    boolean existsByProjectIdAndUserId(UUID projeId, UUID kullaniciId);
}
