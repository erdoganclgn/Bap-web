package com.mersin.bap_otomasyon.dto.request;

import java.util.List;
import java.util.UUID;

public class CreateProjectRequest {
    private String projectTypeKod;
    private String baslik;
    private String ozet;
    private String[] anahtarKelimeler;
    private String temelAlanGrubu;
    private UUID kapatmaOlcutId;
    private List<BudgetItemRequest> budgetItems;
    private List<ResearcherRequest> researchers;

    public String getProjectTypeKod() { return projectTypeKod; }
    public void setProjectTypeKod(String projectTypeKod) { this.projectTypeKod = projectTypeKod; }
    public String getBaslik() { return baslik; }
    public void setBaslik(String baslik) { this.baslik = baslik; }
    public String getOzet() { return ozet; }
    public void setOzet(String ozet) { this.ozet = ozet; }
    public String[] getAnahtarKelimeler() { return anahtarKelimeler; }
    public void setAnahtarKelimeler(String[] anahtarKelimeler) { this.anahtarKelimeler = anahtarKelimeler; }
    public String getTemelAlanGrubu() { return temelAlanGrubu; }
    public void setTemelAlanGrubu(String temelAlanGrubu) { this.temelAlanGrubu = temelAlanGrubu; }
    public UUID getKapatmaOlcutId() { return kapatmaOlcutId; }
    public void setKapatmaOlcutId(UUID kapatmaOlcutId) { this.kapatmaOlcutId = kapatmaOlcutId; }
    public List<BudgetItemRequest> getBudgetItems() { return budgetItems; }
    public void setBudgetItems(List<BudgetItemRequest> budgetItems) { this.budgetItems = budgetItems; }
    public List<ResearcherRequest> getResearchers() { return researchers; }
    public void setResearchers(List<ResearcherRequest> researchers) { this.researchers = researchers; }
}