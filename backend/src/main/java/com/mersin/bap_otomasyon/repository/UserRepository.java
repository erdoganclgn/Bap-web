package com.mersin.bap_otomasyon.repository;

import com.mersin.bap_otomasyon.entity.User;
import com.mersin.bap_otomasyon.entity.enums.KullaniciRol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByBirimId(UUID birimId);
    List<User> findByRol(KullaniciRol rol);
}
