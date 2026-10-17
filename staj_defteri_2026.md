# MERSİN ÜNİVERSİTESİ
## BİLGİSAYAR VE BİLİŞİM BİLİMLERİ FAKÜLTESİ
### STAJ DEFTERİ

---

**Öğrenci Adı Soyadı:**

**Öğrenci No:**

**Bölümü:** Yazılım Mühendisliği

**Staj Yeri:** Mersin Üniversitesi - Bilimsel Araştırma Projeleri Koordinatörlüğü

**Staj Dönemi:** 6 Temmuz 2026 - 3 Ağustos 2026

**Staj Süresi:** 20 İş Günü

---

## GÜNLÜK ÇALIŞMA KAYITLARI

### HAFTA 1

**Tarih:** 06.07.2026 Pazartesi  
**Konu:** Proje Tanıtımı ve Ortam Kurulumu  
**Yapılan İşler:**
- BAP Otomasyon projesi hakkında genel bilgilendirme
- Proje teknoloji stack'inin incelenmesi (Spring Boot 4.0.7, Java 21, PostgreSQL)
- Geliştirme ortamının kurulumu (JDK 21, Maven, PostgreSQL)
- IDE yapılandırması (IntelliJ IDEA / VS Code)
- Proje kaynak kodlarının indirilmesi ve derlenmesi

---

**Tarih:** 07.07.2026 Salı  
**Konu:** Spring Boot Temelleri  
**Yapılan İşler:**
- Spring Boot mimarisi ve temel kavramlar
- Dependency Injection ve Inversion of Control (IoC) kavramları
- Auto-configuration mekanizması
- Application.properties ve YAML yapılandırma dosyaları
- BapOtomasyonApplication sınıfının incelenmesi

---

**Tarih:** 08.07.2026 Çarşamba  
**Konu:** JWT Authentication Yapısı  
**Yapılan İşler:**
- JWT (JSON Web Token) mekanizmasının çalışma prensibi
- JwtService sınıfının incelenmesi
- JwtAuthenticationFilter implementasyonu
- Token oluşturma ve doğrulama işlemleri
- AuthController endpoints analizi

---

**Tarih:** 09.07.2026 Perşembe  
**Konu:** Rol Tabanlı Erişim Kontrolü  
**Yapılan İşler:**
- Spring Security yapılandırması (SecurityConfig)
- KullaniciRol enum yapısı (Admin, BAPBirim, Hakem, Yurutucu, Muhasebe, Satinalma)
- Rol bazlı yetkilendirme mekanizmaları
- @PreAuthorize ve @Secured anotasyonları
- SecurityUtils sınıfının incelenmesi

---

**Tarih:** 10.07.2026 Cuma  
**Konu:** Proje Yönetimi Modülü  
**Yapılan İşler:**
- ProjectController sınıfının detaylı incelenmesi
- Proje oluşturma, güncelleme, silme işlemleri
- Proje durum geçişleri (BasvuruAlindi → Hazirlaniyor → OnayaSunuldu → ...)
- Proje validasyon mekanizmaları
- Eşzamanlı proje limiti kontrolü

---

### HAFTA 2

**Tarih:** 13.07.2026 Pazartesi  
**Konu:** Veritabanı Tasarımı (PostgreSQL)  
**Yapılan İşler:**
- PostgreSQL veritabanı yapısının incelenmesi
- Entity ilişkilerinin (One-to-Many, Many-to-Many) analizi
- Flyway migration dosyalarının incelenmesi
- Tablo yapıları (BAP_TRANSFERLER, BAP_SATINALMA, BAP_AVANSLAR, BAP_MASRAF)
- Foreign key ve constraint tanımlamaları

---

**Tarih:** 14.07.2026 Salı  
**Konu:** Entity-DTO Mapping  
**Yapılan İşler:**
- Entity sınıfları ile DTO sınıfları arasındaki farklar
- Lombok kütüphanesi kullanımı (@Data, @Builder, @NoArgsConstructor, @AllArgsConstructor)
- Request DTO'ları (CreateProjectRequest, LoginRequest, vb.)
- Response DTO'ları (ApiResponse, LoginResponse, ProjectLimitResponse)
- DTO mapping best practices

---

**Tarih:** 16.07.2026 Perşembe  
**Konu:** Repository Pattern  
**Yapılan İşler:**
- JPA Repository interface yapısı
- CRUD operasyonları
- Custom query method tanımlama (@Query anotasyonu)
- JPQL ve Native SQL kullanımı
- Repository metodlarının test edilmesi

---

**Tarih:** 17.07.2026 Cuma  
**Konu:** Service Layer İmplementasyonu  
**Yapılan İşler:**
- Service katmanı sorumluluklarının belirlenmesi
- ProjectServiceImpl sınıfının detaylı incelenmesi
- Business logic implementasyonu
- Transaction yönetimi (@Transactional)
- Exception handling stratejileri

