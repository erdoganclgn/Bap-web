package com.mersin.bap_otomasyon.repository;

import com.mersin.bap_otomasyon.entity.Evaluation;
import com.mersin.bap_otomasyon.entity.enums.DegerlendiriciTip;
import com.mersin.bap_otomasyon.entity.enums.DegerlendirmeTuru;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EvaluationRepository extends JpaRepository<Evaluation, UUID> {
    List<Evaluation> findByProjectId(UUID projeId);

    List<Evaluation> findByDegerlendiriciId(UUID degerlendiriciId);

    Optional<Evaluation> findByProjectIdAndDegerlendiriciId(UUID projeId, UUID degerlendiriciId);

    List<Evaluation> findByDegerlendiriciIdAndTeslimTarihiIsNull(UUID degerlendiriciId);

    List<Evaluation> findByProjectIdAndDegerlendiriciTip(UUID projeId, DegerlendiriciTip tip);

    List<Evaluation> findByProjectIdAndDegerlendirmeTuru(UUID projeId, DegerlendirmeTuru turu);
}
