CREATE TABLE birimler (
    id UUID PRIMARY KEY,
    ad VARCHAR(255) NOT NULL,
    ust_birim_id UUID REFERENCES birimler(id),
    birim_turu VARCHAR(30) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    deleted_at TIMESTAMP
);

CREATE TABLE users (
    id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    ad_soyad VARCHAR(255) NOT NULL,
    rol VARCHAR(30) NOT NULL,
    unvan VARCHAR(100),
    birim_id UUID REFERENCES birimler(id),
    orcid VARCHAR(50),
    researcher_id VARCHAR(50),
    scopus_id VARCHAR(50),
    google_scholar VARCHAR(255),
    apbs_guncel BOOLEAN DEFAULT FALSE,
    yoksis_guncel BOOLEAN DEFAULT FALSE,
    yasak_bitis_tarihi DATE,
    tez_yasagi BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    deleted_at TIMESTAMP
);

CREATE TABLE project_types (
    id UUID PRIMARY KEY,
    kod VARCHAR(10) NOT NULL UNIQUE,
    ad VARCHAR(255) NOT NULL,
    butce_tavani DECIMAL(12,2),
    min_sure_ay INT DEFAULT 0,
    max_sure_ay INT,
    ek_butce_oran DECIMAL(5,2) DEFAULT 0,
    ek_sure_ay INT DEFAULT 0,
    performans_artisi_uygulanir BOOLEAN DEFAULT FALSE,
    bursiyer_olabilir BOOLEAN DEFAULT FALSE,
    yayin_sarti_zorunlu BOOLEAN DEFAULT TRUE,
    kariyer_boyu_kez INT DEFAULT 0,
    yilda_kez INT DEFAULT 0,
    eszamanli_limit INT DEFAULT 1,
    hakem_atanabilir BOOLEAN DEFAULT FALSE,
    sozlu_sunum_istenebilir BOOLEAN DEFAULT FALSE,
    ara_rapor_gerekli BOOLEAN DEFAULT FALSE,
    kongre_destegi_var BOOLEAN DEFAULT TRUE,
    protokol_gerekli BOOLEAN DEFAULT TRUE,
    rektor_onayi_gerekli BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE projects (
    id UUID PRIMARY KEY,
    proje_no VARCHAR(50) NOT NULL UNIQUE,
    tur_id UUID NOT NULL REFERENCES project_types(id),
    yurutucu_id UUID NOT NULL REFERENCES users(id),
    durum VARCHAR(50) NOT NULL DEFAULT 'TASLAK',
    baslik VARCHAR(500) NOT NULL,
    ozet TEXT,
    baslangic_tarihi DATE,
    bitis_tarihi DATE,
    fiili_bitis_tarihi DATE,
    butce_toplam DECIMAL(12,2) DEFAULT 0,
    butce_harcanan DECIMAL(12,2) DEFAULT 0,
    temel_alan_grubu CHAR(1),
    protokol_imzalandi_mi BOOLEAN DEFAULT FALSE,
    protokol_tarihi DATE,
    askida_mi BOOLEAN DEFAULT FALSE,
    aski_baslangic DATE,
    aski_bitis DATE,
    kapatma_olcut_id UUID,
    yayin_sarti_saglandi_mi BOOLEAN DEFAULT FALSE,
    kontenjan_istila_ediyor_mu BOOLEAN DEFAULT FALSE,
    version BIGINT DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    deleted_at TIMESTAMP
);

CREATE TABLE project_researchers (
    id UUID PRIMARY KEY,
    proje_id UUID NOT NULL REFERENCES projects(id),
    kullanici_id UUID NOT NULL REFERENCES users(id),
    rol VARCHAR(30) NOT NULL CHECK (rol IN ('Arastirmaci','Bursiyer')),
    baslama_tarihi DATE,
    bitis_tarihi DATE,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    UNIQUE(proje_id, kullanici_id)
);

CREATE TABLE project_documents (
    id UUID PRIMARY KEY,
    proje_id UUID NOT NULL REFERENCES projects(id),
    belge_turu VARCHAR(50) NOT NULL,
    dosya_adi VARCHAR(255) NOT NULL,
    dosya_yolu VARCHAR(500) NOT NULL,
    dosya_boyutu INTEGER,
    mime_type VARCHAR(100),
    anonim_mi BOOLEAN DEFAULT FALSE,
    guncel_mi BOOLEAN DEFAULT TRUE,
    yukleme_tarihi TIMESTAMP NOT NULL DEFAULT now(),
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE budget_items (
    id UUID PRIMARY KEY,
    proje_id UUID NOT NULL REFERENCES projects(id),
    kalem_kodu VARCHAR(20) NOT NULL,
    aciklama VARCHAR(500),
    birim_fiyat DECIMAL(12,2) NOT NULL,
    miktar DECIMAL(10,2) NOT NULL,
    toplam_tutar DECIMAL(12,2) NOT NULL,
    kdv_orani DECIMAL(5,2) DEFAULT 0,
    proforma_fatura_id UUID REFERENCES project_documents(id),
    onayli_mi BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE budget_transfers (
    id UUID PRIMARY KEY,
    proje_id UUID NOT NULL REFERENCES projects(id),
    kaynak_kalem_id UUID NOT NULL REFERENCES budget_items(id),
    hedef_kalem_id UUID NOT NULL REFERENCES budget_items(id),
    tutar DECIMAL(12,2) NOT NULL,
    gerekce TEXT,
    durum VARCHAR(20) NOT NULL DEFAULT 'Beklemede',
    komisyon_karar_tarihi DATE,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE evaluations (
    id UUID PRIMARY KEY,
    proje_id UUID NOT NULL REFERENCES projects(id),
    degerlendirici_tip VARCHAR(10) NOT NULL CHECK (degerlendirici_tip IN ('Hakem','Komisyon')),
    degerlendirici_id UUID NOT NULL REFERENCES users(id),
    degerlendirme_turu VARCHAR(30) NOT NULL DEFAULT 'BasvuruDegerlendirme',
    puan INTEGER CHECK (puan >= 0 AND puan <= 100),
    gorus TEXT,
    karar VARCHAR(20) CHECK (karar IN ('Kabul','Ret','Duzeltme')),
    davet_tarihi TIMESTAMP,
    davet_cevap_tarihi TIMESTAMP,
    teslim_tarihi TIMESTAMP,
    odeme_yapildi_mi BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE reports (
    id UUID PRIMARY KEY,
    proje_id UUID NOT NULL REFERENCES projects(id),
    rapor_turu VARCHAR(20) NOT NULL CHECK (rapor_turu IN ('AraRapor','SonucRaporu')),
    donem_no INTEGER,
    baslik VARCHAR(500),
    dosya_yolu VARCHAR(500),
    teslim_tarihi DATE,
    durum VARCHAR(30) NOT NULL DEFAULT 'Beklemede',
    degerlendirme_notu TEXT,
    gecikme_gun INTEGER DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE purchase_requests (
    id UUID PRIMARY KEY,
    proje_id UUID NOT NULL REFERENCES projects(id),
    butce_kalem_id UUID REFERENCES budget_items(id),
    talep_turu VARCHAR(20) NOT NULL,
    aciklama VARCHAR(500),
    tutar DECIMAL(12,2) NOT NULL,
    teknik_sartname_id UUID REFERENCES project_documents(id),
    durum VARCHAR(20) NOT NULL DEFAULT 'Beklemede',
    onay_tarihi DATE,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE expense_vouchers (
    id UUID PRIMARY KEY,
    proje_id UUID NOT NULL REFERENCES projects(id),
    alici_adi VARCHAR(255) NOT NULL,
    tutar DECIMAL(12,2) NOT NULL,
    vergi_kesintisi DECIMAL(12,2),
    net_tutar DECIMAL(12,2),
    gerekce TEXT,
    tarih DATE,
    durum VARCHAR(20) NOT NULL DEFAULT 'Hazir',
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE advances (
    id UUID PRIMARY KEY,
    proje_id UUID NOT NULL REFERENCES projects(id),
    satin_alma_talebi_id UUID REFERENCES purchase_requests(id),
    avans_turu VARCHAR(20) NOT NULL CHECK (avans_turu IN ('MalHizmet','Seyahat')),
    tutar DECIMAL(12,2) NOT NULL,
    tarih DATE,
    mahsup_tarihi DATE,
    mahsup_edildi_mi BOOLEAN DEFAULT FALSE,
    mahsup_belgesi_id UUID REFERENCES project_documents(id),
    durum VARCHAR(20) NOT NULL DEFAULT 'Acik',
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE bursary_payments (
    id UUID PRIMARY KEY,
    proje_id UUID NOT NULL REFERENCES projects(id),
    bursiyer_id UUID NOT NULL REFERENCES users(id),
    ay INTEGER NOT NULL CHECK (ay >= 1 AND ay <= 12),
    yil INTEGER NOT NULL,
    brut_tutar DECIMAL(10,2),
    sgk_kesintisi DECIMAL(10,2),
    net_tutar DECIMAL(10,2),
    hakedis_belgesi_id UUID REFERENCES project_documents(id),
    odeme_tarihi DATE,
    durum VARCHAR(20) NOT NULL DEFAULT 'Beklemede',
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    UNIQUE(bursiyer_id, ay, yil)
);

CREATE TABLE parameters (
    id UUID PRIMARY KEY,
    grup VARCHAR(100) NOT NULL,
    anahtar VARCHAR(255) NOT NULL UNIQUE,
    deger TEXT NOT NULL,
    aciklama TEXT,
    aktif BOOLEAN DEFAULT TRUE,
    guncellenme_tarihi TIMESTAMP NOT NULL DEFAULT now(),
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE closure_criteria (
    id UUID PRIMARY KEY,
    olcut_no INTEGER NOT NULL,
    ad VARCHAR(255) NOT NULL,
    aciklama TEXT,
    gecerli_alan_gruplari TEXT[],
    gecerli_proje_turleri TEXT[],
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE quota_increases (
    id UUID PRIMARY KEY,
    kullanici_id UUID NOT NULL REFERENCES users(id),
    proje_turu VARCHAR(10) NOT NULL,
    artis_miktari INTEGER NOT NULL DEFAULT 1,
    gerekce VARCHAR(500),
    baslangic_tarihi DATE NOT NULL,
    bitis_tarihi DATE NOT NULL,
    komisyon_karar_tarihi DATE,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE audit_logs (
    id BIGSERIAL PRIMARY KEY,
    kullanici_id UUID NOT NULL,
    islem_turu VARCHAR(50) NOT NULL,
    varlik_turu VARCHAR(50) NOT NULL,
    varlik_id UUID NOT NULL,
    eski_deger JSONB,
    yeni_deger JSONB,
    ip_adresi VARCHAR(45),
    zaman TIMESTAMP NOT NULL DEFAULT now()
);

CREATE INDEX idx_projects_yurutucu ON projects(yurutucu_id);
CREATE INDEX idx_projects_durum ON projects(durum);
CREATE INDEX idx_projects_tur ON projects(tur_id);
CREATE INDEX idx_pr_proje ON project_researchers(proje_id);
CREATE INDEX idx_pr_kullanici ON project_researchers(kullanici_id);
CREATE INDEX idx_pd_proje ON project_documents(proje_id);
CREATE INDEX idx_bi_proje ON budget_items(proje_id);
CREATE INDEX idx_ev_proje ON evaluations(proje_id);
CREATE INDEX idx_ev_degerlendirici ON evaluations(degerlendirici_id);
CREATE INDEX idx_rp_proje ON reports(proje_id);
CREATE INDEX idx_prq_proje ON purchase_requests(proje_id);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_birim ON users(birim_id);
CREATE INDEX idx_users_rol ON users(rol);
CREATE INDEX idx_birimler_ust ON birimler(ust_birim_id);
CREATE INDEX idx_params_grup ON parameters(grup);
CREATE INDEX idx_audit_logs_varlik ON audit_logs(varlik_turu, varlik_id);
CREATE INDEX idx_audit_logs_kullanici ON audit_logs(kullanici_id);
CREATE INDEX idx_audit_logs_zaman ON audit_logs(zaman);