---

### HAFTA 3

**Tarih:** 20.07.2026 Pazartesi  
**Konu:** REST API Geliştirme  
**Yapılan İşler:**
- RESTful API tasarım prensipleri
- HTTP metodları (GET, POST, PUT, PATCH, DELETE)
- Endpoint URL yapılandırması
- Request/Response body yapıları
- HTTP status code kullanımı

---

**Tarih:** 21.07.2026 Salı  
**Konu:** Validation ve Hata Yönetimi  
**Yapılan İşler:**
- Bean Validation anotasyonları (@NotNull, @NotBlank, @Size, @Email, vb.)
- Custom validator oluşturma
- ValidationResult sınıfının kullanımı
- Global exception handler (@ControllerAdvice)
- ErrorResponse ve ValidationError DTO'ları

---

**Tarih:** 22.07.2026 Çarşamba  
**Konu:** Bütçe Yönetimi Modülü  
**Yapılan İşler:**
- BudgetController analizi
- Bütçe kalemleri yönetimi (BudgetItemRequest)
- Bütçe transfer işlemleri (BudgetTransferRequest)
- Transfer onay mekanizmaları (/transfers/{id}/approve, /reject)
- Bütçe özet raporları (/ozet endpoint)

---

**Tarih:** 23.07.2026 Perşembe  
**Konu:** Değerlendirme Sistemi  
**Yapılan İşler:**
- EvaluationController fonksiyonelliği
- Hakem davet mekanizması (/invite endpoint)
- Değerlendirme kabul/red işlemleri (/accept, /reject)
- Değerlendirme gönderme (/submit)
- Hatırlatma sistemi (/remind)

---

**Tarih:** 24.07.2026 Cuma  
**Konu:** Raporlama Modülü  
**Yapılan İşler:**
- ReportController yapısı
- Rapor oluşturma ve listeleme
- Rapor değerlendirme sistemi (/reports/{reportId}/evaluate)
- ReportEvaluationRequest analizi
- Rapor durum yönetimi

---

### HAFTA 4

**Tarih:** 27.07.2026 Pazartesi  
**Konu:** Harcama Takibi  
**Yapılan İşler:**
- SpendingController incelenmesi
- Satın alma işlemleri yönetimi
- Satın alma onay mekanizmaları (/purchases/{id}/approve, /reject)
- Avans işlemleri
- Avans kapatma (/advances/{id}/close)

---

**Tarih:** 28.07.2026 Salı  
**Konu:** Admin Panel Yönetimi  
**Yapılan İşler:**
- AdminController fonksiyonlarının incelenmesi
- Kullanıcı yönetimi (listeleme, detay görüntüleme)
- Kullanıcı yasaklama mekanizması (/users/{id}/ban)
- Yasak kaldırma işlemi (/users/{id}/unban)
- UserBanRequest DTO yapısı

---

**Tarih:** 29.07.2026 Çarşamba  
**Konu:** Araştırmacı Yönetimi  
**Yapılan İşler:**
- ResearcherController analizi
- Araştırmacı kayıt işlemleri
- ResearcherRequest DTO yapısı
- Araştırmacı bilgilerinin güncellenmesi
- Araştırmacı listeleme ve arama

---

**Tarih:** 30.07.2026 Perşembe  
**Konu:** Proje Tipi Yönetimi  
**Yapılan İşler:**
- ProjectTypeController incelenmesi
- Proje tipi oluşturma (ProjectTypeCreateRequest)
- Proje tipi güncelleme (ProjectTypeUpdateRequest)
- Eşzamanlı proje limit yönetimi
- Proje tipi silme ve listeleme

---

**Tarih:** 31.07.2026 Cuma  
**Konu:** Temel Alan Yönetimi  
**Yapılan İşler:**
- TemelAlanController yapısı
- Birim yönetimi (BirimController)
- BirimRequest ve BirimResponse DTO'ları
- Temel alan ve birim ilişkileri
- Kapanış kriterleri (ClosureCriteriaController)

---

### HAFTA 5

**Tarih:** 03.08.2026 Pazartesi  
**Konu:** Test Yazımı ve Proje Değerlendirmesi  
**Yapılan İşler:**
- Unit test yazımı (JUnit 5)
- Integration test implementasyonu
- MockMvc kullanımı
- Test coverage analizi
- Proje genel değerlendirmesi ve staj sonuç raporu

---

## STAJ YERİ YETKİLİSİ ONAY BÖLÜMÜ

**Ad Soyad:**

**İmza:**

**Tarih:**

**Kaşe:**

---

## FAKÜLTE STAJ KOMİSYONU DEĞERLENDİRMESİ

**Komisyon Üyesi:**

**İmza:**

**Tarih:**
