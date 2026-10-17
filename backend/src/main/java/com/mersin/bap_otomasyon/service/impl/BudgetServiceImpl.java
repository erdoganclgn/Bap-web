package com.mersin.bap_otomasyon.service.impl;

import com.mersin.bap_otomasyon.entity.*;
import com.mersin.bap_otomasyon.entity.enums.KalemKodu;
import com.mersin.bap_otomasyon.entity.enums.TransferDurumu;
import com.mersin.bap_otomasyon.repository.*;
import com.mersin.bap_otomasyon.service.BudgetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BudgetServiceImpl implements BudgetService {

    private final BudgetItemRepository budgetItemRepository;
    private final BudgetTransferRepository budgetTransferRepository;
    private final ProjectRepository projectRepository;

    public BudgetServiceImpl(BudgetItemRepository budgetItemRepository,
                             BudgetTransferRepository budgetTransferRepository,
                             ProjectRepository projectRepository) {
        this.budgetItemRepository = budgetItemRepository;
        this.budgetTransferRepository = budgetTransferRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BudgetItem> getItems(UUID projeId) {
        return budgetItemRepository.findByProjectId(projeId);
    }

    @Override
    public BudgetItem addItem(UUID projeId, BudgetItem item) {
        Project project = projectRepository.findById(projeId)
                .orElseThrow(() -> new RuntimeException("Proje bulunamadi"));
        item.setProject(project);
        item.setToplamTutar(item.getBirimFiyat().multiply(item.getMiktar()));
        return budgetItemRepository.save(item);
    }

    @Override
    public BudgetItem updateItem(UUID itemId, BudgetItem item) {
        BudgetItem existing = budgetItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Butce kalemi bulunamadi"));
        existing.setKalemKodu(item.getKalemKodu());
        existing.setAciklama(item.getAciklama());
        existing.setBirimFiyat(item.getBirimFiyat());
        existing.setMiktar(item.getMiktar());
        existing.setKdvOrani(item.getKdvOrani());
        existing.setToplamTutar(item.getBirimFiyat().multiply(item.getMiktar()));
        existing.setOnayliMi(item.getOnayliMi());
        return budgetItemRepository.save(existing);
    }

    @Override
    public void removeItem(UUID itemId) {
        budgetItemRepository.deleteById(itemId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BudgetTransfer> getTransfers(UUID projeId) {
        return budgetTransferRepository.findByProjectId(projeId);
    }

    @Override
    public BudgetTransfer requestTransfer(UUID projeId, UUID kaynakId, UUID hedefId, BigDecimal tutar, String gerekce) {
        BudgetItem kaynak = budgetItemRepository.findById(kaynakId)
                .orElseThrow(() -> new RuntimeException("Kaynak kalem bulunamadi"));
        BudgetItem hedef = budgetItemRepository.findById(hedefId)
                .orElseThrow(() -> new RuntimeException("Hedef kalem bulunamadi"));

        if (isTransferForbidden(kaynak.getKalemKodu()) || isTransferForbidden(hedef.getKalemKodu())) {
            throw new RuntimeException("Bu kaleme/kalemden aktarim yasaktir");
        }

        if (kaynak.getToplamTutar().compareTo(tutar) < 0) {
            throw new RuntimeException("Kaynak kalemde yeterli butce yok");
        }

        Project project = projectRepository.findById(projeId)
                .orElseThrow(() -> new RuntimeException("Proje bulunamadi"));

        BudgetTransfer transfer = new BudgetTransfer();
        transfer.setProject(project);
        transfer.setKaynakKalem(kaynak);
        transfer.setHedefKalem(hedef);
        transfer.setTutar(tutar);
        transfer.setGerekce(gerekce);
        transfer.setDurum(TransferDurumu.Beklemede);
        return budgetTransferRepository.save(transfer);
    }

    @Override
    public BudgetTransfer approveTransfer(UUID transferId) {
        BudgetTransfer transfer = budgetTransferRepository.findById(transferId)
                .orElseThrow(() -> new RuntimeException("Aktarim bulunamadi"));
        transfer.setDurum(TransferDurumu.Onaylandi);
        return budgetTransferRepository.save(transfer);
    }

    @Override
    public BudgetTransfer rejectTransfer(UUID transferId, String reason) {
        BudgetTransfer transfer = budgetTransferRepository.findById(transferId)
                .orElseThrow(() -> new RuntimeException("Aktarim bulunamadi"));
        transfer.setDurum(TransferDurumu.Reddedildi);
        return budgetTransferRepository.save(transfer);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getRemainingBudget(UUID projeId) {
        Project project = projectRepository.findById(projeId)
                .orElseThrow(() -> new RuntimeException("Proje bulunamadi"));
        return project.getButceToplam().subtract(project.getButceHarcanan());
    }

    @Override
    @Transactional(readOnly = true)
    public void validateBudgetSufficiency(UUID projeId, BigDecimal amount) {
        BigDecimal remaining = getRemainingBudget(projeId);
        if (remaining.compareTo(amount) < 0) {
            throw new RuntimeException("Yetersiz butce. Kalan: " + remaining + ", Talep: " + amount);
        }
    }

    private boolean isTransferForbidden(KalemKodu kalemKodu) {
        return kalemKodu == KalemKodu.KOD_03_3
                || kalemKodu == KalemKodu.Burs
                || kalemKodu == KalemKodu.AcikErisim;
    }
}
