package com.mersin.bap_otomasyon.service;

import com.mersin.bap_otomasyon.entity.User;
import com.mersin.bap_otomasyon.entity.enums.KullaniciRol;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> findAll();
    User findById(UUID id);
    User findByEmail(String email);
    User create(User user);
    User update(UUID id, User user);
    void delete(UUID id);
    void banUser(UUID id, java.time.LocalDate yasakBitis);
    void unbanUser(UUID id);
    List<User> findByBirim(UUID birimId);
    List<User> findByRol(KullaniciRol rol);
}
