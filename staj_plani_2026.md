# BAP Otomasyon Projesi Staj Planı
## Tarih Aralığı: 6 Temmuz 2026 - 3 Ağustos 2026

### Proje Özeti
- **Proje Adı:** BAP (Bilimsel Araştırma Projeleri) Otomasyonu
- **Teknoloji Stack:** Spring Boot 4.0.7, Java 21, PostgreSQL, Flyway Migrations
- **Ana Modüller:** Authentication, Project Management, Budget Management, Evaluation, Reporting, Spending, Admin Panel
- **Roller:** Admin, BAPBirim, Hakem, Yurutucu, Muhasebe, Satinalma

### Çalışma Günleri Analizi
- **Toplam Süre:** 6 Temmuz - 3 Ağustos 2026 (29 gün)
- **Hafta Sonları Hariç:** 11-12 Tem, 18-19 Tem, 25-26 Tem, 1-2 Ağu
- **Tatil Günü:** 15 Temmuz 2026 (Çarşamba) - dahil edilmedi
- **Toplam Çalışma Günü:** 20 gün

### Staj Konuları ve Tarih Dağılımı

#### Hafta 1: 6-8 Temmuz 2026 (3 gün)
| Tarih | Konu No | Konu Başlığı | Açıklama |
|-------|---------|--------------|----------|
| 6 Tem Paz | 1 | Proje tanıtımı ve ortam kurulumu | Proje yapısı, teknoloji stack'i, geliştirme ortamı kurulumu |
| 7 Tem Sal | 2 | Spring Boot temelleri | Spring Boot mimarisi, dependency injection, auto-configuration |
| 8 Tem Çar | 3 | JWT Authentication yapısı | JWT token mekanizması, güvenlik konfigürasyonu |

#### Hafta 2: 11-14 Temmuz 2026 (4 gün)
| Tarih | Konu No | Konu Başlığı | Açıklama |
|-------|---------|--------------|----------|
| 11 Tem Paz | 4 | Rol tabanlı erişim kontrolü | Yetkilendirme mekanizmaları, Spring Security |
| 12 Tem Sal | 5 | Proje yönetimi modülü | ProjectController analizi, proje lifecycle |
| 13 Tem Çar | 6 | Veritabanı tasarımı (PostgreSQL) | Entity ilişkileri, tablo yapıları |
| 14 Tem Per | 7 | Entity-DTO mapping | ModelMapper/Lombok kullanımı, data transfer objects |

#### Hafta 3: 18-22 Temmuz 2026 (5 gün)
| Tarih | Konu No | Konu Başlığı | Açıklama |
|-------|---------|--------------|----------|
| 18 Tem Paz | 8 | Repository pattern | JPA Repository, custom query yazımı |
| 19 Tem Sal | 9 | Service layer implementasyonu | Business logic katmanı, transaction yönetimi |
| 20 Tem Çar | 10 | REST API geliştirme | RESTful API best practices, HTTP methods |
| 21 Tem Per | 11 | Validation ve hata yönetimi | Bean Validation, custom exception handling |
| 22 Tem Cum | 12 | Bütçe yönetimi modülü | BudgetController, transfer onay mekanizmaları |

#### Hafta 4: 25-29 Temmuz 2026 (5 gün)
| Tarih | Konu No | Konu Başlığı | Açıklama |
|-------|---------|--------------|----------|
| 25 Tem Paz | 13 | Değerlendirme sistemi | EvaluationController, hakem davet mekanizması |
| 26 Tem Sal | 14 | Raporlama modülü | ReportController, PDF/excel rapor oluşturma |
| 27 Tem Çar | 15 | Harcama takibi | SpendingController, masraf ve avans yönetimi |
| 28 Tem Per | 16 | Admin panel yönetimi | AdminController, kullanıcı yönetimi ve ban işlemleri |
| 29 Tem Cum | 17 | Araştırmacı yönetimi | ResearcherController, araştırmacı kayıt ve yönetimi |

#### Hafta 5: 1-3 Ağustos 2026 (3 gün)
| Tarih | Konu No | Konu Başlığı | Açıklama |
|-------|---------|--------------|----------|
| 1 Ağu Paz | 18 | Proje tipi yönetimi | ProjectTypeController, proje kategorileri |
| 2 Ağu Sal | 19 | Temel alan yönetimi | TemelAlanController, alan bazlı sınıflandırma |
| 3 Ağu Çar | 20 | Test yazımı ve proje değerlendirmesi | Unit/integration test yazma, proje genel değerlendirmesi |

### Önemli Notlar
1. **Hafta Sonları:** Cumartesi ve Pazar günleri staja dahil edilmemiştir
2. **Tatil Günü:** 15 Temmuz 2026 (Demokrasi ve Milli Birlik Günü) dahil edilmemiştir
3. **Konu Sıralaması:** Temelden ileri seviyeye doğru mantıksal bir sıra izlenmiştir
4. **Proje Odaklı Yaklaşım:** Her konu BAP Otomasyon projesinin spesifik modülleri üzerinden işlenmiştir
5. **Pratik Uygulama:** Her konu teorik bilgiyle birlikte proje kodları üzerinde pratik çalışma içermektedir

### Beklenen Kazanımlar
- Spring Boot framework'ü ile enterprise uygulama geliştirme
- JWT tabanlı güvenlik ve rol yönetimi
- RESTful API tasarımı ve geliştirme
- PostgreSQL ve JPA ile veritabanı yönetimi
- Proje yönetimi sistemleri tasarımı
- Test odaklı geliştirme yaklaşımı
- Code review ve best practices

### Teknik Gereksinimler
- Java 21 SDK
- Maven 3.9+
- PostgreSQL 15+
- IDE: IntelliJ IDEA veya VS Code
- Postman veya curl (API testleri için)