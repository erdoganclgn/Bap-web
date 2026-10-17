package com.mersin.bap_otomasyon.dto.request;

import com.mersin.bap_otomasyon.entity.enums.KullaniciRol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    @NotBlank(message = "Email zorunludur")
    @Email(message = "Gecerli bir email girilmelidir")
    private String email;

    @NotBlank(message = "Sifre zorunludur")
    @Size(min = 6, message = "Sifre en az 6 karakter olmalidir")
    private String password;

    @NotBlank(message = "Ad soyad zorunludur")
    private String adSoyad;

    private String unvan;

    private KullaniciRol rol;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getAdSoyad() { return adSoyad; }
    public void setAdSoyad(String adSoyad) { this.adSoyad = adSoyad; }
    public String getUnvan() { return unvan; }
    public void setUnvan(String unvan) { this.unvan = unvan; }
    public KullaniciRol getRol() { return rol; }
    public void setRol(KullaniciRol rol) { this.rol = rol; }
}
