package com.mersin.bap_otomasyon.service;

import com.mersin.bap_otomasyon.entity.Project;

import java.util.UUID;

@SuppressWarnings("unused")
public interface ClosureService {
    void closeProject(UUID projeId, UUID olcutId);
    boolean checkYayinSarti(UUID projeId);
    void releaseQuota(UUID projeId);
    void applyYayinIbaresiControl(UUID projeId);
    void checkContenantIstila();
}
