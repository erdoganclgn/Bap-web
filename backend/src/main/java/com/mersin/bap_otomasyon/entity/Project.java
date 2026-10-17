package com.mersin.bap_otomasyon.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mersin.bap_otomasyon.entity.enums.ProjeDurum;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "proje_no", nullable = false, unique = true, length = 50)
    private String projeNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tur_id", nullable = false)
    private ProjectType projectType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yurutucu_id", nullable = false)
    private User yurutucu;

    @Enumerated(EnumType.STRING)
    @Column(name = "durum", nullable = false)
    private ProjeDurum durum = ProjeDurum.Taslak;

    @Column(name = "baslik", nullable = false, length = 500)
    private String baslik;

    @Column(name = "ozet", columnDefinition = "TEXT")
    private String ozet;

    @Column(name = "anahtar_kelimeler", columnDefinition = "TEXT[]")
    private String[] anahtarKelimeler;

    @Column(name = "baslangic_tarihi")
    private LocalDate baslangicTarihi;

    @Column(name = "bitis_tarihi")
    private LocalDate bitisTarihi;

    @Column(name = "fiili_bitis_tarihi")
    private LocalDate fiiliBitisTarihi;

    @Column(name = "butce_toplam", nullable = false, precision = 12, scale = 2)
    private BigDecimal butceToplam = BigDecimal.ZERO;

    @Column(name = "butce_harcanan", nullable = false, precision = 12, scale = 2)
    private BigDecimal butceHarcanan = BigDecimal.ZERO;

    @Column(name = "temel_alan_grubu", length = 10)
    private String temelAlanGrubu;

    @Column(name = "protokol_imzalandi_mi", nullable = false)
    private Boolean protokolImzalandiMi = false;

    @Column(name = "protokol_tarihi")
    private LocalDate protokolTarihi;

    @Column(name = "askida_mi", nullable = false)
    private Boolean askidaMi = false;

    @Column(name = "aski_baslangic")
    private LocalDate askiBaslangic;

    @Column(name = "aski_bitis")
    private LocalDate askiBitis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kapatma_olcut_id")
    private ClosureCriteria kapatmaOlcult;

    @Column(name = "yayin_sarti_saglandi_mi", nullable = false)
    private Boolean yayinSartiSaglandiMi = false;

    @Column(name = "kontenjan_istila_ediyor_mu", nullable = false)
    private Boolean kontenjanIstilaEdiyorMu = false;

    @Version
    @Column(name = "version", nullable = false)
    private Integer version = 0;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ProjectResearcher> projectResearchers;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ProjectDocument> projectDocuments;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<BudgetItem> budgetItems;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<BudgetTransfer> budgetTransfers;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Evaluation> evaluations;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Report> reports;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<PurchaseRequest> purchaseRequests;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Advance> advances;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<BursaryPayment> bursaryPayments;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ExpenseVoucher> expenseVouchers;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getProjeNo() { return projeNo; }
    public void setProjeNo(String projeNo) { this.projeNo = projeNo; }
    public ProjectType getProjectType() { return projectType; }
    public void setProjectType(ProjectType projectType) { this.projectType = projectType; }
    public User getYurutucu() { return yurutucu; }
    public void setYurutucu(User yurutucu) { this.yurutucu = yurutucu; }
    public ProjeDurum getDurum() { return durum; }
    public void setDurum(ProjeDurum durum) { this.durum = durum; }
    public String getBaslik() { return baslik; }
    public void setBaslik(String baslik) { this.baslik = baslik; }
    public String getOzet() { return ozet; }
    public void setOzet(String ozet) { this.ozet = ozet; }
    public String[] getAnahtarKelimeler() { return anahtarKelimeler; }
    public void setAnahtarKelimeler(String[] anahtarKelimeler) { this.anahtarKelimeler = anahtarKelimeler; }
    public LocalDate getBaslangicTarihi() { return baslangicTarihi; }
    public void setBaslangicTarihi(LocalDate baslangicTarihi) { this.baslangicTarihi = baslangicTarihi; }
    public LocalDate getBitisTarihi() { return bitisTarihi; }
    public void setBitisTarihi(LocalDate bitisTarihi) { this.bitisTarihi = bitisTarihi; }
    public LocalDate getFiiliBitisTarihi() { return fiiliBitisTarihi; }
    public void setFiiliBitisTarihi(LocalDate fiiliBitisTarihi) { this.fiiliBitisTarihi = fiiliBitisTarihi; }
    public BigDecimal getButceToplam() { return butceToplam; }
    public void setButceToplam(BigDecimal butceToplam) { this.butceToplam = butceToplam; }
    public BigDecimal getButceHarcanan() { return butceHarcanan; }
    public void setButceHarcanan(BigDecimal butceHarcanan) { this.butceHarcanan = butceHarcanan; }
    public String getTemelAlanGrubu() { return temelAlanGrubu; }
    public void setTemelAlanGrubu(String temelAlanGrubu) { this.temelAlanGrubu = temelAlanGrubu; }
    public Boolean getProtokolImzalandiMi() { return protokolImzalandiMi; }
    public void setProtokolImzalandiMi(Boolean protokolImzalandiMi) { this.protokolImzalandiMi = protokolImzalandiMi; }
    public LocalDate getProtokolTarihi() { return protokolTarihi; }
    public void setProtokolTarihi(LocalDate protokolTarihi) { this.protokolTarihi = protokolTarihi; }
    public Boolean getAskidaMi() { return askidaMi; }
    public void setAskidaMi(Boolean askidaMi) { this.askidaMi = askidaMi; }
    public LocalDate getAskiBaslangic() { return askiBaslangic; }
    public void setAskiBaslangic(LocalDate askiBaslangic) { this.askiBaslangic = askiBaslangic; }
    public LocalDate getAskiBitis() { return askiBitis; }
    public void setAskiBitis(LocalDate askiBitis) { this.askiBitis = askiBitis; }
    public ClosureCriteria getKapatmaOlcult() { return kapatmaOlcult; }
    public void setKapatmaOlcult(ClosureCriteria kapatmaOlcult) { this.kapatmaOlcult = kapatmaOlcult; }
    public Boolean getYayinSartiSaglandiMi() { return yayinSartiSaglandiMi; }
    public void setYayinSartiSaglandiMi(Boolean yayinSartiSaglandiMi) { this.yayinSartiSaglandiMi = yayinSartiSaglandiMi; }
    public Boolean getKontenjanIstilaEdiyorMu() { return kontenjanIstilaEdiyorMu; }
    public void setKontenjanIstilaEdiyorMu(Boolean kontenjanIstilaEdiyorMu) { this.kontenjanIstilaEdiyorMu = kontenjanIstilaEdiyorMu; }
    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public LocalDateTime getDeletedAt() { return deletedAt; }
    public List<ProjectResearcher> getProjectResearchers() { return projectResearchers; }
    public void setProjectResearchers(List<ProjectResearcher> projectResearchers) { this.projectResearchers = projectResearchers; }
    public List<ProjectDocument> getProjectDocuments() { return projectDocuments; }
    public List<BudgetItem> getBudgetItems() { return budgetItems; }
    public void setBudgetItems(List<BudgetItem> budgetItems) { this.budgetItems = budgetItems; }
    public List<BudgetTransfer> getBudgetTransfers() { return budgetTransfers; }
    public List<Evaluation> getEvaluations() { return evaluations; }
    public List<Report> getReports() { return reports; }
    public List<PurchaseRequest> getPurchaseRequests() { return purchaseRequests; }
    public List<Advance> getAdvances() { return advances; }
    public List<BursaryPayment> getBursaryPayments() { return bursaryPayments; }
    public List<ExpenseVoucher> getExpenseVouchers() { return expenseVouchers; }
}
