CREATE TABLE temel_alanlar (
    id UUID PRIMARY KEY,
    kod VARCHAR(10) NOT NULL UNIQUE,
    ad VARCHAR(255) NOT NULL,
    aktif BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

INSERT INTO temel_alanlar (id, kod, ad, aktif) VALUES
    ('a1b2c3d4-0000-4000-8000-000000000001', 'A', 'Fen ve Mühendislik Bilimleri', true),
    ('a1b2c3d4-0000-4000-8000-000000000002', 'B', 'Matematik ve Bilgisayar Bilimleri', true),
    ('a1b2c3d4-0000-4000-8000-000000000003', 'C', 'Sosyal ve Beşeri Bilimler', true),
    ('a1b2c3d4-0000-4000-8000-000000000004', 'D', 'Doğa Bilimleri', true),
    ('a1b2c3d4-0000-4000-8000-000000000005', 'E', 'Sağlık Bilimleri', true),
    ('a1b2c3d4-0000-4000-8000-000000000006', 'F', 'Sanat Bilimleri', true),
    ('a1b2c3d4-0000-4000-8000-000000000007', 'G', 'Eğitim Bilimleri', true)
ON CONFLICT (id) DO NOTHING;