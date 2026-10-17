-- Native PostgreSQL enum tiplerini VARCHAR'a cevir
-- Neden: Hibernate @Enumerated(EnumType.STRING) ile native enum sutunlarina
-- INSERT/UPDATE yapildiginda "column is of type x but expression is of type character varying"
-- hatasi verir. Semantik korunmasi icin CHECK constraint'ler eklenir,
-- mevcut DEFAULT'lar string literal olarak korunur.

-- 1) Enum tipine bakan DEFAULT'lari dusur
ALTER TABLE projects ALTER COLUMN durum DROP DEFAULT;
ALTER TABLE budget_transfers ALTER COLUMN durum DROP DEFAULT;
ALTER TABLE reports ALTER COLUMN durum DROP DEFAULT;
ALTER TABLE purchase_requests ALTER COLUMN durum DROP DEFAULT;
ALTER TABLE advances ALTER COLUMN durum DROP DEFAULT;
ALTER TABLE bursary_payments ALTER COLUMN durum DROP DEFAULT;
ALTER TABLE expense_vouchers ALTER COLUMN durum DROP DEFAULT;
ALTER TABLE notifications ALTER COLUMN tip DROP DEFAULT;
ALTER TABLE closure_criteria ALTER COLUMN gecerli_alan_gruplari DROP DEFAULT;

-- 2) Sütun tiplerini VARCHAR'a cevir
ALTER TABLE users ALTER COLUMN rol TYPE varchar(50) USING rol::text;
ALTER TABLE projects ALTER COLUMN durum TYPE varchar(50) USING durum::text;
ALTER TABLE projects ALTER COLUMN temel_alan_grubu TYPE varchar(10) USING temel_alan_grubu::text;
ALTER TABLE project_researchers ALTER COLUMN rol TYPE varchar(50) USING rol::text;
ALTER TABLE project_documents ALTER COLUMN belge_turu TYPE varchar(50) USING belge_turu::text;
ALTER TABLE birimler ALTER COLUMN birim_turu TYPE varchar(50) USING birim_turu::text;
ALTER TABLE budget_items ALTER COLUMN kalem_kodu TYPE varchar(50) USING kalem_kodu::text;
ALTER TABLE budget_transfers ALTER COLUMN durum TYPE varchar(50) USING durum::text;
ALTER TABLE evaluations ALTER COLUMN degerlendirici_tip TYPE varchar(50) USING degerlendirici_tip::text;
ALTER TABLE evaluations ALTER COLUMN degerlendirme_turu TYPE varchar(50) USING degerlendirme_turu::text;
ALTER TABLE evaluations ALTER COLUMN karar TYPE varchar(50) USING karar::text;
ALTER TABLE reports ALTER COLUMN durum TYPE varchar(50) USING durum::text;
ALTER TABLE reports ALTER COLUMN rapor_turu TYPE varchar(50) USING rapor_turu::text;
ALTER TABLE purchase_requests ALTER COLUMN durum TYPE varchar(50) USING durum::text;
ALTER TABLE purchase_requests ALTER COLUMN talep_turu TYPE varchar(50) USING talep_turu::text;
ALTER TABLE advances ALTER COLUMN avans_turu TYPE varchar(50) USING avans_turu::text;
ALTER TABLE advances ALTER COLUMN durum TYPE varchar(50) USING durum::text;
ALTER TABLE bursary_payments ALTER COLUMN durum TYPE varchar(50) USING durum::text;
ALTER TABLE expense_vouchers ALTER COLUMN durum TYPE varchar(50) USING durum::text;
ALTER TABLE notifications ALTER COLUMN tip TYPE varchar(50) USING tip::text;
ALTER TABLE audit_logs ALTER COLUMN islem_turu TYPE varchar(50) USING islem_turu::text;
ALTER TABLE closure_criteria ALTER COLUMN gecerli_alan_gruplari TYPE text[] USING gecerli_alan_gruplari::text[];

