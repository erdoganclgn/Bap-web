package com.mersin.bap_otomasyon.repository;

import com.mersin.bap_otomasyon.entity.ClosureCriteria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClosureCriteriaRepository extends JpaRepository<ClosureCriteria, UUID> {
    Optional<ClosureCriteria> findByOlcutNo(Integer olcutNo);
}
