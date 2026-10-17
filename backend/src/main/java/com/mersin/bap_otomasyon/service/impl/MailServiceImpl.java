package com.mersin.bap_otomasyon.service.impl;

import com.mersin.bap_otomasyon.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    private static final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

    private final JavaMailSender mailSender;
    private final String from;
    private final String baseUrl;

    public MailServiceImpl(JavaMailSender mailSender,
                           @Value("${bap.password-reset.mail-from}") String from,
                           @Value("${bap.password-reset.base-url}") String baseUrl) {
        this.mailSender = mailSender;
        this.from = from;
        this.baseUrl = baseUrl;
    }

    @Override
    public void sendPasswordResetMail(String to, String token) {
        String resetLink = baseUrl + "?token=" + token;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("BAP Otomasyon - Sifre Sifirlama Talebi");
        message.setText("Merhaba,\n\n"
                + "Hesabiniz icin bir sifre sifirlama talebi alindi.\n\n"
                + "Sifrenizi sifirlamak icin asagidaki baglantiya tiklayin:\n"
                + resetLink + "\n\n"
                + "Bu baglanti sinirli bir sure icin gecerlidir. "
                + "Eger bu talebi siz yapmediysaniz bu e-postayi yok sayabilirsiniz.\n\n"
                + "Mersin Universitesi BAP Otomasyon");
        mailSender.send(message);
        log.info("Sifre sifirlama maili gonderildi: {}", to);
    }
}