# SESSION.md - İlerleme Kaydı

## Proje
**BAP (Bilimsel Araştırma Projeleri) Otomasyonu**
- Backend kodu: `C:\project\bap\backend\src\main\java\com\mersin\bap_otomasyon`
  (v1 referans: `/workspace/MersinBapOtomasyon/src/main/java`)

## Mimari / Ayarlar
- Java 17 + Spring Boot 3 + Maven. JWT auth (`/api/auth/...`), role tabanli.
- Roller: `KullaniciRol` enum (Admin, BAPBirim, Hakem, Yurutucu, Muhasebe, Satinalma).
- Proje durumlari SDD'deki akisa bagli:
  `006 BasvuruAlindi (INI)` -> `008 Hazirlaniyor` -> `009 OnayaSunuldu` -> `010 Onaylandi`
  -> `011 Komisyonda` -> `102 KomisyonOnayli` -> `103 KomisyondanDondu`
  -> `075 DestekYasakli` -> `107 Kapandi`.
- Tablolar: `BAP_TRANSFERLER`, `BAP_SATINALMA`, `BAP_AVANSLAR`, `BAP_MASRAF` vb.
- Validasyon `ValidationResult`/`validationErrors` doner; eszamanli proje kapasitesi
  `countActiveByYurutucu` ile `eszamanliLimit` karsilastirilarak denetlenir.
- REST guzergah yaklasimi: koleksiyon alt kaynaklari `/api/users/{userId}/projects`,
  `/api/projects/{id}/limits` olarak; yaratici islemler icin action fiilleri
  (`/submit`, `/approve`, `/review`, `/submit-commission`, `/state`, `/ban`, `/unban`,
  `/evaluate`, `/reject`, `/close`) kullaniliyor.

## Kaynaklar
- Controllerlar: Auth, Project, Budget, Evaluation, Report, Spending, Admin, ProjectType (v1), Researcher.
- Servisler: ProjectServiceImpl, BudgetServiceImpl, EvaluationServiceImpl, ReportServiceImpl,
  SpendingServiceImpl, UserServiceImpl, ParameterServiceImpl, ProjectValidationServiceImpl,
  ResearcherServiceImpl.
- Repository, entity, DTO, enum, exception handler paketleri.

## Notlar / Yapilanlar
- [x] SDD action fiilleri: ProjectController `/validate`, `/submit`, `/approve`, `/review`, `/submit-commission`, `/state`.
- [x] REST koleksiyonlari: `/api/users/{userId}/projects`, `/api/projects/{id}/limits`.
- [x] Budget: `/ozet` ciktisi; transfer action'ları `/transfers/{id}/approve`, `/reject`.
- [x] Evaluation: `/invite`, `/accept`, `/reject`, `/submit`, `/remind`.
- [x] Report: `/reports/{reportId}/evaluate`.
- [x] Admin: `/users/{id}/ban`, `/users/{id}/unban`.
- [x] Spending: `/purchases/{id}/approve`, `/reject`, `/advances/{id}/close`.
- [x] Kullanilmayan DTO'lar silindi: EvaluationUpdateRequest, PurchaseDecisionRequest, TransferDecisionRequest.
- [x] `mvn compile` temiz (EXIT=0).

## Aktif Gorev Durumu
- Temiz: `mvn -q compile` -> EXIT=0. Son kontrol: 2026-08-14.