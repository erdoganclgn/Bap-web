# BAP Otomasyon Sistemi — Yazılım Tasarım Dokümanı (SDD)

**Dayanak:** Mersin Üniversitesi BAP İhtiyaç Analizi Dokümanı  
**Sürüm:** 1.0 · **Durum:** Taslak  
**Hedef Kitle:** Geliştiriciler, sistem mimarları, proje yöneticileri ve teknik bilgiye sahip paydaşlar

---

## İÇİNDEKİLER

1. [Giriş ve Amaç](#1-giriş-ve-amaç)
2. [Sistem Mimarisi (Genel Bakış)](#2-sistem-mimarisi-genel-bakış)
3. [Teknoloji Yığını Önerisi](#3-teknoloji-yığını-önerisi)
4. [Modül Mimarisi](#4-modül-mimarisi)
5. [Veritabanı Tasarımı (Kavramsal)](#5-veritabanı-tasarımı-kavramsal)
6. [API Tasarımı](#6-api-tasarımı)
7. [Durum Makineleri (State Machines)](#7-durum-makineleri-state-machines)
8. [Önemli Akış Diyagramları](#8-önemli-akış-diyagramları)
9. [Sınıf/Model Tasarımı](#9-sınıfmodel-tasarımı)
10. [Ekran/UI Tasarımı (Şematik)](#10-ekranui-tasarımı-şematik)
11. [Güvenlik Tasarımı](#11-güvenlik-tasarımı)
12. [Entegrasyon Tasarımı](#12-entegrasyon-tasarımı)
13. [Parametrik Yapı Tasarımı](#13-parametrik-yapı-tasarımı)
14. [Hata Yönetimi ve Log Stratejisi](#14-hata-yönetimi-ve-log-stratejisi)
15. [Performans ve Ölçeklenebilirlik](#15-performans-ve-ölçeklenebilirlik)

---

## 1. Giriş ve Amaç

### 1.1. Bu Doküman Kimler İçin?

Bu doküman, İhtiyaç Analizi'nde tanımlanan gereksinimlerin **nasıl yazılıma dönüştürüleceğini** açıklar. Okuyucunun temel yazılım geliştirme kavramlarına hakim olduğu varsayılır (API, veritabanı, state machine, CRUD vb.), ancak BAP mevzuatını bilmesi gerekmez.

### 1.2. SDD'nin Amacı

- Geliştiricilere **ne inşa edeceklerini** değil, **nasıl inşa edeceklerini** söylemek
- Sistemin modüllerini, veritabanı yapısını, API uçlarını ve akışlarını tanımlamak
- Farklı ekiplerin (backend, frontend, veritabanı) aynı dilde konuşmasını sağlamak

### 1.3. Kısaltmalar

| Kısaltma | Anlamı |
|----------|--------|
| BAP | Bilimsel Araştırma Projeleri |
| SDD | Software Design Document (Yazılım Tasarım Dokümanı) |
| API | Application Programming Interface |
| CRUD | Create, Read, Update, Delete |
| JWT | JSON Web Token |
| RBAC | Role-Based Access Control |
| RDBMS | Relational Database Management System |
| SLA | Service Level Agreement |
| AGNO | Ağırlıklı Genel Not Ortalaması |
| TP | Tez Projesi |
| AP | Araştırma Projesi |

---

## 2. Sistem Mimarisi (Genel Bakış)

### 2.1. Mimari Stil: Katmanlı Mimari (Layered Architecture) + Mikroservis Hazırlığı

BAP Otomasyon Sistemi, başlangıçta **katmanlı bir monolit** olarak inşa edilir, ancak modüller arası gevşek bağlı (loosely coupled) tasarlanır. Gelecekte yoğunluk yaşanan modüller (ör. başvuru, harcama) ayrı servislere çıkarılabilir.

```
┌─────────────────────────────────────────────────────────────────────┐
│                         SUNUM KATMANI (Frontend)                     │
│  React / Vue.js SPA                                                 │
│  - Yürütücü Paneli                                                  │
│  - BAP Birim Paneli                                                 │
│  - Komisyon Paneli                                                  │
│  - Hakem Paneli                                                     │
│  - Admin/Yönetim Paneli                                             │
└──────────────────────────┬──────────────────────────────────────────┘
                           │ HTTPS / REST API
┌──────────────────────────▼──────────────────────────────────────────┐
│                       API KATMANI (Backend)                          │
│  .NET Core / Node.js (NestJS) / Java Spring Boot                    │
│                                                                      │
│  ┌─────────────┐ ┌──────────────┐ ┌────────────┐ ┌──────────────┐  │
│  │ Auth & Role │ │ Başvuru API  │ │ Harcama API│ │ Rapor API    │  │
│  │ Controller   │ │ Controller   │ │ Controller │ │ Controller   │  │
│  └─────────────┘ └──────────────┘ └────────────┘ └──────────────┘  │
│  ┌─────────────┐ ┌──────────────┐ ┌────────────┐ ┌──────────────┐  │
│  │ Bursiyer API│ │ Değerlendirme│ │ Parametre  │ │ Entegrasyon  │  │
│  │ Controller   │ │ API          │ │ API        │ │ API          │  │
│  └─────────────┘ └──────────────┘ └────────────┘ └──────────────┘  │
└──────────────────────────┬──────────────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────────────┐
│                     İŞ KATMANI (Business Logic)                      │
│  - Servis sınıfları (ProjectService, BudgetService, vb.)            │
│  - State Machine yöneticisi                                         │
│  - Validation engine (kurallar parametrik)                          │
│  - Audit log yazıcı                                                 │
│  - Bildirim motoru                                                  │
└──────────────────────────┬──────────────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────────────┐
│                    VERİ ERİŞİM KATMANI (Data Access)                 │
│  - Repository Pattern (Entity Framework Core / Hibernate / Prisma) │
│  - Unit of Work                                                     │
│  - Transaction yönetimi                                             │
└──────────────────────────┬──────────────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────────────┐
│                     VERİTABANI (RDBMS + File Storage)                │
│  PostgreSQL / MS SQL Server                                         │
│  - İlişkisel veritabanı (projeler, kullanıcılar, bütçe, vb.)       │
│  - Dosya deposu (belge yönetimi için)                               │
│  - Redis Cache (opsiyonel, parametreler için)                       │
└─────────────────────────────────────────────────────────────────────┘
```

### 2.2. Temel Tasarım İlkeleri

| İlke | Açıklama |
|------|----------|
| **Parametriklik (NFR-01)** | Tüm kurallar veritabanında saklanır, kodda gömülü değildir |
| **Denetlenebilirlik (NFR-02)** | Her işlem loglanır, kim-ne-zaman-önceki-sonraki kaydedilir |
| **RBAC (NFR-06)** | Her kullanıcının rolüne göre yetkisi vardır |
| **Transaction Atomicity** | Limit kontrolleri, durum geçişleri tek transaction'da yapılır |
| **Validation Sandwich** | İstemci + sunucu olmak üzere çift taraflı doğrulama |
| **Soyutlama** | Proje türleri ortak bir arayüz (interface) ile yönetilir |

---

## 3. Teknoloji Yığını Önerisi

Bu bölümdeki seçimler öneridir; mevcut üniversite altyapısına göre değiştirilebilir.

### 3.1. Backend

| Bileşen | Öneri | Alternatif |
|---------|-------|------------|
| Runtime | Node.js (NestJS) veya .NET 8+ | Java Spring Boot, Python Django |
| API Stili | REST (JSON) | GraphQL (karmaşık sorgular için) |
| ORM | Prisma (Node.js) / Entity Framework Core (.NET) | TypeORM, Hibernate |
| Validation | class-validator + custom pipes (NestJS) / FluentValidation (.NET) | Joi, Yup |
| Auth | JWT (access + refresh token) | OAuth 2.0 / SAML (kurumsal SSO) |

### 3.2. Frontend

| Bileşen | Öneri | Alternatif |
|---------|-------|------------|
| Framework | React 18+ (TypeScript) | Vue 3, Angular |
| State Management | React Query (server state) + Zustand (client state) | Redux Toolkit, Pinia |
| UI Kütüphanesi | Ant Design (kapsamlı form/table desteği) | Material UI, PrimeReact |
| Form Yönetimi | React Hook Form + Zod | Formik, Final Form |

### 3.3. Veritabanı

| Bileşen | Öneri | Gerekçe |
|---------|-------|---------|
| RDBMS | PostgreSQL 15+ | JSONB desteği, ücretsiz, güçlü constraint |
| File Storage | MinIO (S3 uyumlu) veya fiziksel disk | Belge yönetimi için |
| Cache | Redis | Parametre önbelleği, oturum yönetimi |

### 3.4. Altyapı

| Bileşen | Öneri |
|---------|-------|
| Containerization | Docker + Docker Compose |
| Reverse Proxy | Nginx / Traefik |
| CI/CD | GitLab CI / GitHub Actions |

---

## 4. Modül Mimarisi

Sistem 10 ana modüle ayrılır:

```
src/
├── modules/
│   ├── auth/               # Kimlik doğrulama, yetkilendirme
│   ├── project/            # Proje başvuru, tür yönetimi
│   ├── evaluation/         # Değerlendirme, hakem, sözlü sunum
│   ├── protocol/           # Protokol oluşturma ve imzalama
│   ├── budget/             # Bütçe, ek bütçe, kalem aktarımı
│   ├── spending/           # Harcama, satın alma, avans
│   ├── report/             # Ara/sonuç raporu yönetimi
│   ├── bursary/            # Bursiyer yönetimi
│   ├── closure/            # Proje kapatma, yayın şartı takibi
│   └── parametre/          # Parametrik kural yönetimi
├── common/                 # Paylaşılan yardımcılar (guard, pipe, filter)
├── config/                 # Yapılandırma
└── database/               # Migration, seed, entity
```

### 4.1. Modüller Arası Bağımlılıklar

```
auth → tüm modüller (yetkilendirme için)
parametre → tüm modüller (kurallar için)
project → evaluation → protocol → budget → spending → report → closure
     ↓                                      ↑
  bursary ──────────────────────────────────┘
```

Bağımlılıklar **tek yönlüdür**. Döngüsel bağımlılıktan kaçınılır. Örneğin `spending`, `project` modülünü referans alabilir, ancak `project`, `spending` modülünü referans almaz.

---

## 5. Veritabanı Tasarımı (Kavramsal)

### 5.1. Varlık-İlişki Diyagramı (Metin Tabanlı)

Bu bölümde her bir tablonun amacı, önemli alanları ve ilişkileri açıklanır. Gerçek implementasyonda ek alanlar (createdAt, updatedAt, vb.) eklenecektir.

### 5.2. Ana Tablolar

#### 5.2.1. Birimler (`birimler`)

Üniversite içindeki fakülte, enstitü, yüksekokul ve bölümleri temsil eder.

| Alan | Tip | Açıklama |
|------|-----|----------|
| id | UUID | Birincil anahtar |
| ad | VARCHAR(255) | Birim adı (örn. Mühendislik Fakültesi) |
| ust_birim_id | FK → birimler.id | Bağlı olduğu üst birim (nullable) |
| birim_turu | ENUM | Fakulte, Enstitu, Yuksekokul, Bolum, Merkez |

**İlişkiler:**
- `birimler.ust_birim_id → birimler.id` (N:1, kendine referans)

#### 5.2.2. Kullanıcılar (`users`)

Sistemdeki tüm paydaşları (yürütücü, araştırmacı, bursiyer, hakem, komisyon üyesi, BAP personeli) temsil eder.

| Alan | Tip | Açıklama |
|------|-----|----------|
| id | UUID | Birincil anahtar |
| email | VARCHAR(255) | @mersin.edu.tr e-posta |
| password_hash | VARCHAR(255) | Hashlenmiş şifre |
| ad_soyad | VARCHAR(255) | - |
| rol | ENUM | Yürütücü, Araştırmacı, Bursiyer, Hakem, Komisyon, BAPPersonel, Koordinatör, Admin, Rektor, EnstitüFakulte |
| unvan | VARCHAR(100) | Dr. Öğr. Üyesi, Prof. Dr. vb. |
| birim_id | FK → birimler | Bağlı olduğu fakülte/enstitü |
| orcid | VARCHAR(50) | - |
| researcher_id | VARCHAR(50) | - |
| scopus_id | VARCHAR(50) | - |
| google_scholar | VARCHAR(255) | - |
| apbs_guncel | BOOLEAN | APBS güncellik kontrolü |
| yoksis_guncel | BOOLEAN | YÖKSİS güncellik kontrolü |
| yasak_bitis_tarihi | DATE | 2 yıllık destek yasağı (nullable) |
| tez_yasagi | BOOLEAN | Süresiz tez yasağı (TP2/TP3) |

**İlişkiler:**
- `users.birim_id → birimler.id` (N:1)

#### 5.2.3. Proje Türleri (`project_types`)

12 proje türünün (TP1, TP2, ..., AP9) parametrik tanımları. Bu tablo sayesinde yeni proje türü eklemek için kod değişikliği gerekmez.

| Alan | Tip | Açıklama |
|------|-----|----------|
| id | UUID | - |
| kod | VARCHAR(10) | TP1, TP2, AP1, ... |
| ad | VARCHAR(255) | Lisans Öğrencisi Araştırma Projesi |
| butce_tavani | DECIMAL(12,2) | 10000, 45000, ... |
| min_sure_ay | INT | 0 (yoksa) veya 6 |
| max_sure_ay | INT | 12, 24, 36 |
| ek_butce_oran | DECIMAL(5,2) | 0.50 (%50) veya 0 |
| ek_sure_ay | INT | 0, 1, 6, 12 |
| performans_artisi_uygulanir | BOOLEAN | Sadece AP1/AP2/AP3 |
| bursiyer_olabilir | BOOLEAN | Hangi türlerde bursiyer var |
| yayin_sarti_zorunlu | BOOLEAN | TP1/AP9 hariç |
| kariyer_boyu_kez | INT | 0 (sınırsız), 1 (AP1/TP1), 3 (AP7), ... |
| yilda_kez | INT | 0 (sınırsız) |
| eszamanli_limit | INT | Maksimum eşzamanlı proje sayısı |
| hakem_atanabilir | BOOLEAN | Hakem değerlendirmesi yapılabilir mi |
| sozlu_sunum_istenebilir | BOOLEAN | - |
| ara_rapor_gerekli | BOOLEAN | - |
| kongre_destegi_var | BOOLEAN | - |
| protokol_gerekli | BOOLEAN | Genelde true |
| rektor_onayi_gerekli | BOOLEAN | AP4/AP5/AP8 |

#### 5.2.4. Projeler (`projects`)

Her bir BAP projesini temsil eder. Sistemdeki en önemli varlıktır.

| Alan | Tip | Açıklama |
|------|-----|----------|
| id | UUID | - |
| proje_no | VARCHAR(50) | Otomatik oluşturulur (BAP-2025-001 gibi) |
| tur_id | FK → project_types | Hangi proje türü |
| yurutucu_id | FK → users | Proje yürütücüsü |
| durum | ENUM | Beklemede, Taslak, BasvuruYapildi, Inceleniyor, EksikIade, Komisyonda, HakemDegerlendirmede, DegerlendirmeTamam, SozluSunum, SozluSunumYapildi, KomisyonKarari, Reddedildi, Kabul, YenidenInceleme, ProtokolAsamasi, Aktif, AskiyaAlindi, Bitti, SonucRaporuBekliyor, RaporKabul, RaporRet, RaporGecikti, Iptal, Kapandi |
| baslik | VARCHAR(500) | Proje başlığı |
| ozet | TEXT | Proje özeti |
| baslangic_tarihi | DATE | Protokol imza tarihi |
| bitis_tarihi | DATE | Planlanan bitiş |
| fiili_bitis_tarihi | DATE | Gerçek bitiş (ek süreli) |
| butce_toplam | DECIMAL(12,2) | Onaylanan toplam bütçe |
| butce_harcanan | DECIMAL(12,2) | Harcanan tutar |
| temel_alan_grubu | ENUM | A, B, C, D, E, F, G |
| protokol_imzalandi_mi | BOOLEAN | - |
| protokol_tarihi | DATE | - |
| askida_mi | BOOLEAN | - |
| aski_baslangic | DATE | - |
| aski_bitis | DATE | En fazla 6 ay |
| kapatma_olcut_id | FK → closure_criteria | Hangi ölçütle kapatıldı |
| yayin_sarti_saglandi_mi | BOOLEAN | - |
| kontenjan_istila_ediyor_mu | BOOLEAN | Yayın şartı sağlanmamışsa true |

**İlişkiler:**
- `projects.tur_id → project_types.id` (N:1)
- `projects.yurutucu_id → users.id` (N:1)
- `projects.temel_alan_grubu` → closure_criteria tablosu ile ilişkili

#### 5.2.5. Proje Araştırmacıları (`project_researchers`)

Projede görev alan araştırmacıları (yürütücü hariç) temsil eder.

| Alan | Tip | Açıklama |
|------|-----|----------|
| id | UUID | - |
| proje_id | FK → projects | - |
| kullanici_id | FK → users | - |
| rol | ENUM | Arastirmaci, Bursiyer |
| baslama_tarihi | DATE | - |
| bitis_tarihi | DATE | - |

**Unique Constraint:** `(proje_id, kullanici_id)` — bir projede birden fazla kez eklenemez.

#### 5.2.7. Kontenjan Artırımları (`quota_increases`)

TP2/TP3 projelerinde danışmanın SCI-E/SSCI/AHCI yayını veya patentine dayalı kontenjan artırımını takip eder.

| Alan | Tip | Açıklama |
|------|-----|----------|
| id | UUID | - |
| kullanici_id | FK → users | Kontenjan artırımı verilen danışman |
| proje_turu | ENUM | TP2, TP3 |
| artis_miktari | INT | +1 kontenjan |
| gerekce | VARCHAR(500) | Yayın/patent referansı |
| baslangic_tarihi | DATE | - |
| bitis_tarihi | DATE | 2 yıl geçerli |
| komisyon_karar_tarihi | DATE | - |

#### 5.2.6. Proje Belgeleri (`project_documents`)

Başvuru sırasında yüklenen tüm belgeler.

| Alan | Tip | Açıklama |
|------|-----|----------|
| id | UUID | - |
| proje_id | FK → projects | - |
| belge_turu | ENUM | BasvuruDosyasi, ProformaFatura, TeknikSartname, EtikKurul, Transkript, OgrenciBelgesi, Feragatname, ArastirmaciBeyan, OrtaklikTaahhutnamesi, vb. |
| dosya_adi | VARCHAR(255) | Orijinal dosya adı |
| dosya_yolu | VARCHAR(500) | Depodaki yolu |
| dosya_boyutu | INT | Byte cinsinden |
| mime_type | VARCHAR(100) | application/pdf, application/msword |
| yukleme_tarihi | TIMESTAMP | - |
| guncel_mi | BOOLEAN | Eksik iade sonrası yeniden yüklenmiş olabilir |

#### 5.2.8. Bütçe Kalemleri (`budget_items`)

Proje bütçesini oluşturan her bir harcama kalemi.

| Alan | Tip | Açıklama |
|------|-----|----------|
| id | UUID | - |
| proje_id | FK → projects | - |
| kalem_kodu | ENUM | 03.1 (Tuketim), 03.2 (Makine), 03.3 (Yolluk), 03.4 (Hizmet), 03.5 (Diger), Burs, AcikErisim |
| aciklama | VARCHAR(500) | - |
| birim_fiyat | DECIMAL(12,2) | Proforma faturadaki KDV hariç birim fiyat |
| miktar | DECIMAL(10,2) | - |
| toplam_tutar | DECIMAL(12,2) | birim_fiyat × miktar |
| kdv_orani | DECIMAL(5,2) | 0, 8, 18 (check constraint: IN (0, 1, 8, 10, 18, 20)) |
| proforma_fatura_id | FK → project_documents | İlişkili proforma fatura belgesi |
| onayli_mi | BOOLEAN | Komisyon onayından geçmiş mi |

#### 5.2.9. Bütçe Aktarımları (`budget_transfers`)

Bütçe kalemleri arası aktarım talepleri. Yolluk/kongre/burs kalemlerine aktarım yasaktır.

| Alan | Tip | Açıklama |
|------|-----|----------|
| id | UUID | - |
| proje_id | FK → projects | - |
| kaynak_kalem_id | FK → budget_items | - |
| hedef_kalem_id | FK → budget_items | - |
| tutar | DECIMAL(12,2) | - |
| gerekce | TEXT | - |
| durum | ENUM | Beklemede, Onaylandi, Reddedildi |
| komisyon_karar_tarihi | DATE | - |

#### 5.2.10. Değerlendirme (`evaluations`)

Hakem ve komisyon değerlendirmelerini tutar.

| Alan | Tip | Açıklama |
|------|-----|----------|
| id | UUID | - |
| proje_id | FK → projects | - |
| degerlendirici_tip | ENUM | Hakem, Komisyon |
| degerlendirici_id | FK → users | Hakem veya komisyon üyesi |
| degerlendirme_turu | ENUM | BasvuruDegerlendirme, AraRapor, SonucRaporu |
| puan | INT | 0-100 arası |
| gorus | TEXT | Açıklama |
| karar | ENUM | Kabul, Ret, Duzeltme |
| davet_tarihi | TIMESTAMP | Hakeme davet gönderim tarihi |
| davet_cevap_tarihi | TIMESTAMP | - |
| teslim_tarihi | TIMESTAMP | Değerlendirme teslim tarihi |
| odeme_yapildi_mi | BOOLEAN | Hakemlik ücreti ödendi mi |

#### 5.2.11. Raporlar (`reports`)

Ara ve sonuç raporları.

| Alan | Tip | Açıklama |
|------|-----|----------|
| id | UUID | - |
| proje_id | FK → projects | - |
| rapor_turu | ENUM | AraRapor, SonucRaporu |
| donem_no | INT | 1, 2, 3... (ara rapor için sıra) |
| baslik | VARCHAR(500) | - |
| dosya_yolu | VARCHAR(500) | PDF rapor dosyası |
| teslim_tarihi | DATE | - |
| durum | ENUM | Beklemede, Kabul, Red, DuzeltmeGerekli |
| degerlendirme_notu | TEXT | Varsa hakem/komisyon görüşü |
| gecikme_gun | INT | - |

#### 5.2.11. Satın Alma Talepleri (`purchase_requests`)

| Alan | Tip | Açıklama |
|------|-----|----------|
| id | UUID | - |
| proje_id | FK → projects | - |
| butce_kalem_id | FK → budget_items | Hangi bütçe kaleminden |
| talep_turu | ENUM | Mal, Hizmet, Seyahat, Kongre |
| aciklama | VARCHAR(500) | - |
| tutar | DECIMAL(12,2) | - |
| teknik_sartname_id | FK → project_documents | - |
| durum | ENUM | Beklemede, Onaylandi, Reddedildi, SatinAlindi |
| onay_tarihi | DATE | - |

#### 5.2.12. Harcama Pusulaları (`expense_vouchers`)

Vergi mükellefi olmayan kişilere yapılan ödemelerde sistemden üretilen pusula. %15 gelir vergisi kesintisi otomatik hesaplanır.

| Alan | Tip | Açıklama |
|------|-----|----------|
| id | UUID | - |
| proje_id | FK → projects | - |
| alici_adi | VARCHAR(255) | Ödeme yapılan kişi |
| tutar | DECIMAL(12,2) | Brüt tutar |
| vergi_kesintisi | DECIMAL(12,2) | %15 gelir vergisi (otomatik hesaplanır) |
| net_tutar | DECIMAL(12,2) | Ödenecek net tutar |
| gerekce | TEXT | Harcama gerekçesi |
| tarih | DATE | - |
| durum | ENUM | Hazir, Odendi, Iptal |

#### 5.2.13. Avanslar (`advances`)

| Alan | Tip | Açıklama |
|------|-----|----------|
| id | UUID | - |
| proje_id | FK → projects | - |
| satin_alma_talebi_id | FK → purchase_requests | - |
| avans_turu | ENUM | MalHizmet, Seyahat |
| tutar | DECIMAL(12,2) | - |
| tarih | DATE | - |
| mahsup_tarihi | DATE | - |
| mahsup_edildi_mi | BOOLEAN | - |
| mahsup_belgesi_id | FK → project_documents | Fatura vb. |
| durum | ENUM | Acik, MahsupEdildi, Gecikmis |

#### 5.2.13. Bursiyer Hakedişleri (`bursary_payments`)

| Alan | Tip | Açıklama |
|------|-----|----------|
| id | UUID | - |
| proje_id | FK → projects | - |
| bursiyer_id | FK → users | - |
| ay | INT | 1-12 |
| yil | INT | - |
| brut_tutar | DECIMAL(10,2) | - |
| sgk_kesintisi | DECIMAL(10,2) | - |
| net_tutar | DECIMAL(10,2) | - |
| hakedis_belgesi_id | FK → project_documents | - |
| odeme_tarihi | DATE | - |
| durum | ENUM | Beklemede, Odendi, Iptal |

**Unique Constraint:** `(bursiyer_id, ay, yil)` — aynı bursiyere aynı ay için iki ödeme yapılamaz.

#### 5.2.14. Parametreler (`parameters`)

Tüm kuralların parametrik olarak saklandığı tablo. NFR-01'in kalbidir.

| Alan | Tip | Açıklama |
|------|-----|----------|
| id | UUID | - |
| grup | VARCHAR(100) | butce, sure, kontenjan, kapatma, bildirim, vb. |
| anahtar | VARCHAR(255) | ap1.butce_tavani, tp1.agno_min, vb. |
| deger | TEXT | JSON formatında değer |
| aciklama | TEXT | İnsan okunabilir açıklama |
| aktif | BOOLEAN | Aktif/pasif |
| guncellenme_tarihi | TIMESTAMP | - |

#### 5.2.15. Denetim Logları (`audit_logs`)

Değiştirilemez (immutable) log tablosu. NFR-02.

| Alan | Tip | Açıklama |
|------|-----|----------|
| id | BIGINT (auto-increment) | - |
| kullanici_id | UUID | İşlemi yapan |
| islem_turu | VARCHAR(50) | PROJE_OLUSTUR, BUTCE_GUNCELLE, DURUM_DEGISTIR, vb. |
| varlik_turu | VARCHAR(50) | Project, BudgetItem, Report |
| varlik_id | UUID | Hangi kayıt üzerinde işlem |
| eski_deger | JSONB | Önceki durum |
| yeni_deger | JSONB | Sonraki durum |
| ip_adresi | VARCHAR(45) | - |
| zaman | TIMESTAMP | Varsayılan: now() |

**Önemli:** Bu tabloya sadece INSERT yapılır. UPDATE veya DELETE yapılamaz. Silinemez bağlantılı (append-only).

#### 5.2.16. Kapatma Ölçütleri (`closure_criteria`)

12 kapatma ölçütünün (ve alt kırılımlarının) tanımlandığı tablo.

| Alan | Tip | Açıklama |
|------|-----|----------|
| id | UUID | - |
| olcut_no | INT | 1-12 |
| ad | VARCHAR(255) | Q1/Q2 WoS Makale |
| aciklama | TEXT | - |
| gecerli_alan_gruplari | TEXT[] | ['A','B','C','D','E','F','G'] (hangi alan gruplarında geçerli) |
| gecerli_proje_turleri | TEXT[] | ['TP2','TP3','AP1','AP2',...] (hangi proje türlerinde geçerli) |

### 5.3. İlişki Özeti (Görsel Metin)

```
birimler ──1:N── birimler (kendine referans, üst birim)
birimler ──1:N── users (birim_id)
project_types ──1:N── projects
users ──1:N── projects (yürütücü)
users ──N:M── projects (araştırmacı) → project_researchers
projects ──1:N── project_documents
projects ──1:N── budget_items
budget_items ──1:N── budget_transfers
projects ──1:N── evaluations
projects ──1:N── reports
projects ──1:N── purchase_requests
purchase_requests ──1:N── advances
projects ──1:N── bursary_payments
closure_criteria ──N:M── projects (kapatma_olcut_id üzerinden)
```

---

## 6. API Tasarımı

### 6.1. API Tasarım Prensipleri

- RESTful kaynak bazlı URL'ler
- Çoğul isimler (`/api/projects`, `/api/users`)
- Standart HTTP metodları (GET, POST, PUT, PATCH, DELETE)
- Hata kodları anlamlı (400 validation error, 404 not found, 409 conflict, 422 business rule violation)
- Sayfalama: `?page=1&limit=20`
- Sıralama: `?sort=createdAt&order=desc`
- Filtreleme: `?durum=Taslak&tur=TP1`
- JSON response formatı: `{ data, meta, errors }`

### 6.2. Temel API Uçları

#### 6.2.1. Kimlik Doğrulama (`/api/auth`)

| Metot | Uç | Açıklama |
|-------|-----|----------|
| POST | /auth/login | E-posta + şifre ile giriş |
| POST | /auth/refresh | Refresh token ile yeni access token |
| POST | /auth/logout | Oturum kapatma |
| GET | /auth/me | Oturum bilgisi |
| POST | /auth/forgot-password | Şifre sıfırlama e-postası |
| POST | /auth/reset-password | Şifre sıfırlama |

#### 6.2.2. Projeler (`/api/projects`)

| Metot | Uç | Açıklama |
|-------|-----|----------|
| GET | /projects | Projeleri listele (filtreleme + sayfalama) |
| POST | /projects | Yeni proje oluştur (taslak) |
| GET | /projects/:id | Proje detayı |
| PATCH | /projects/:id | Proje güncelle (taslak aşamasında) |
| DELETE | /projects/:id | Proje sil (sadece taslak) |
| POST | /projects/:id/submit | Başvuruyu yürütücü onayına gönder |
| POST | /projects/:id/approve | Yürütücü onayı |
| POST | /projects/:id/return | Eksik iade (BAP birimi) |
| POST | /projects/:id/convert | Proje dönüştürme (TP1→AP7, vb.) |
| POST | /projects/:id/suspend | Projeyi askıya al (Komisyon) |
| POST | /projects/:id/resume | Askıdaki projeyi devam ettir |
| POST | /projects/:id/change-executive | Yürütücü değişikliği talebi |
| GET | /projects/:id/timeline | Proje zaman çizelgesi |

#### 6.2.3. Proje Türleri (`/api/project-types`)

| Metot | Uç | Açıklama |
|-------|-----|----------|
| GET | /project-types | Tüm proje türlerini listele |
| GET | /project-types/:kod | Belirli proje türü detayı |
| PATCH | /project-types/:kod | Proje türü parametrelerini güncelle (admin) |

#### 6.2.4. Bütçe (`/api/projects/:id/budget`)

| Metot | Uç | Açıklama |
|-------|-----|----------|
| GET | /projects/:id/budget | Bütçe kalemlerini listele |
| POST | /projects/:id/budget/items | Bütçe kalemi ekle |
| PATCH | /projects/:id/budget/items/:itemId | Bütçe kalemini güncelle |
| DELETE | /projects/:id/budget/items/:itemId | Bütçe kalemini sil |
| POST | /projects/:id/budget/transfers | Bütçe aktarım talebi |
| POST | /projects/:id/budget/extension | Ek bütçe talebi |

#### 6.2.5. Değerlendirme (`/api/evaluations`)

| Metot | Uç | Açıklama |
|-------|-----|----------|
| POST | /evaluations | Değerlendirme başlat (Komisyon) |
| GET | /evaluations/pending | Bekleyen değerlendirmeler (Hakem) |
| POST | /evaluations/:id/accept | Hakem daveti kabul |
| POST | /evaluations/:id/reject | Hakem daveti ret |
| POST | /evaluations/:id/submit | Değerlendirme teslim et |
| POST | /evaluations/:id/remind | Hatırlatma gönder |
| GET | /evaluations/:id/result | Değerlendirme sonucu |

#### 6.2.6. Raporlar (`/api/projects/:id/reports`)

| Metot | Uç | Açıklama |
|-------|-----|----------|
| GET | /projects/:id/reports | Raporları listele |
| POST | /projects/:id/reports | Rapor yükle |
| PATCH | /projects/:id/reports/:reportId | Rapor güncelle |
| POST | /projects/:id/reports/:reportId/submit | Raporu teslim et |
| POST | /projects/:id/reports/:reportId/evaluate | Raporu değerlendir |

#### 6.2.7. Harcama (`/api/projects/:id/purchases`)

| Metot | Uç | Açıklama |
|-------|-----|----------|
| GET | /projects/:id/purchases | Satın alma taleplerini listele |
| POST | /projects/:id/purchases | Satın alma talep et |
| PATCH | /projects/:id/purchases/:purchaseId | Talebi güncelle |
| POST | /projects/:id/purchases/:purchaseId/approve | Onayla (BAP birimi) |
| POST | /projects/:id/purchases/:purchaseId/reject | Reddet |
| GET | /projects/:id/advances/overdue | Gecikmiş (45 günü aşan mahsup edilmemiş) avansları listele |
| POST | /projects/:id/advances | Avans talebi |
| POST | /projects/:id/advances/:advanceId/close | Mahsup (avans kapatma) |

#### 6.2.8. Bursiyer (`/api/projects/:id/bursary`)

| Metot | Uç | Açıklama |
|-------|-----|----------|
| GET | /projects/:id/bursary/students | Bursiyer listesi |
| POST | /projects/:id/bursary/students | Bursiyer ekle |
| DELETE | /projects/:id/bursary/students/:studentId | Bursiyer çıkar |
| POST | /projects/:id/bursary/payments | Hakediş girişi |
| GET | /projects/:id/bursary/payments | Ödeme geçmişi |

#### 6.2.9. Parametreler (`/api/admin/parameters`)

| Metot | Uç | Açıklama |
|-------|-----|----------|
| GET | /admin/parameters | Tüm parametreleri listele |
| GET | /admin/parameters?grup=butce | Gruba göre filtrele |
| PATCH | /admin/parameters/:id | Parametre güncelle |
| POST | /admin/parameters | Yeni parametre ekle |

#### 6.2.10. Kullanıcı Yönetimi (`/api/admin/users`)

| Metot | Uç | Açıklama |
|-------|-----|----------|
| GET | /admin/users | Kullanıcıları listele (filtreleme + sayfalama) |
| GET | /admin/users/:id | Kullanıcı detayı |
| POST | /admin/users | Yeni kullanıcı oluştur |
| PATCH | /admin/users/:id | Kullanıcı bilgilerini güncelle |
| DELETE | /admin/users/:id | Kullanıcı sil (soft delete) |
| POST | /admin/users/:id/ban | Destek yasağı uygula (süreli) |
| POST | /admin/users/:id/unban | Yasağı kaldır |

#### 6.2.11. Denetim Logları (`/api/admin/audit`)

| Metot | Uç | Açıklama |
|-------|-----|----------|
| GET | /admin/audit | Logları listele (filtreleme + sayfalama) |
| GET | /admin/audit/:varlik_turu/:varlik_id | Belirli kaynağın log geçmişi |

---

## 7. Durum Makineleri (State Machines)

Projenin yaşam döngüsü bir state machine (durum makinesi) ile yönetilir. Her durum geçişi tetikleyici bir olayla (event) gerçekleşir.

### 7.1. Proje Durum Makinesi

```
                    ┌──────────────────────────────────────────────────┐
                    │                  BAŞLANGIÇ                      │
                    └──────────────────────────────────────────────────┘
                                          │
                          ┌───────────────┼───────────────────┐
                          │               │                    │
                          ▼               ▼                    ▼
          ┌──────────────────────────┐ ┌────────────┐  ┌──────────────────┐
          │  BEKLEMEDE (AP8 ön)     │ │  TASLAK   │  │  (doğrudan       │
          │  (Ön başvuru, dış       │ │  (Normal  │  │   taslak)        │
          │  kurum onayı bekleniyor) │ │  başlangıç)│  │                  │
          └──────────┬───────────────┘ └─────┬──────┘  └──────────────────┘
                     │ dış_kurum_kabul       │
                     └──────────┬────────────┘
                                ▼
                    ┌──────────────────────────────────────────────────┐
                    │                TASLAK                           │
                    │  (Yürütücü oluşturur, henüz başvuru yok)       │
                    └───────────────┬──────────────────────────────────┘
                                    │ yurutucu_onayla
                                    ▼
                    ┌──────────────────────────────────────────────────┐
                    │          BASVURU_YAPILDI                       │
                    │  (Yürütücü onayladı, BAP birimi bekliyor)      │
                    └───────────────┬──────────────────────────────────┘
                                    │ bap_incele
                    ┌───────────────┼──────────────────────────────────┐
                    │               │                                  │
                    ▼               ▼                                  ▼
    ┌─────────────────────────┐ ┌──────────────┐         ┌──────────────────────┐
    │      INCELENIYOR        │ │  EKSIK_IADE  │         │     KOMISYONDA       │
    │  (BAP birimi inceliyor) │ │ (Düzeltme    │────────▶│ (Komisyona sunuldu)  │
    └─────────────────────────┘ │  için iade)  │         └──────────┬───────────┘
                                └──────┬───────┘                    │
                                       │ yurutucu_duzelt            │
                                       ▼                            │
                                ┌──────────────────┐                │
                                │   TASLAK         │                │
                                │ (düzeltme yapılıp│                │
                                │  tekrar gönder)  │                │
                                └──────────────────┘                │
                                                                     │
                                    ┌────────────────────────────────┼────────────────────┐
                                    │                                │                    │
                                    ▼                                ▼                    ▼
                    ┌──────────────────────────┐  ┌──────────────────────────┐  ┌──────────────────┐
                    │   HAKEM_DEGERLENDIRME    │  │    SOZLU_SUNUM          │  │ KOMISYON_KARARI  │
                    │  (hakem atandı)          │  │  (sözlü sunum talep     │  │ (doğrudan karar) │
                    └──────────┬───────────────┘  │   edildi)              │  └────────┬─────────┘
                               │                  └──────────┬───────────────┘           │
                               ▼                             ▼                            │
                    ┌──────────────────────┐  ┌────────────────────────┐                  │
                    │ DEGERLENDIRME_TAMAM  │  │  SOZLU_SUNUM_YAPILDI  │                  │
                    └──────────┬───────────┘  └───────────┬────────────┘                  │
                               │                          │                                │
                               └────────────┬─────────────┘                                │
                                            │                                               │
                                            ▼                                               ▼
                              ┌─────────────────────────────────────────────────────────────┐
                              │                    KOMISYON_KARARI                          │
                              │  Kabul veya Ret                                            │
                              └─────────────────────┬───────────────────────────────────────┘
                                                    │
                                    ┌───────────────┼───────────────────┐
                                    │               │                   │
                                    ▼               ▼                   ▼
                    ┌──────────────────────┐  ┌────────────┐  ┌────────────────────┐
                    │       REDDEDILDI     │  │  KABUL     │  │  YENIDEN_INCELEME  │
                    │  (Gerekçeli ret)     │  │            │  │  (revize isteği)    │
                    └──────────────────────┘  └──────┬─────┘  └────────────────────┘
                                                      │ rektor_onay (AP4/AP5/AP8)
                                                      ▼
                                        ┌────────────────────────────────┐
                                        │      PROTOKOL_ASAMASI          │
                                        │  (Belge teslimi + imzalar)    │
                                        └───────────────┬────────────────┘
                                                         │ protokol_imzalandi
                                                         ▼
                                        ┌────────────────────────────────┐
                                        │          AKTIF                 │
                                        │  (Harcama yapılabilir)        │
                                        └───────────────┬────────────────┘
                                                         │
                                              ┌──────────┼──────────┐
                                              │          │          │
                                              ▼          ▼          ▼
                                  ┌──────────────┐ ┌───────────┐ ┌──────────┐
                                  │ ASKIYA_ALINDI│ │ AKTIF     │ │ BITTI    │
                                  │ (en fazla     │ │ (devam)   │ │ (süre    │
                                  │  6 ay)        │ │           │ │ doldu)   │
                                  └──────────────┘ └───────────┘ └────┬─────┘
                                                                       │
                                                                       ▼
                                                         ┌────────────────────────┐
                                                         │   SONUC_RAPORU_BEKLIYOR│
                                                         │  (3 ay içinde rapor)   │
                                                         └───────────┬────────────┘
                                                                     │
                                                         ┌───────────┼────────────┐
                                                         │           │             │
                                                         ▼           ▼             ▼
                                             ┌──────────────┐ ┌──────────┐ ┌──────────────┐
                                             │ RAPOR_KABUL  │ │RAPOR_RET │ │RAPOR_GECIKTI │
                                             │ (proje       │ │(ek süre  │ │(yaptırım     │
                                             │  kapanır)    │ │verilir)  │ │başlatılır)   │
                                             └──────┬───────┘ └──────────┘ └──────────────┘
                                                    │
                                                    ▼
                                             ┌────────────────────────┐
                                             │     KAPANDI            │
                                             │  (resmi kapanış)       │
                                             │  Yayın şartı takip edilir│
                                             └────────────────────────┘
```

### 7.2. Durum Geçiş Validasyonları

Her durum geçişinde aşağıdaki kontroller yapılır:

| Geçiş | Kontrol |
|-------|---------|
| Taslak → BasvuruYapildi | Tüm zorunlu belgeler yüklenmiş mi? Proje türü limitleri aşılmamış mı? |
| Inceleniyor → EksikIade | Eksik belgeler belirtilmiş mi? |
| Komisyonda → HakemDegerlendirme | Bu proje türünde hakem atanabilir mi? |
| Komisyonda → SozluSunum | Bu proje türünde sözlü sunum istenebilir mi? |
| Kabul → ProtokolAsamasi | Rektör onayı gerekiyorsa alınmış mı? |
| KomisyonKarari → YenidenInceleme | Revize gerekçesi belirtilmiş mi? |
| YenidenInceleme → Komisyonda | Revize yapılmış ve tekrar sunulmuş mu? |
| Aktif → AskiyaAlindi | Askı süresi < 6 ay mı? |
| Bitti → SonucRaporuBekliyor | Süre dolmuş mu? |
| SonucRaporuBekliyor → RaporGecikti | 3 aylık sonuç raporu teslim süresi aşılmış mı? |
| RaporGecikti → RaporKabul | Rapor teslim edilmiş ve Komisyonca kabul edilmiş mi? |


### 7.3. Otomatik Zamanlanmış Geçişler (Scheduled Jobs)

Sistem, belirli aralıklarla çalışan background job'lar ile otomatik durum geçişlerini tetikler:

| Job | Periyot | Yaptığı İş |
|-----|---------|------------|
| `checkReportDeadlines` | Günlük | Süresi dolan raporları tespit eder, rapor teslim edilene kadar proje işlemlerini durdurur. Rapor sunmayan yürütücüler için Yönerge kapsamında yaptırım sürecini başlatır |
| `checkAdvanceDeadlines` | Günlük | 45 günü aşan mahsup edilmemiş avansları tespit eder |
| `sendReminderEmails` | Günlük | Yaklaşan/hakem/rapor hatırlatmalarını gönderir |
| `checkProjectDeadlines` | Günlük | Bitiş tarihi yaklaşan projeleri bildirir |
| `checkBursaryDeadlines` | Aylık (25'inde) | Hakediş belgesi teslimini kontrol eder |

---

Sistem, belirli aralıklarla çalışan background job'lar ile otomatik durum geçişlerini tetikler:

| Job | Periyot | Yaptığı İş |
|-----|---------|------------|
| `checkReportDeadlines` | Günlük | Süresi dolan raporları tespit eder, rapor teslim edilene kadar proje işlemlerini durdurur. Rapor sunmayan yürütücüler için Yönerge kapsamında yaptırım sürecini başlatır |r: (1) 1 ay gecikme → proje işlemlerini durdurur, (2) +15 gün daha (toplam 45 gün) → iptal sürecini başlatır |
| `checkAdvanceDeadlines` | Günlük | 45 günü aşan mahsup edilmemiş avansları tespit eder |
| `sendReminderEmails` | Günlük | Yaklaşan/hakem/rapor hatırlatmalarını gönderir |
| `checkProjectDeadlines` | Günlük | Bitiş tarihi yaklaşan projeleri bildirir |
| `checkBursaryDeadlines` | Aylık (25'inde) | Hakediş belgesi teslimini kontrol eder |

---

## 8. Önemli Akış Diyagramları

### 8.1. Başvuru Akışı (Adım Adım)

```
Yürütücü                        BAP Birimi                    Komisyon                  Hakem
    │                               │                            │                        │
    │ 1. Taslak oluşturur           │                            │                        │
    │ 2. Belgeleri yükler           │                            │                        │
    │ 3. Bütçeyi girer              │                            │                        │
    │    │                          │                            │                        │
    │ 4. Yürütücü onayı             │                            │                        │
    │───┼──────────────────────────▶│                            │                        │
    │   │                           │                            │                        │
    │   │                   5. Biçimsel inceleme                │                        │
    │   │                           │                            │                        │
│   │               ┌───────────┼──────────┐              │                        │
│   │               │           │           │              │                        │
│   │          Eksik var    Eksik yok      │              │                        │
│   │               │           │           │              │                        │
│   │◀──── iade ────│           │           │              │                        │
│   │               │           ▼           │              │                        │
│   │               │    6. Komisyona       │              │                        │
│   │               │       sunar           │              │                        │
    │   │               │           │              │            │                        │
    │   │               │           ▼              │            │                        │
    │   │               │    7. Değerlendirme      │            │                        │
    │   │               │    ┌──────┼──────┐       │            │                        │
    │   │               │    │      │      │      │            │                        │
    │   │               │ Hakem  Sözlü  Karar     │            │                        │
    │   │               │    │    Sunum   │       │            │                        │
    │   │               │    │      │      │      │            │                        │
    │   │               │    ▼      ▼      ▼      │            │                        │
    │   │               │ Atama  Davet  Kabul/Ret │            │                        │
    │   │               │    │      │      │      │            │                        │
    │   │               │    ▼      ▼      ▼      │            │                        │
    │   │               │  8. Komisyon kararı     │            │                        │
    │   │               │       │                 │            │                        │
    │   │               │  ┌────┼────┐            │            │                        │
    │   │               │  │    │    │            │            │                        │
    │   │               │Kabul  Ret  Revize       │            │                        │
    │   │               │       │                 │            │                        │
    │   │               │       ▼                 │            │                        │
    │   │               │   RED_BILDIRIMI         │            │                        │
    │   │               │       │                 │            │                        │
    │   │               │       ▼                 │            │                        │
    │   │               │   PROTOKOL_AŞAMASI      │            │                        │
    │◀──┼────────────────────────│                 │            │                        │
    │   │                       │                  │            │                        │
    │   │             9. Protokol imzalanır       │            │                        │
    │   │                       │                  │            │                        │
    │   │              10. PROJE AKTİF             │            │                        │
    ▼                       ▼                      ▼            ▼                        ▼
```

### 8.2. Hakem Değerlendirme Akışı

```
Komisyon                       Sistem                      Hakem
    │                             │                           │
    │ 1. Hakem ata kararı         │                           │
    │────────────────────────────▶│                           │
    │                             │                           │
    │                     2. Hakem seçimi                    │
    │                        (havuzdan)                      │
    │                             │                           │
    │                     3. Davet e-postası                │
    │                             │──────────────────────────▶│
    │                             │                           │
    │                             │              ┌────────────┼────────────┐
    │                             │              │            │             │
    │                             │          Kabul(7g)    Ret(7g)     Yanıtsız
    │                             │              │            │             │
    │                             │◀──── kabul ──│            │             │
    │                             │              │            │             │
    │                     4. Değerlendirme       │            │     5. Yeni hakem
    │                        ekranı açılır       │            │        atanır
    │                             │              │            │             │
    │                             │    ┌─────────┼────────┐   │             │
    │                             │    │         │        │   │             │
    │                             │ 14 gün    14+5 gün  24 gün              │
    │                             │    │         │        │                  │
    │                             │    │    1.hatırlatma │                  │
    │                             │    │         │        │                  │
    │                             │    │    2.hatırlatma │                  │
    │                             │    │         │        │                  │
    │                             │◀──── teslim ──│        │                  │
    │                             │              │         │                  │
    │                     6. Değerlendirme       │      İptal               │
    │                        sonucu iletilir     │      (ödeme yok)         │
    │◀────────────────────────────│              │         │                  │
    │                             │              │         │                  │
    ▼                             ▼              ▼         ▼                  ▼
```

### 8.3. Harcama Süreci Akışı

```
Yürütücü                  BAP Birimi                    Strateji GDB
    │                          │                              │
    │ 1. Satın alma talebi     │                              │
    │    (EK-4/A-1 formu)      │                              │
    │─────────────────────────▶│                              │
    │                          │                              │
    │                  2. İnceleme                            │
    │                  ┌───────┼───────┐                      │
    │                  │       │       │                      │
    │              Uygun    Eksik    Red                      │
    │                  │       │       │                      │
    │                  │       ▼       │                      │
    │                  │  İade ◀───────│                      │
    │◀──────── iade ───│       │       │                      │
    │                  │       │       │                      │
    │                  ▼       │       │                      │
    │          3. Onay verir   │       │                      │
    │                  │       │       │                      │
    │◀───── onay ──────│       │       │                      │
    │                  │       │       │                      │
    │    ┌─────────────┼───────┼───────┼──────────────┐       │
    │    │             │       │       │              │        │
    │    ▼             ▼       │       ▼              ▼        │
    │Direk satınalma  Avans   │   Seyahat        Kongre      │
    │    │             │       │       │              │        │
    │    │      4. Avans açılır│      │              │        │
    │    │             │       │       │              │        │
    │    ▼             ▼       │       ▼              ▼        │
    │  Fatura     45 gün      │   30 gün          30 gün     │
    │  ödemesi    mahsup       │   mahsup          mahsup     │
    │    │             │       │       │              │        │
    │    │             │       │       │              │        │
    │    ▼             ▼       ▼       ▼              ▼        │
    │             5. Ödeme emri                                │
    │────────────────────────────────────────────────────────▶│
    │                          │                              │
    │                          │              6. Ödeme yapılır│
    │                          │                              │
    ▼                          ▼                              ▼
```

### 8.4. Kontenjan Kontrolü Akışı

Bu akış, yeni bir proje başvurusu anında (veya yürütücü onayında) çalışır.

```
Yeni başvuru gelir
       │
       ▼
┌─────────────────────────────┐
│ Yürütücünün eşzamanlı       │
│ yürüttüğü projeler sayılır  │
└─────────────┬───────────────┘
              │
              ▼
┌─────────────────────────────┐
│ Proje türünün limiti ile    │
│ karşılaştırılır             │
│ (project_types.eszamanli_   │
│  limit)                     │
└─────────────┬───────────────┘
              │
       ┌──────┼──────┐
       │      │      │
    Aşıyor  Sınırda  Geçti
       │      │      │
       │      │      ▼
       │      │  ┌─────────────────────────────┐
       │      │  │ AP1-AP4 çapraz kısıt kontrolü│
       │      │  │ (aynı anda sadece 1 AP1-AP4)│
       │      │  └─────────────┬───────────────┘
       │      │                │
       │      │          ┌─────┼─────┐
       │      │          │     │     │
       │      │       Uygun  İhlal  Yayın şartı
       │      │          │     │     kilidi var
       │      │          │     │         │
       │      │          │     │         ▼
       │      │          │     │  ┌────────────────┐
       │      │          │     │  │ Eski projelerde │
       │      │          │     │  │ yayın şartı     │
       │      │          │     │  │ sağlanmamış     │
       │      │          │     │  └───────┬────────┘
       │      │          │     │          │
       │      │          │     ▼          ▼
       │      │          │  BAŞVURU ENGELLENİR
       │      │          │  (hata mesajı gösterilir)
       │      │          │
       ▼      ▼          ▼
  BAŞVURU ENGELLENİR   BAŞVURU DEVAM EDEBİLİR
```

---

## 9. Sınıf/Model Tasarımı

### 9.1. Temel Entity Şablonu

Tüm entity'ler aşağıdaki ortak alanları paylaşır (BaseEntity):

```
BaseEntity:
  id: UUID (PK)
  createdAt: DateTime (default: now)
  updatedAt: DateTime (default: now, onUpdate: now)
  deletedAt: DateTime? (soft delete için)
```

### 9.2. Domain Servisleri

İş mantığını taşıyan ana servis sınıfları:

```
ProjectService
├── create(userId, projectTypeCode, data)
├── submitForReview(projectId)        // Yürütücü onayına gönder
├── approveByExecutive(projectId)     // Yürütücü onayı
├── reviewByUnit(projectId, action)   // BAP birimi incelemesi (approve/return)
├── assignReviewer(projectId, reviewerId)
├── convertProject(projectId, targetTypeCode)
├── getTimeline(projectId)
├── checkEşzamanlıLimit(userId, projectTypeCode)
├── checkÇaprazKısıt(userId, projectTypeCode)
└── checkYayınŞartıKilidi(userId)

BudgetService
├── addItem(projectId, data)
├── updateItem(projectId, itemId, data)
├── removeItem(projectId, itemId)
├── requestTransfer(projectId, sourceItemId, targetItemId, amount, reason)
├── approveTransfer(transferId)
├── requestExtension(projectId, amount, reason)
├── calculatePerformanceIncrease(projectId, userId)  // AP1/AP2/AP3 için
└── validateProformaMatch(budgetItemId, documentId)

EvaluationService
├── startEvaluation(projectId, type)      // Komisyon başlatır
├── inviteReviewer(evaluationId, reviewerId)
├── acceptInvitation(evaluationId)
├── rejectInvitation(evaluationId)
├── submitEvaluation(evaluationId, score, opinion, decision)
├── sendReminder(evaluationId)
├── cancelEvaluation(evaluationId)
└── getReviewerStatistics(reviewerId, year)

SpendingService
├── createPurchaseRequest(projectId, data)
├── approvePurchase(purchaseId)
├── rejectPurchase(purchaseId, reason)
├── createAdvance(purchaseRequestId, type, amount)
├── closeAdvance(advanceId, documentId)
├── checkAdvanceDeadlines()              // Scheduled job
├── getRemainingBudget(projectId)
└── validateBudgetSufficiency(projectId, amount)

ReportService
├── submitReport(projectId, reportType, file)
├── evaluateReport(reportId, decision, notes)
├── checkDeadline(projectId, reportType)
├── applyPenalty(projectId)              // Gecikme yaptırımı
├── liftPenalty(projectId)               // Rapor teslim edilince
└── generateReportTemplate(projectId, reportType)

ClosureService
├── closeProject(projectId, criterionId)
├── checkYayınŞartı(projectId)
├── applyYayınİbaresiControl(publication)
├── releaseQuota(projectId)              // Yayın şartı sağlanınca
└── getProjectsBlockingQuota(userId)

BursaryService
├── addStudent(projectId, studentId)
├── removeStudent(projectId, studentId)
├── submitBursaryDocument(studentId, projectId, month, year, documentId)
├── calculatePayment(studentId, projectId, month, year)
├── processPayment(paymentId)
├── checkBursaryEligibility(studentId)
└── checkBudgetLimit(projectId)          // Bütçenin %30'u kuralı

NotificationService
├── sendEmail(to, template, variables)
├── sendSystemNotification(userId, message, type)
├── sendSms(userId, message)            // Opsiyonel
└── getUnreadNotifications(userId)

ParameterService
├── getValue(group, key)                 // Önbellekten okur
├── setValue(group, key, value)          // Admin işlemi
├── getProjectTypeConfig(typeCode)       // Proje türü kurallarını döndürür
├── getClosureMatrix(projectType, field) // Kapatma matrisini döndürür
└── invalidateCache(group)               // Admin değişikliği sonrası
```

### 9.3. Validasyon Stratejisi

Validasyonlar üç katmanda yapılır:

**Katman 1 — Controller/Input Validation (DTO seviyesi):**
- Alan tipleri, zorunlu alanlar, format kontrolleri (e-posta, URL, vb.)
- `class-validator` dekoratörleri (NestJS) veya FluentValidation (.NET)

**Katman 2 — Servis Validasyonu (Business Rule):**
- Limit kontrolleri (kontenjan, bütçe, başvuru sıklığı)
- Durum geçiş kontrolleri
- Mevzuat kuralları (örn. "yolluk kalemine aktarım yasak")

**Katman 3 — Veritabanı Konstraint:**
- Unique constraint (aynı bursiyere aynı ay iki ödeme)
- Foreign key
- Check constraint (AGNO ≥ 2.50, süre ≤ 36 ay)

---

## 10. Ekran/UI Tasarımı (Şematik)

### 10.1. Ana Ekran Yapısı (Dashboard)

Her kullanıcı rolü için farklı bir ana sayfa:

**Yürütücü Dashboard:**
```
┌──────────────────────────────────────────────────────────────────────┐
│  Hoş Geldiniz, Prof. Dr. Ahmet Yılmaz                  [Profil] [Çıkış] │
├──────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐               │
│  │  Aktif Proje  │  │  Bekleyen    │  │  Yayın Şartı  │              │
│  │      5        │  │  İşlem: 3    │  │  Eksik: 2     │              │
│  └──────────────┘  └──────────────┘  └──────────────┘               │
│                                                                      │
│  ┌──────────────────────────────────────────────────────────────┐   │
│  │  PROJELERİM                         [Yeni Proje Başvurusu]   │   │
│  ├──────────────────────────────────────────────────────────────┤   │
│  │  Proje No │ Tür │ Başlık │ Durum │ İlerleme │ Son İşlem      │   │
│  │  BAP-2025-001 │ TP1 │ ...   │ AKTIF  │ ██████░░  │ 3 gün önce  │   │
│  │  BAP-2025-002 │ AP2 │ ...   │ TASLAK │ ░░░░░░░░  │ -           │   │
│  │  BAP-2025-003 │ AP3 │ ...   │ İNCELEME│ ██░░░░░░  │ 1 gün önce  │   │
│  └──────────────────────────────────────────────────────────────┘   │
│                                                                      │
│  ┌─────────────────────────┐  ┌─────────────────────────────────┐   │
│  │  YAKLAŞAN TESLİMLER     │  │  BİLDİRİMLER                    │   │
│  │  - Ara rapor: 15 gün    │  │  - Hakem değerlendirmeniz var   │   │
│  │  - Proje bitiş: 45 gün  │  │  - Bütçe aktarımı onaylandı     │   │
│  └─────────────────────────┘  └─────────────────────────────────┘   │
└──────────────────────────────────────────────────────────────────────┘
```

### 10.2. Başvuru Formu (Proje Türüne Göre Dinamik)

Seçilen proje türüne göre form alanları dinamik olarak değişir:

```
┌──────────────────────────────────────────────────────────────────────┐
│  YENİ BAŞVURU                                                    │
├──────────────────────────────────────────────────────────────────────┤
│  Proje Türü: [TP1 ▼]                                                │
│  ┌────────────────────────────────────────────────────────────────┐ │
│  │  TP1 - Açıklama: Lisans Öğrencisi Araştırma Projesi          │ │
│  │  Bütçe Tavanı: 10.000 ₺ · Süre: 6-12 ay · Yayın Şartı: Yok │ │
│  └────────────────────────────────────────────────────────────────┘ │
│                                                                      │
│  ┌─────────── BAŞVURU BİLGİLERİ ──────────────────────────────────┐ │
│  │  Proje Başlığı: [________________________________________]     │ │
│  │  Proje Özeti: [___________________________________________]    │ │
│  │  Anahtar Kelimeler: [___________________________]              │ │
│  └────────────────────────────────────────────────────────────────┘ │
│                                                                      │
│  ┌─────────── ÖĞRENCİ BİLGİLERİ ──────────────────────────────────┐ │
│  │  Öğrenci: [Ad Soyad yazın...]  [Öğrenci No sorgula]          │ │
│  │  AGNO: 3.20 (Güncel: 10.01.2025) ✓                            │ │
│  │  4 yarıyıl tamam: Evet ✓                                      │ │
│  │  Daha önce TP1: Hayır ✓                                       │ │
│  └────────────────────────────────────────────────────────────────┘ │
│                                                                      │
│  ┌─────────── BÜTÇE ──────────────────────────────────────────────┐ │
│  │  + Bütçe Kalemi Ekle                                          │ │
│  │  ┌────┬────────────┬────────┬──────┬───────┬──────────┐      │ │
│  │  │ No │ Kalem      │ Açıkl. │ Mik. │ B.Fiyat│ Toplam  │      │ │
│  │  │ 1  │ 03.1 Tük.Mal│ Kimyasal│ 5   │ 500 ₺  │ 2.500 ₺│      │ │
│  │  │ 2  │ 03.2 Makine │ Analiz  │ 1   │ 5.000 ₺│ 5.000 ₺│      │ │
│  │  │    │            │ Cihazı  │     │        │        │      │ │
│  │  └────┴────────────┴────────┴──────┴───────┴──────────┘      │ │
│  │  Toplam: 7.500 ₺ / 10.000 ₺                                   │ │
│  └────────────────────────────────────────────────────────────────┘ │
│                                                                      │
│  ┌─────────── BELGELER ──────────────────────────────────────────┐ │
│  │  [√] Proje Başvuru Dosyası (EK-1/A)            [Yüklendi]    │ │
│  │  [√] Proforma Faturalar                        [2 dosya]     │ │
│  │  [ ] Teknik Şartname (Word)                    [Yükle]       │ │
│  │  [√] Öğrenci Belgesi                           [Yüklendi]    │ │
│  │  [√] Transkript (AGNO≥2.50)                    [Yüklendi]    │ │
│  │  [ ] Etik Kurul İzni (gerekliyse)              [Yükle]       │ │
│  │  [√] Feragatname                               [Yüklendi]    │ │
│  └────────────────────────────────────────────────────────────────┘ │
│                                                                      │
│  [Taslak Kaydet] [Yürütücü Onayına Gönder]                          │
└──────────────────────────────────────────────────────────────────────┘
```

### 10.3. BAP Birimi Paneli — İnceleme Ekranı

```
┌──────────────────────────────────────────────────────────────────────┐
│  BEKLEYEN BAŞVURULAR (12)                          [Filtrele] [Ara] │
├──────────────────────────────────────────────────────────────────────┤
│  ┌──────────────────────────────────────────────────────────────┐   │
│  │  BAP-2025-015 · TP1 · "Mikrobiyal..." · Yürütücü: A.Yılmaz  │   │
│  │  Başvuru: 15.07.2025 · Süre: 7/12 gün                       │   │
│  │                                                              │   │
│  │  Belge Kontrol Listesi:                                      │   │
│  │  [✓] Başvuru Dosyası ✓                                      │   │
│  │  [✓] Proforma Faturalar ✓                                   │   │
│  │  [✗] Teknik Şartname (Eksik - Word formatı değil)          │   │
│  │  [✓] Öğrenci Belgesi ✓                                     │   │
│  │  [✓] Transkript ✓                                          │   │
│  │  [—] Etik Kurul (gerekmiyor)                                │   │
│  │                                                              │   │
│  │  Bütçe Uyumu:                                               │   │
│  │  Kalem 1: 2.500 ₺ → Proforma: 2.500 ₺ ✓                   │   │
│  │  Kalem 2: 5.000 ₺ → Proforma: 5.000 ₺ ✓                   │   │
│  │                                                              │   │
│  │  [Eksik İade] [Komisyona Sun]                                │   │
│  └──────────────────────────────────────────────────────────────┘   │
│  ┌──────────────────────────────────────────────────────────────┐   │
│  │  BAP-2025-016 · AP2 · ...                                    │   │
│  └──────────────────────────────────────────────────────────────┘   │
└──────────────────────────────────────────────────────────────────────┘
```

### 10.4. Admin Parametre Yönetim Ekranı

```
┌──────────────────────────────────────────────────────────────────────┐
│  PARAMETRE YÖNETİMİ                                       [Yeni Ekle]│
├──────────────────────────────────────────────────────────────────────┤
│  Grup: [Tümü ▼] │ Ara: [___________________________]                │
│                                                                      │
│  ┌────────┬────────────────────────┬────────────────┬────────┬─────┐ │
│  │ Grup   │ Anahtar               │ Değer          │ Aktif  │ İşlem│ │
│  ├────────┼────────────────────────┼────────────────┼────────┼─────┤ │
│  │ butce  │ ap1.butce_tavani      │ 100000         │ ✓      │ ✎   │ │
│  │ butce  │ ap2.butce_tavani      │ 75000          │ ✓      │ ✎   │ │
│  │ butce  │ ap2.perf_artis_kat1   │ 0.30           │ ✓      │ ✎   │ │
│  │ butce  │ ap2.perf_artis_kat3   │ 0.70           │ ✓      │ ✎   │ │
│  │ sure   │ tp1.max_sure_ay       │ 12             │ ✓      │ ✎   │ │
│  │ sure   │ tp1.ek_sure_ay        │ 1              │ ✓      │ ✎   │ │
│  │ konten │ ap1.eszamanli_limit   │ 1              │ ✓      │ ✎   │ │
│  │ konten │ arastirmaci_max_proje │ 5              │ ✓      │ ✎   │ │
│  │ agno   │ tp1.agno_min          │ 2.50           │ ✓      │ ✎   │ │
│  │ agno   │ tp1.kacinci_yarıyıl   │ 4              │ ✓      │ ✎   │ │
│  └────────┴────────────────────────┴────────────────┴────────┴─────┘ │
└──────────────────────────────────────────────────────────────────────┘
```

---

## 11. Güvenlik Tasarımı

### 11.1. Kimlik Doğrulama (Authentication)

- **JWT (JSON Web Token)** tabanlı kimlik doğrulama
- Access token: 15 dakika (kısa ömürlü)
- Refresh token: 7 gün (uzun ömürlü, httpOnly cookie'de saklanır)
- Token içinde: `{ userId, email, rol, birimId }`
- Şifreler: bcrypt (12 round) veya Argon2id ile hash'lenir

### 11.2. Yetkilendirme (Authorization)

**Rol Bazlı Yetki (RBAC):**

| Rol | Yetkileri |
|-----|-----------|
| Yürütücü | Kendi projelerini görme/düzenleme, harcama talebi, rapor yükleme |
| Araştırmacı | Görevli olduğu projeleri görme |
| Bursiyer | Kendi hakediş belgelerini yükleme, ödeme durumunu görme |
| Hakem | Atandığı değerlendirmeleri görme (kör), değerlendirme girme |
| BAPPersonel | Tüm başvuruları görme, biçimsel inceleme, satın alma onayı |
| Koordinatör | BAPPersonel yetkilerine ek olarak: süreç koordinasyonu, komisyon sekretaryası, satın alma süreç yönetimi |
| Komisyon | Değerlendirme aşamasındaki projeleri görme, karar verme |
| Rektor | Onay gereken projeleri görme |
| EnstitüFakulte | Tez konusu onayı (TP2/TP3), yönetim kurulu kararları, geçici yürütücü görüşü |
| Admin | Parametre yönetimi, kullanıcı yönetimi, log görüntüleme |

**Yetkilendirme Dekoratörleri (NestJS örneği):**
```
@Roles('BAPPersonel', 'Komisyon')
@Get('/pending')
getPendingProjects() { ... }
```

### 11.3. Kör Değerlendirme

Hakem değerlendirmesinde, yürütücü adı ve kimliği gizlenir. `evaluations` tablosunda hakeme gösterilen bilgiler:
- Proje başlığı ve özeti (görünür)
- Başvuru dosyası (görünür, ancak yürütücü adı silinmiş PDF)
- Yürütücü adı (gizli)
- Yürütücü birimi (gizli)

**PDF Anonimleştirme Mekanizması:**
Sistem, başvuru dosyası yüklendiğinde orijinal PDF'i korur ve ayrıca bir "anonim kopya" oluşturur:
1. PDF metin katmanı taranır, yürütücü adı, araştırmacı adları ve birim bilgisi [GİZLİ] etiketiyle değiştirilir
2. Dosya adından da kimlik bilgileri temizlenir (proje_no ile yeniden adlandırılır)
3. Anonim kopya `project_documents` tablosunda ayrı bir kayıt olarak `anonim_mi = true` flag'i ile saklanır
4. Hakeme yalnızca anonim kopya gösterilir; orijinal dosyaya yalnızca Komisyon ve BAPPersonel erişebilir

### 11.4. Veri Sahipliği

- Yürütücü yalnızca **kendi** projelerini görebilir (WHERE yurutucu_id = userId)
- Araştırmacı yalnızca **görevli olduğu** projeleri görebilir (JOIN project_researchers)
- BAPPersonel tüm projeleri görebilir (rol bazlı)

### 11.5. KVKK Uyumu

- **Saklama süresi:** Proje kapanışından itibaren 10 yıl (mevzuat gereği)
- **İmha:** Süre sonunda otomatik anonimleştirme veya silme
- **Veri minimizasyonu:** Sadece ihtiyaç duyulan veriler toplanır
- **Aydınlatma metni:** İlk girişte kullanıcıya gösterilir
- **Veri sahibi hakları:** API üzerinden erişim/silme/düzeltme talebi

---

## 12. Entegrasyon Tasarımı

### 12.1. Entegrasyon Mimarisi

BAP sistemi, aşağıdaki dış sistemlerle entegre olur. Her entegrasyon için bir **adaptör (adapter)** deseni kullanılır:

```
BAP Sistemi
    │
    ├── APBS Adaptörü → APBS (REST API ile sorgu)
    ├── YÖKSİS Adaptörü → YÖKSİS (Web Servis / SOAP)
    ├── E-posta Adaptörü → SMTP / Kurum Exchange
    ├── Strateji Adaptörü → Muhasebe Sistemi (Dosya/REST API)
    ├── Taşınır Adaptörü → Taşınır Kayıt (REST API)
    ├── ÖBS Adaptörü → Öğrenci Bilgi Sistemi (REST API)
    ├── Kütüphane Adaptörü → Kütüphane Sistemi (REST API)
    └── Bilgi İşlem Adaptörü → Bilgi İşlem DB (REST API)
```

### 12.2. Entegrasyon Detayları

| Entegrasyon | Yöntem | Veri | Frekans |
|-------------|--------|------|---------|
| APBS | REST GET | Akademik özgeçmiş güncellik kontrolü | Başvuru anında |
| YÖKSİS | Web Servis | Unvan, birim doğrulama | Başvuru anında |
| E-posta | SMTP | Bildirim, hatırlatma, davet | Olay bazlı |
| Strateji GDB | Dosya/API | Ödeme emri, SGK kesintisi | Harcama anında |
| Taşınır | REST POST | Dayanıklı taşınır kaydı | Satın alma sonrası |
| ÖBS | REST GET | Öğrenci kaydı, AGNO sorgulama | Başvuru anında + periyodik |
| Kütüphane | REST POST | Kitap demirbaş kaydı | Kitap alımında |
| Bilgi İşlem | REST POST | Yazılım kaydı | Yazılım alımında |

### 12.3. Hata Yönetimi (Entegrasyon)

- Her dış sistem çağrısı **circuit breaker** deseni ile korunur
- 3 başarısız denemeden sonra sistem "entegrasyon hatası" moduna geçer
- Kullanıcıya "Sistem şu anda kullanılamıyor, lütfen daha sonra tekrar deneyin" mesajı gösterilir
- Hatalar ayrı bir log tablosunda (`integration_logs`) tutulur
- Periyodik retry job'ı ile başarısız entegrasyonlar tekrar dener

---

## 13. Parametrik Yapı Tasarımı

### 13.1. Parametre Hiyerarşisi

```
parameters tablosu
    │
    ├── Grup: butce
    │   ├── ap1.butce_tavani = 100000
    │   ├── ap1.perf_artis_kat1 = 0.30
    │   ├── ap1.perf_artis_kat2 = 0.30
    │   ├── ap1.perf_artis_kat3 = 0.50
    │   ├── ap1.perf_artis_kat4 = 0.50
    │   ├── tp1.butce_tavani = 10000
    │   └── ...
    │
    ├── Grup: sure
    │   ├── tp1.min_sure_ay = 6
    │   ├── tp1.max_sure_ay = 12
    │   ├── tp1.ek_sure_ay = 1
    │   └── ...
    │
    ├── Grup: kontenjan
    │   ├── tp1.eszamanli_limit = 5
    │   ├── ap1_4.toplam_eszamanli = 1
    │   └── ...
    │
    ├── Grup: hakem
    │   ├── davet_cevap_suresi_gun = 7
    │   ├── degerlendirme_suresi_gun = 14
    │   ├── ek_sure_gun = 5
    │   ├── hatirlatma_sayisi = 2
    │   └── yillik_odeme_limit = 6
    │
    ├── Grup: rapor
    │   ├── sonuc_raporu_teslim_suresi_ay = 3
    │   ├── ara_rapor_suresi_ay = 6
    │   └── gecikme_yaptirim_1_gun = 30
    │
    ├── Grup: kapatma
    │   ├── olcut_1_alan_gruplari = ["A","B","C","D","E","F","G"]
    │   ├── olcut_1_proje_turleri = ["TP2","TP3","AP1","AP2","AP3","AP4","AP5","AP6","AP7","AP8"]
    │   └── ...
    │
    └── Grup: bildirim
        ├── eposta_bildirim_aktif = true
        ├── sms_bildirim_aktif = false
        └── ...
```

### 13.2. Önbellek Stratejisi

Parametreler sık okunduğu için **Redis** (veya in-memory cache) ile önbelleğe alınır:

1. Sistem ayağa kalktığında tüm parametreler önbelleğe yüklenir
2. Admin bir parametreyi değiştirdiğinde:
   - Veritabanı güncellenir
   - İlgili önbellek anahtarı geçersiz kılınır (invalidated)
   - Bir sonraki okumada yeniden veritabanından yüklenir
3. Cache miss durumunda veritabanından okunur ve önbelleğe yazılır

### 13.3. Parametrelerin Kodda Kullanımı

```typescript
// Örnek: Bir proje türünün bütçe tavanını okuma
const butceTavani = await parameterService.getValue('butce', 'tp1.butce_tavani');
// 10000 döndürür

// Proje türü yapılandırmasını toplu okuma
const tp1Config = await parameterService.getProjectTypeConfig('TP1');
// {
//   butceTavani: 10000,
//   minSure: 6,
//   maxSure: 12,
//   ekSure: 1,
//   eszamanliLimit: 5,
//   ...
// }
```

### 13.4. Yönetim Ekranı Validasyonu

Admin parametre değiştirirken aşağıdaki kontroller yapılır:
- Sayısal alanlar için tip kontrolü
- Oran alanları için 0-1 aralığı
- Süre alanları için pozitif tam sayı
- Değişiklik loglanır (audit_logs)

---

## 14. Hata Yönetimi ve Log Stratejisi

### 14.1. Hata Kodu Yapısı

```
BAP-{Modül}-{Numara}

Örnek:
BAP-PRJ-001: Proje bulunamadı
BAP-PRJ-002: Proje türü geçersiz
BAP-BUD-001: Bütçe limiti aşıldı
BAP-EVL-001: Hakem davet süresi doldu
BAP-RPT-001: Rapor teslim süresi doldu
```

### 14.2. API Hata Formatı

```json
{
  "success": false,
  "error": {
    "code": "BAP-BUD-001",
    "message": "Bütçe limiti aşıldı",
    "details": {
      "limit": 10000,
      "talep": 12500,
      "fark": -2500
    },
    "timestamp": "2025-01-15T10:30:00Z",
    "path": "/api/projects/123/budget/items"
  }
}
```

### 14.3. Log Kategorileri

| Kategori | Açıklama | Saklama |
|----------|----------|---------|
| audit_logs | İşlem geçmişi (değişmez) | 10 yıl |
| error_logs | Sistem hataları | 30 gün |
| access_logs | API erişim logları | 90 gün |
| integration_logs | Dış sistem hataları | 90 gün |
| scheduled_job_logs | Zamanlanmış iş logları | 30 gün |

### 14.4. Transaction Yönetimi

Kritik işlemler **database transaction** içinde yapılır:

```typescript
// Örnek: Proje başvurusu onaylama (transaction)
async approveProject(projectId: string) {
  return this.prisma.$transaction(async (tx) => {
    // 1. Proje durumunu değiştir
    const project = await tx.project.update({
      where: { id: projectId },
      data: { durum: 'BASVURU_YAPILDI' }
    });

    // 2. Kontenjan kontrolü (race condition koruması)
    const aktifSayi = await tx.project.count({
      where: { yurutucuId: project.yurutucuId, durum: { notIn: ['TASLAK', 'REDDEDILDI', 'KAPANDI'] } }
    });
    if (aktifSayi >= projectType.eszamanliLimit) {
      throw new BusinessException('BAP-PRJ-003', 'Eşzamanlı proje limiti aşıldı');
    }

    // 3. Audit log yaz
    await tx.auditLog.create({
      data: {
        kullaniciId: userId,
        islemTuru: 'PROJE_ONAYLA',
        varlikTuru: 'Project',
        varlikId: projectId,
        eskiDeger: oldState,
        yeniDeger: newState,
      }
    });

    return project;
  });
}
```

---

## 15. Performans ve Ölçeklenebilirlik

### 15.1. Veritabanı Performansı

**İndeks Stratejisi:**
- `projects` tablosu: `(yurutucu_id), (durum), (tur_id), (createdAt)`
- `project_researchers`: `(kullanici_id), (proje_id)` — composite index
- `audit_logs`: `(varlik_turu, varlik_id), (kullanici_id), (zaman)`
- `budget_items`: `(proje_id)`
- `evaluations`: `(proje_id), (degerlendirici_id)`

**Sorgu Optimizasyonu:**
- Dashboard sorguları için materialized view (Örn: `mv_kullanici_proje_ozet`)
- Sayfalama için cursor-based pagination (offset yerine, büyük tablolar için)

### 15.2. Dosya Yönetimi

- Her belge en fazla 50 MB
- Kabul edilen formatlar: PDF, DOCX, JPG, PNG
- Dosyalar doğrudan veritabanında değil, **MinIO/S3** veya fiziksel dosya sisteminde saklanır
- Veritabanında sadece dosya yolu (path) tutulur
- Virus scanning: Yüklenen dosyalar taranır (ClamAV veya benzeri)

### 15.3. Eşzamanlılık (Concurrency)

Kontenjan ve limit kontrollerinde **pessimistic locking** veya **optimistic locking** kullanılır:

```typescript
// Optimistic locking örneği (version alanı ile)
const project = await tx.project.findUnique({
  where: { id: projectId }
});
// ... iş mantığı ...
await tx.project.update({
  where: { id: projectId, version: project.version },
  data: { durum: 'YENI_DURUM', version: project.version + 1 }
});
// Eğer version eşleşmezse: PrismaRecordNotFoundError → retry
```

### 15.4. Ölçeklenebilirlik

- **Stateless backend:** API sunucuları yatay ölçeklenebilir (birden fazla instance)
- **Session:** Sunucuda session tutulmaz, JWT token ile çalışılır
- **File storage:** MinIO/S3 ile merkezi dosya deposu
- **Background jobs:** Ayrı bir worker process'te çalışır
- **Cache:** Redis ile parametre ve sık okunan veriler önbelleklenir

---

## Ek A: Sık Kullanılan İş Kuralları (Kontratlar)

Bu bölüm, geliştiricilerin sık başvurduğu iş kurallarını özetler:

| Kural ID | Açıklama | Kontrol Yeri |
|----------|----------|--------------|
| BK-001 | AGNO ≥ 2.50 (TP1) | Başvuru anında |
| BK-002 | İlk 4 yarıyıl tamamlanmış olmalı (TP1) | Başvuru anında |
| BK-003 | TP1 kariyer boyu 1 kez | Başvuru anında |
| BK-004 | AP1 kariyer boyu 1 kez | Başvuru anında |
| BK-005 | Aynı anda en fazla 1 adet AP1-AP4 projesi | Başvuru anında |
| BK-006 | AP1-AP4 yürüten AP1-AP4 başvuramaz (çapraz kısıt) | Başvuru anında |
| BK-007 | Araştırmacı en fazla 5 projede (TP1/AP9 hariç) | Araştırmacı ekleme anında |
| BK-008 | Yolluk/kongre/burs kalemine aktarım YASAK | Bütçe aktarım anında |
| BK-009 | Açık erişim ödeneği aktarılamaz | Bütçe aktarım anında |
| BK-010 | Burs ≥ bütçenin %30'u olamaz | Bütçe onay anında |
| BK-011 | 45 günde mahsup edilmeyen avans → uyarı | Günlük job |
| BK-012 | 6 ayda bir ara rapor (süre > 12 ay ise) | Rapor zamanı |
| BK-013 | Sonuç raporu 3 ay içinde | Proje bitişi sonrası |
| BK-014 | Protokol 1 ay içinde imzalanmazsa → iptal | Proje kabulü sonrası |
| BK-015 | 2 yıl destek yasağı → tüm başvurular engellenir | Başvuru anında |
| BK-016 | Yayın şartı sağlanmamış proje kontenjan işgal eder | Kontenjan kontrolünde |

---

## Ek B: Veritabanı Migration Stratejisi

- **Araç:** Prisma Migrate / EF Core Migrations
- **İsimlendirme:** `YYYY_MM_DD_HHmm_descriptive_name`
- **Her migration:** Geri alınabilir (down script)
- **Seed data:** Proje türleri (12 adet), varsayılan parametreler, admin kullanıcısı
- **Değişiklik:** Tablo silmek yerine soft delete + nullable yeni alan

---

## Ek C: Test Stratejisi

| Test Türü | Kapsam | Araç |
|-----------|--------|------|
| Unit Test | Domain servisleri, validasyon kuralları | Jest / Vitest / xUnit |
| Integration Test | API uçları, veritabanı işlemleri | Supertest / Testcontainers |
| E2E Test | Kritik akışlar (başvuru→kabul→harcama→rapor) | Playwright / Cypress |
| Load Test | Başvuru dönemi zirvesi (500 eşzamanlı) | k6 / Artillery |

---

*Doküman Sonu · 23 Temmuz 2026*
