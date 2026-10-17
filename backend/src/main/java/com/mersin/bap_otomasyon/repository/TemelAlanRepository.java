package com.mersin.bap_otomasyon.repository;

import com.mersin.bap_otomasyon.entity.TemelAlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TemelAlanRepository extends JpaRepository<TemelAlan, UUID> {
    Optional<TemelAlan> findByKod(String kod);
    boolean existsByKod(String kod);
}