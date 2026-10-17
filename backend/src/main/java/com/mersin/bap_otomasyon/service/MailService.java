package com.mersin.bap_otomasyon.service;

public interface MailService {
    void sendPasswordResetMail(String to, String token);
}
