package com.mersin.bap_otomasyon.dto.request;

import java.util.UUID;

public class AdvanceCloseRequest {
    private UUID mahsubBelgesiId;

    public UUID getMahsubBelgesiId() { return mahsubBelgesiId; }
    public void setMahsubBelgesiId(UUID mahsubBelgesiId) { this.mahsubBelgesiId = mahsubBelgesiId; }
}