-- 3) CHECK constraint'ler
ALTER TABLE users ADD CONSTRAINT ck_users_rol CHECK (rol IN ('Yurutucu','Arastirmaci','Bursiyer','Hakem','Komisyon','BAPPersonel','Koordinator','Admin','EnstituFakulte','Rektor'));
ALTER TABLE projects ADD CONSTRAINT ck_projects_durum CHECK (durum IN ('Beklemede','Taslak','BasvuruYapildi','Inceleniyor','EksikIade','Komisyonda','HakemDegerlendirme','DegerlendirmeTamam','SozluSunum','SozluSunumYapildi','KomisyonKarari','Reddedildi','Kabul','YenidenInceleme','ProtokolAsamasi','Aktif','AskiyaAlindi','Bitti','SonucRaporuBekliyor','RaporKabul','RaporRet','RaporGecikti','Kapandi','Iptal'));
ALTER TABLE projects ADD CONSTRAINT ck_projects_temel_alan CHECK (temel_alan_grubu IN ('A','B','C','D','E','F','G'));
ALTER TABLE project_researchers ADD CONSTRAINT ck_project_researchers_rol CHECK (rol IN ('Arastirmaci','Bursiyer'));
ALTER TABLE project_documents ADD CONSTRAINT ck_project_documents_belge_turu CHECK (belge_turu IN ('BasvuruDosyasi','ProformaFatura','TeknikSartname','EtikKurul','Transkript','OgrenciBelgesi','Feragatname','ArastirmaciBeyan','OrtaklikTaahhutnamesi','MahsupBelgesi','HakedisBelgesi','Diger'));
ALTER TABLE birimler ADD CONSTRAINT ck_birimler_birim_turu CHECK (birim_turu IN ('Fakulte','Enstitu','Yuksekokul','Bolum','Merkez'));
ALTER TABLE budget_items ADD CONSTRAINT ck_budget_items_kalem_kodu CHECK (kalem_kodu IN ('03.1','03.2','03.3','03.4','03.5','Burs','AcikErisim'));
ALTER TABLE budget_transfers ADD CONSTRAINT ck_budget_transfers_durum CHECK (durum IN ('Beklemede','Onaylandi','Reddedildi'));
ALTER TABLE evaluations ADD CONSTRAINT ck_evaluations_degerlendirici_tip CHECK (degerlendirici_tip IN ('Hakem','Komisyon'));
ALTER TABLE evaluations ADD CONSTRAINT ck_evaluations_degerlendirme_turu CHECK (degerlendirme_turu IN ('BasvuruDegerlendirme','AraRapor','SonucRaporu'));
ALTER TABLE evaluations ADD CONSTRAINT ck_evaluations_karar CHECK (karar IN ('Kabul','Ret','Duzeltme'));
ALTER TABLE reports ADD CONSTRAINT ck_reports_durum CHECK (durum IN ('Beklemede','Kabul','Red','DuzeltmeGerekli'));
ALTER TABLE reports ADD CONSTRAINT ck_reports_rapor_turu CHECK (rapor_turu IN ('AraRapor','SonucRaporu'));
ALTER TABLE purchase_requests ADD CONSTRAINT ck_purchase_requests_durum CHECK (durum IN ('Beklemede','Onaylandi','Reddedildi','SatinAlindi'));
ALTER TABLE purchase_requests ADD CONSTRAINT ck_purchase_requests_talep_turu CHECK (talep_turu IN ('Mal','Hizmet','Seyahat','Kongre'));
ALTER TABLE advances ADD CONSTRAINT ck_advances_avans_turu CHECK (avans_turu IN ('MalHizmet','Seyahat'));
ALTER TABLE advances ADD CONSTRAINT ck_advances_durum CHECK (durum IN ('Acik','MahsupEdildi','Gecikmis'));
ALTER TABLE bursary_payments ADD CONSTRAINT ck_bursary_payments_durum CHECK (durum IN ('Beklemede','Odendi','Iptal'));
ALTER TABLE expense_vouchers ADD CONSTRAINT ck_expense_vouchers_durum CHECK (durum IN ('Hazir','Odendi','Iptal'));
ALTER TABLE notifications ADD CONSTRAINT ck_notifications_tip CHECK (tip IN ('Eposta','Sistem','Sms'));
ALTER TABLE audit_logs ADD CONSTRAINT ck_audit_logs_islem_turu CHECK (islem_turu IN ('PROJE_OLUSTUR','PROJE_GUNCELLE','PROJE_SIL','PROJE_ONAYLA','DURUM_DEGISTIR','BELGE_YUKLE','BELGE_SIL','BUTCE_EKLE','BUTCE_GUNCELLE','BUTCE_SIL','BUTCE_AKTARIM','DEGERLENDIRME_YAP','DAVET_GONDER','DAVET_KABUL','DAVET_RET','RAPOR_YUKLE','RAPOR_DEGERLENDIR','SATIN_ALMA_TALEP','SATIN_ALMA_ONAY','AVANS_TALEP','AVANS_MAHSUP','BURSIYER_EKLE','BURSIYER_CIKAR','HAKEDIS_GIR','PARAMETRE_GUNCELLE','KULLANICI_GUNCELLE','PROJE_KAPAT'));

-- 4) DEFAULT'lari string literal olarak geri ekle
ALTER TABLE projects ALTER COLUMN durum SET DEFAULT 'Taslak';
ALTER TABLE budget_transfers ALTER COLUMN durum SET DEFAULT 'Beklemede';
ALTER TABLE reports ALTER COLUMN durum SET DEFAULT 'Beklemede';
ALTER TABLE purchase_requests ALTER COLUMN durum SET DEFAULT 'Beklemede';
ALTER TABLE advances ALTER COLUMN durum SET DEFAULT 'Acik';
ALTER TABLE bursary_payments ALTER COLUMN durum SET DEFAULT 'Beklemede';
ALTER TABLE expense_vouchers ALTER COLUMN durum SET DEFAULT 'Hazir';
ALTER TABLE notifications ALTER COLUMN tip SET DEFAULT 'Sistem';
ALTER TABLE closure_criteria ALTER COLUMN gecerli_alan_gruplari SET DEFAULT '{}';

-- 5) Artik kullanilmayan enum tiplerini kaldir
DROP TYPE IF EXISTS kullanici_rol;
DROP TYPE IF EXISTS proje_durum;
DROP TYPE IF EXISTS temel_alan_grubu;
DROP TYPE IF EXISTS arastirmaci_rol;
DROP TYPE IF EXISTS belge_turu;
DROP TYPE IF EXISTS birim_turu;
DROP TYPE IF EXISTS kalem_kodu;
DROP TYPE IF EXISTS transfer_durumu;
DROP TYPE IF EXISTS degerlendirici_tip;
DROP TYPE IF EXISTS degerlendirme_turu;
DROP TYPE IF EXISTS degerlendirme_karar;
DROP TYPE IF EXISTS rapor_durum;
DROP TYPE IF EXISTS rapor_turu;
DROP TYPE IF EXISTS satin_alma_durum;
DROP TYPE IF EXISTS talep_turu;
DROP TYPE IF EXISTS avans_turu;
DROP TYPE IF EXISTS avans_durum;
DROP TYPE IF EXISTS hakedis_durum;
DROP TYPE IF EXISTS harcama_pusula_durum;
DROP TYPE IF EXISTS bildirim_tipi;
DROP TYPE IF EXISTS islem_turu;
