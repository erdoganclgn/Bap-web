package com.mersin.bap_otomasyon.service;

import com.mersin.bap_otomasyon.entity.BursaryPayment;
import com.mersin.bap_otomasyon.entity.ProjectResearcher;

import java.util.List;
import java.util.UUID;

public interface BursaryService {
    List<ProjectResearcher> getBursaryStudents(UUID projeId);
    ProjectResearcher addStudent(UUID projeId, UUID ogrenciId);
    void removeStudent(UUID projeId, UUID ogrenciId);
    BursaryPayment processPayment(UUID projeId, UUID bursiyerId, Integer ay, Integer yil);
    List<BursaryPayment> getPaymentHistory(UUID projeId);
    void checkBursaryEligibility(UUID ogrenciId);
    void checkBudgetLimit(UUID projeId);
}
