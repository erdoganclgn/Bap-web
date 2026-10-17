package com.mersin.bap_otomasyon.repository;

import com.mersin.bap_otomasyon.entity.ExpenseVoucher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExpenseVoucherRepository extends JpaRepository<ExpenseVoucher, UUID> {
    List<ExpenseVoucher> findByProjectId(UUID projeId);
}
