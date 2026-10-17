package com.mersin.bap_otomasyon.repository;

import com.mersin.bap_otomasyon.entity.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParameterRepository extends JpaRepository<Parameter, UUID> {
    Optional<Parameter> findByGrupAndAnahtar(String grup, String anahtar);

    List<Parameter> findByGrup(String grup);

    List<Parameter> findByAktifTrue();
}
