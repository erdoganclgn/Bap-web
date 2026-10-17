# BAP Otomasyonu - Staj Defteri Konu Dağılımı

**Staj Tarih Aralığı:** 06 Temmuz 2026 – 03 Ağustos 2026
**Hariç Tutulanlar:** 15 Temmuz 2026 (tatil), tüm hafta sonları (Cumartesi-Pazar)
**Toplam İş Günü:** 20 gün

**Proje:** BAP (Bilimsel Araştırma Projeleri) Otomasyonu
**Teknoloji:** Java 17 + Spring Boot 3 + Maven, JWT tabanlı rol bazlı kimlik doğrulama

---

## Gün Gün Konu Planı

### 1. Hafta
| Gün | Tarih | Konu |
|-----|-------|------|
| 1 | 06.07.2026 Pazartesi | Proje tanıtımı ve ortam kurulumu: Spring Boot 3, Maven, Java 17, proje yapısının incelenmesi (`BapOtomasyonApplication`, `pom.xml`) |
| 2 | 07.07.2026 Salı | Katmanlı mimari ve paket yapısı: controller, service, repository, entity, dto, config paketlerinin tanınması |
| 3 | 08.07.2026 Çarşamba | Güvenlik altyapısı: `SecurityConfig`, `JwtService`, `JwtAuthenticationFilter`, `JwtAuthenticationEntryPoint` |
| 4 | 09.07.2026 Perşembe | Kimlik doğrulama ve yetkilendirme: `AuthController`, `AuthServiceImpl`, `LoginRequest`/`RegisterRequest`, `KullaniciRol` enum |
| 5 | 10.07.2026 Cuma | Kullanıcı ve rol yönetimi: `User` entity, `UserServiceImpl`, `AdminController`, ban/unban akışları |

### 2. Hafta
| Gün | Tarih | Konu |
|-----|-------|------|
| 6 | 13.07.2026 Pazartesi | Birim ve temel alan yönetimi: `Birim`, `TemelAlan` entity'leri, `BirimController`, `TemelAlanController` |
| 7 | 14.07.2026 Salı | Proje türü (ProjectType) tanımları: `ProjectType`, `ProjectTypeCreateRequest/UpdateRequest`, `ProjectTypeServiceImpl`, kriter yapısı |
| 8 | 16.07.2026 Perşembe | Proje oluşturma ve yaşam döngüsü: `Project` entity, `CreateProjectRequest`, `ProjectController`, durum akışı (BasvuruAlindi → Onaylandi → Kapandi) |
| 9 | 17.07.2026 Cuma | Proje servisleri ve validasyon: `ProjectServiceImpl`, `ProjectValidationServiceImpl`, eşzamanlı limit kontrolü, `ProjectRepository` sorguları |

### 3. Hafta
| Gün | Tarih | Konu |
|-----|-------|------|
| 10 | 20.07.2026 Pazartesi | Araştırmacı (Researcher) yönetimi: `ResearcherController`, `ProjectResearcher`, `ResearcherServiceImpl`, `ArastirmaciRol` |
| 11 | 21.07.2026 Salı | Bütçe (Budget) yönetimi: `BudgetController`, `BudgetServiceImpl`, `BudgetItem`, özet (`/ozet`) çıktısı |
| 12 | 22.07.2026 Çarşamba | Bütçe transferi: `BudgetTransfer`, `BudgetTransferRepository`, onay/red akışları (`/transfers/{id}/approve`) |
| 13 | 23.07.2026 Perşembe | Avans (Advance) yönetimi: `Advance`, `AdvanceCloseRequest`, avans türleri/durumları (`AvansTur`, `AvansDurum`), kapanış akışı |
| 14 | 24.07.2026 Cuma | Harcama ve satın alma: `SpendingController`, `SpendingServiceImpl`, `ExpenseVoucher` (harcama pusulası), `PurchaseRequest` (satın alma) |

### 4. Hafta
| Gün | Tarih | Konu |
|-----|-------|------|
| 15 | 27.07.2026 Pazartesi | Satın alma onay akışı ve burs ödemesi: `SatinAlmaDurum`, `/purchases/{id}/approve`, `BursaryPayment`, `BursaryServiceImpl` |
| 16 | 28.07.2026 Salı | Rapor (Report) yönetimi: `Report`, `ReportController`, `ReportServiceImpl`, rapor türleri/durumları, `/reports/{id}/evaluate` |
| 17 | 29.07.2026 Çarşamba | Değerlendirme (Evaluation): `EvaluationController`, `EvaluationServiceImpl`, hakem davet/kabul/red (`/invite`, `/accept`, `/submit`) |
| 18 | 30.07.2026 Perşembe | Kapanış kriterleri ve proje kapanışı: `ClosureCriteria`, `ClosureCriteriaServiceImpl`, `ClosureServiceImpl`, `ProjeDurum` |
| 19 | 31.07.2026 Cuma | Bildirim, mail ve sistem altyapısı: `Notification`, `MailServiceImpl`, `Parameter`, `AuditLog`, `IntegrationLog` |

### 5. Hafta
| Gün | Tarih | Konu |
|-----|-------|------|
| 20 | 03.08.2026 Pazartesi | Test, derleme ve genel değerlendirme: `mvn compile` kontrolü, `run.log` incelemesi, staj defterinin tamamlanması ve proje özeti |

---

**Not:** Her günün konusu, BAP projesinin ilgili modülüyle (controller/service/entity/dto) birebir ilişkilidir. 15 Temmuz 2026 (tatil) ve tüm Cumartesi-Pazar günleri plan dışı bırakılmıştır.
