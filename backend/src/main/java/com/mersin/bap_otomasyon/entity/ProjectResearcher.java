package com.mersin.bap_otomasyon.entity;

import com.mersin.bap_otomasyon.entity.enums.ArastirmaciRol;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "project_researchers",
       uniqueConstraints = @UniqueConstraint(columnNames = {"proje_id", "kullanici_id"}))
public class ProjectResearcher {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proje_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kullanici_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private ArastirmaciRol rol;

    @Column(name = "baslama_tarihi", nullable = false)
    private LocalDate baslamaTarihi = LocalDate.now();

    @Column(name = "bitis_tarihi")
    private LocalDate bitisTarihi;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public ArastirmaciRol getRol() { return rol; }
    public void setRol(ArastirmaciRol rol) { this.rol = rol; }
    public LocalDate getBaslamaTarihi() { return baslamaTarihi; }
    public void setBaslamaTarihi(LocalDate baslamaTarihi) { this.baslamaTarihi = baslamaTarihi; }
    public LocalDate getBitisTarihi() { return bitisTarihi; }
    public void setBitisTarihi(LocalDate bitisTarihi) { this.bitisTarihi = bitisTarihi; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
