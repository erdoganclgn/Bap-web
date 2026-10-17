CREATE TYPE bildirim_tipi AS ENUM ('Eposta', 'Sistem', 'Sms');

CREATE TABLE IF NOT EXISTS notifications (
    id UUID PRIMARY KEY,
    kullanici_id UUID NOT NULL REFERENCES users(id),
    tip bildirim_tipi NOT NULL DEFAULT 'Sistem',
    baslik VARCHAR(255) NOT NULL,
    mesaj TEXT NOT NULL,
    okundu_mi BOOLEAN NOT NULL DEFAULT FALSE,
    ilgili_url VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_notifications_kullanici ON notifications(kullanici_id);

CREATE TABLE IF NOT EXISTS integration_logs (
    id BIGSERIAL PRIMARY KEY,
    entegrasyon_ad VARCHAR(100) NOT NULL,
    istek_url VARCHAR(500),
    istek_govde JSONB,
    yanit_kodu INTEGER,
    yanit_govde JSONB,
    hata_mesaji TEXT,
    basarili_mi BOOLEAN NOT NULL DEFAULT FALSE,
    deneme_sayisi INTEGER NOT NULL DEFAULT 1,
    zaman TIMESTAMP NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_integration_logs_entegrasyon ON integration_logs(entegrasyon_ad);
CREATE INDEX IF NOT EXISTS idx_integration_logs_zaman ON integration_logs(zaman);
