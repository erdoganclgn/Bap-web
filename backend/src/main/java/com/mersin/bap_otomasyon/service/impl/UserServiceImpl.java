package com.mersin.bap_otomasyon.service.impl;

import com.mersin.bap_otomasyon.entity.User;
import com.mersin.bap_otomasyon.entity.enums.KullaniciRol;
import com.mersin.bap_otomasyon.exception.BusinessException;
import com.mersin.bap_otomasyon.exception.ResourceNotFoundException;
import com.mersin.bap_otomasyon.repository.UserRepository;
import com.mersin.bap_otomasyon.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kullanici", id));
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Kullanici", email));
    }

    @Override
    public User create(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BusinessException("BAP-ADM-001", "Bu email ile kayitli kullanici var: " + user.getEmail());
        }
        if (user.getPasswordHash() == null || user.getPasswordHash().isBlank()) {
            throw new BusinessException("BAP-ADM-002", "Kullanici sifresi zorunludur");
        }
        return userRepository.save(user);
    }

    @Override
    public User update(UUID id, User user) {
        User existing = findById(id);
        existing.setAdSoyad(user.getAdSoyad());
        existing.setUnvan(user.getUnvan());
        existing.setBirim(user.getBirim());
        existing.setOrcid(user.getOrcid());
        existing.setResearcherId(user.getResearcherId());
        existing.setScopusId(user.getScopusId());
        existing.setGoogleScholar(user.getGoogleScholar());
        existing.setTelefon(user.getTelefon());
        existing.setApbsGuncel(user.getApbsGuncel());
        existing.setYoksisGuncel(user.getYoksisGuncel());
        existing.setAktif(user.getAktif());
        return userRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public void banUser(UUID id, LocalDate yasakBitis) {
        User user = findById(id);
        user.setYasakBitisTarihi(yasakBitis);
        userRepository.save(user);
    }

    @Override
    public void unbanUser(UUID id) {
        User user = findById(id);
        user.setYasakBitisTarihi(null);
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findByBirim(UUID birimId) {
        return userRepository.findByBirimId(birimId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findByRol(KullaniciRol rol) {
        return userRepository.findByRol(rol);
    }
}
