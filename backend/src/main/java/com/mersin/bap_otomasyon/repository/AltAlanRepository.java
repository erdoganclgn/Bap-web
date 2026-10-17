package com.mersin.bap_otomasyon.repository;

import com.mersin.bap_otomasyon.entity.AltAlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AltAlanRepository extends JpaRepository<AltAlan, UUID> {
    List<AltAlan> findByTemelAlanId(UUID temelAlanId);
    List<AltAlan> findByTemelAlanIdAndAktifTrue(UUID temelAlanId);
    boolean existsByTemelAlanIdAndKod(UUID temelAlanId, String kod);
}
