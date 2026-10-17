package com.mersin.bap_otomasyon.service.impl;

import com.mersin.bap_otomasyon.entity.*;
import com.mersin.bap_otomasyon.entity.enums.ArastirmaciRol;
import com.mersin.bap_otomasyon.entity.enums.HakedisDurum;
import com.mersin.bap_otomasyon.repository.*;
import com.mersin.bap_otomasyon.service.BursaryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BursaryServiceImpl implements BursaryService {

    private final ProjectResearcherRepository projectResearcherRepository;
    private final BursaryPaymentRepository bursaryPaymentRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public BursaryServiceImpl(ProjectResearcherRepository projectResearcherRepository,
                              BursaryPaymentRepository bursaryPaymentRepository,
                              ProjectRepository projectRepository,
                              UserRepository userRepository) {
        this.projectResearcherRepository = projectResearcherRepository;
        this.bursaryPaymentRepository = bursaryPaymentRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResearcher> getBursaryStudents(UUID projeId) {
        return projectResearcherRepository.findByProjectId(projeId).stream()
                .filter(pr -> pr.getRol() == ArastirmaciRol.Bursiyer)
                .toList();
    }

    @Override
    public ProjectResearcher addStudent(UUID projeId, UUID ogrenciId) {
        Project project = projectRepository.findById(projeId)
                .orElseThrow(() -> new RuntimeException("Proje bulunamadi"));
        User student = userRepository.findById(ogrenciId)
                .orElseThrow(() -> new RuntimeException("Ogrenci bulunamadi"));

        if (!project.getProjectType().getBursiyerOlabilir()) {
            throw new RuntimeException("Bu proje turunde bursiyer calistirilamaz");
        }

        if (projectResearcherRepository.existsByProjectIdAndUserId(projeId, ogrenciId)) {
            throw new RuntimeException("Bu kullanici zaten projede kayitli");
        }

        ProjectResearcher pr = new ProjectResearcher();
        pr.setProject(project);
        pr.setUser(student);
        pr.setRol(ArastirmaciRol.Bursiyer);
        return projectResearcherRepository.save(pr);
    }

    @Override
    public void removeStudent(UUID projeId, UUID ogrenciId) {
        ProjectResearcher pr = projectResearcherRepository
                .findByProjectIdAndUserId(projeId, ogrenciId)
                .orElseThrow(() -> new RuntimeException("Bursiyer bulunamadi"));
        projectResearcherRepository.delete(pr);
    }

    @Override
    public BursaryPayment processPayment(UUID projeId, UUID bursiyerId, Integer ay, Integer yil) {
        if (bursaryPaymentRepository.findByBursiyerIdAndAyAndYil(bursiyerId, ay, yil).isPresent()) {
            throw new RuntimeException("Bu ay icin odeme zaten yapilmis");
        }

        BursaryPayment payment = new BursaryPayment();
        payment.setAy(ay);
        payment.setYil(yil);
        payment.setDurum(HakedisDurum.Beklemede);
        // Amounts would be calculated based on actual rules
        payment.setBrutTutar(BigDecimal.ZERO);
        payment.setSgkKesintisi(BigDecimal.ZERO);
        payment.setNetTutar(BigDecimal.ZERO);
        return bursaryPaymentRepository.save(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BursaryPayment> getPaymentHistory(UUID projeId) {
        return bursaryPaymentRepository.findByProjectId(projeId);
    }

    @Override
    @Transactional(readOnly = true)
    public void checkBursaryEligibility(UUID ogrenciId) {
        User student = userRepository.findById(ogrenciId)
                .orElseThrow(() -> new RuntimeException("Ogrenci bulunamadi"));
        if (student.getTezYasagi()) {
            throw new RuntimeException("Bu ogrencinin tez yasagi bulunuyor");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void checkBudgetLimit(UUID projeId) {
        Project project = projectRepository.findById(projeId)
                .orElseThrow(() -> new RuntimeException("Proje bulunamadi"));
        BigDecimal bursLimit = project.getButceToplam().multiply(new BigDecimal("0.30"));
        BigDecimal totalBurs = bursaryPaymentRepository.findByProjectId(projeId).stream()
                .map(payment -> payment.getBrutTutar())
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
        if (totalBurs.compareTo(bursLimit) > 0) {
            throw new RuntimeException("Burs butcesi limiti asildi. Limit: " + bursLimit);
        }
    }
}
