package com.mersin.bap_otomasyon.repository;

import com.mersin.bap_otomasyon.entity.BudgetItem;
import com.mersin.bap_otomasyon.entity.enums.KalemKodu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BudgetItemRepository extends JpaRepository<BudgetItem, UUID> {
    List<BudgetItem> findByProjectId(UUID projeId);

    List<BudgetItem> findByProjectIdAndKalemKodu(UUID projeId, KalemKodu kalemKodu);
}
