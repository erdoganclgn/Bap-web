package com.mersin.bap_otomasyon.dto.response;

import java.util.List;
import java.util.UUID;

public class UserResearchersResponse {
    private UUID kullaniciId;
    private List<ResearcherResponse> researchers;
    private long aktifProjeSayisi;

    public UserResearchersResponse(UUID kullaniciId, List<ResearcherResponse> researchers, long aktifProjeSayisi) {
        this.kullaniciId = kullaniciId;
        this.researchers = researchers;
        this.aktifProjeSayisi = aktifProjeSayisi;
    }

    public UUID getKullaniciId() { return kullaniciId; }
    public List<ResearcherResponse> getResearchers() { return researchers; }
    public long getAktifProjeSayisi() { return aktifProjeSayisi; }
}
