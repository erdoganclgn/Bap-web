package com.mersin.bap_otomasyon.service.impl;

import com.mersin.bap_otomasyon.entity.*;
import com.mersin.bap_otomasyon.entity.enums.AvansDurum;
import com.mersin.bap_otomasyon.entity.enums.AvansTur;
import com.mersin.bap_otomasyon.entity.enums.SatinAlmaDurum;
import com.mersin.bap_otomasyon.repository.*;
import com.mersin.bap_otomasyon.service.SpendingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class SpendingServiceImpl implements SpendingService {

    private static final String PROJE_BULUNAMADI = "Proje bulunamadi";
    private static final String TALEP_BULUNAMADI = "Talep bulunamadi";

    private final PurchaseRequestRepository purchaseRequestRepository;
    private final AdvanceRepository advanceRepository;
    private final ExpenseVoucherRepository expenseVoucherRepository;
    private final ProjectRepository projectRepository;
    private final BudgetItemRepository budgetItemRepository;

    public SpendingServiceImpl(PurchaseRequestRepository purchaseRequestRepository,
                               AdvanceRepository advanceRepository,
                               ExpenseVoucherRepository expenseVoucherRepository,
                               ProjectRepository projectRepository,
                               BudgetItemRepository budgetItemRepository) {
        this.purchaseRequestRepository = purchaseRequestRepository;
        this.advanceRepository = advanceRepository;
        this.expenseVoucherRepository = expenseVoucherRepository;
        this.projectRepository = projectRepository;
        this.budgetItemRepository = budgetItemRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseRequest> getPurchaseRequests(UUID projeId) {
        return purchaseRequestRepository.findByProjectId(projeId);
    }

    @Override
    public PurchaseRequest createPurchaseRequest(UUID projeId, UUID butceKalemId, PurchaseRequest request) {
        Project project = projectRepository.findById(projeId)
                .orElseThrow(() -> new RuntimeException(PROJE_BULUNAMADI));
        BudgetItem butceKalem = budgetItemRepository.findById(butceKalemId)
                .orElseThrow(() -> new RuntimeException("Butce kalemi bulunamadi"));
        request.setProject(project);
        request.setButceKalem(butceKalem);
        request.setDurum(SatinAlmaDurum.Beklemede);
        return purchaseRequestRepository.save(request);
    }

    @Override
    public PurchaseRequest approvePurchase(UUID purchaseId) {
        PurchaseRequest request = purchaseRequestRepository.findById(purchaseId)
                .orElseThrow(() -> new RuntimeException(TALEP_BULUNAMADI));
        request.setDurum(SatinAlmaDurum.Onaylandi);
        request.setOnayTarihi(LocalDate.now(ZoneId.systemDefault()));
        return purchaseRequestRepository.save(request);
    }

    @Override
    public PurchaseRequest rejectPurchase(UUID purchaseId, String reason) {
        PurchaseRequest request = purchaseRequestRepository.findById(purchaseId)
                .orElseThrow(() -> new RuntimeException(TALEP_BULUNAMADI));
        request.setDurum(SatinAlmaDurum.Reddedildi);
        return purchaseRequestRepository.save(request);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Advance> getAdvances(UUID projeId) {
        return advanceRepository.findByProjectId(projeId);
    }

    @Override
    public Advance createAdvance(UUID projeId, UUID purchaseId, String avansTur, BigDecimal tutar) {
        Project project = projectRepository.findById(projeId)
                .orElseThrow(() -> new RuntimeException(PROJE_BULUNAMADI));
        PurchaseRequest purchase = purchaseRequestRepository.findById(purchaseId)
                .orElseThrow(() -> new RuntimeException(TALEP_BULUNAMADI));

        Advance advance = new Advance();
        advance.setProject(project);
        advance.setSatinAlmaTalebi(purchase);
        advance.setAvansTur(AvansTur.valueOf(avansTur));
        advance.setTutar(tutar);
        advance.setTarih(LocalDate.now(ZoneId.systemDefault()));
        advance.setDurum(AvansDurum.Acik);
        advance.setMahsupEdildiMi(false);
        return advanceRepository.save(advance);
    }

    @Override
    public Advance closeAdvance(UUID advanceId, UUID mahsupBelgesiId) {
        Advance advance = advanceRepository.findById(advanceId)
                .orElseThrow(() -> new RuntimeException("Avans bulunamadi"));
        advance.setMahsupEdildiMi(true);
        advance.setMahsupTarihi(LocalDate.now(ZoneId.systemDefault()));
        advance.setDurum(AvansDurum.MahsupEdildi);

        if (mahsupBelgesiId != null) {
            // Document reference would be set here
        }
        return advanceRepository.save(advance);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Advance> getOverdueAdvances() {
        LocalDate threshold = LocalDate.now(ZoneId.systemDefault()).minusDays(45);
        return advanceRepository.findOverdueAdvances(threshold);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpenseVoucher> getExpenseVouchers(UUID projeId) {
        return expenseVoucherRepository.findByProjectId(projeId);
    }

    @Override
    public ExpenseVoucher createExpenseVoucher(UUID projeId, ExpenseVoucher voucher) {
        Project project = projectRepository.findById(projeId)
                .orElseThrow(() -> new RuntimeException(PROJE_BULUNAMADI));
        voucher.setProject(project);
        voucher.setVergiKesintisi(voucher.getTutar().multiply(new BigDecimal("0.15")));
        voucher.setNetTutar(voucher.getTutar().subtract(voucher.getVergiKesintisi()));
        return expenseVoucherRepository.save(voucher);
    }

    @Override
    public ExpenseVoucher updateExpenseVoucherStatus(UUID voucherId, String durum) {
        ExpenseVoucher voucher = expenseVoucherRepository.findById(voucherId)
                .orElseThrow(() -> new RuntimeException("Pusula bulunamadi"));
        voucher.setDurum(com.mersin.bap_otomasyon.entity.enums.HarcamaPusulaDurum.valueOf(durum));
        return expenseVoucherRepository.save(voucher);
    }
}
