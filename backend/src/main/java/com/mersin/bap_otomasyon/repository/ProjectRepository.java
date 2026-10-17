package com.mersin.bap_otomasyon.repository;

import com.mersin.bap_otomasyon.entity.Project;
import com.mersin.bap_otomasyon.entity.enums.ProjeDurum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
    Optional<Project> findByProjeNo(String projeNo);

    List<Project> findByYurutucuId(UUID yurutucuId);

    List<Project> findByDurum(ProjeDurum durum);

    List<Project> findByProjectTypeId(UUID turId);

    @Query("SELECT p FROM Project p WHERE p.yurutucu.id = :userId AND p.durum NOT IN :excludedStates")
    List<Project> findActiveByYurutucuId(@Param("userId") UUID userId,
                                          @Param("excludedStates") List<ProjeDurum> excludedStates);

    @Query("SELECT COUNT(p) FROM Project p WHERE p.yurutucu.id = :userId AND p.durum NOT IN :excludedStates")
    long countActiveByYurutucuId(@Param("userId") UUID userId,
                                  @Param("excludedStates") List<ProjeDurum> excludedStates);

    @Query("SELECT p FROM Project p WHERE p.yurutucu.id = :userId AND p.projectType.id = :turId AND p.durum NOT IN :excludedStates")
    List<Project> findByYurutucuAndTur(@Param("userId") UUID userId,
                                       @Param("turId") UUID turId,
                                       @Param("excludedStates") List<ProjeDurum> excludedStates);

    @Query("SELECT COUNT(p) FROM Project p WHERE p.yurutucu.id = :userId AND p.kontenjanIstilaEdiyorMu = true")
    long countContenantIstilaEdenByYurutucu(@Param("userId") UUID userId);

    List<Project> findByKontenjanIstilaEdiyorMuTrue();

    @Query("SELECT p.projeNo FROM Project p")
    List<String> findAllProjeNos();
}
