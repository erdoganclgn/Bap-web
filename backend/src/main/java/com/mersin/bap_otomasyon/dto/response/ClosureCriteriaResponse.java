package com.mersin.bap_otomasyon.dto.response;

import com.mersin.bap_otomasyon.entity.ClosureCriteria;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ClosureCriteriaResponse {
    private UUID id;
    private Integer olcutNo;
    private String ad;
    private String aciklama;
    private List<String> gecerliAlanGruplari;
    private List<String> gecerliProjeTurleri;
    private Boolean aktif;

    public static ClosureCriteriaResponse from(ClosureCriteria entity) {
        ClosureCriteriaResponse dto = new ClosureCriteriaResponse();
        dto.id = entity.getId();
        dto.olcutNo = entity.getOlcutNo();
        dto.ad = entity.getAd();
        dto.aciklama = entity.getAciklama();
        dto.gecerliAlanGruplari = entity.getGecerliAlanGruplari() != null
                ? Arrays.asList(entity.getGecerliAlanGruplari())
                : List.of();
        dto.gecerliProjeTurleri = entity.getGecerliProjeTurleri() != null
                ? List.of(entity.getGecerliProjeTurleri())
                : List.of();
        dto.aktif = entity.getAktif();
        return dto;
    }

    public UUID getId() { return id; }
    public Integer getOlcutNo() { return olcutNo; }
    public String getAd() { return ad; }
    public String getAciklama() { return aciklama; }
    public List<String> getGecerliAlanGruplari() { return gecerliAlanGruplari; }
    public List<String> getGecerliProjeTurleri() { return gecerliProjeTurleri; }
    public Boolean getAktif() { return aktif; }
}