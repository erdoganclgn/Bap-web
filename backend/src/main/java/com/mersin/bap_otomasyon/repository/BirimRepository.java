package com.mersin.bap_otomasyon.repository;

import com.mersin.bap_otomasyon.entity.Birim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BirimRepository extends JpaRepository<Birim, UUID> {
    Optional<Birim> findByKod(String kod);
    boolean existsByKod(String kod);
}
