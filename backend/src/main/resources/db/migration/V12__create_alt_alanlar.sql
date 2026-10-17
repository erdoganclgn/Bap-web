CREATE TABLE alt_alanlar (
    id UUID PRIMARY KEY,
    temel_alan_id UUID NOT NULL REFERENCES temel_alanlar(id) ON DELETE CASCADE,
    kod VARCHAR(20) NOT NULL,
    ad VARCHAR(255) NOT NULL,
    aktif BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT uk_alt_alan_kod UNIQUE (temel_alan_id, kod)
);

CREATE INDEX idx_alt_alanlar_temel_alan_id ON alt_alanlar (temel_alan_id);

INSERT INTO alt_alanlar (id, temel_alan_id, kod, ad, aktif) VALUES
    ('b2c3d4e5-0000-4000-8000-000000000001', 'a1b2c3d4-0000-4000-8000-000000000001', 'MAK', 'Makine Mühendisliği', true),
    ('b2c3d4e5-0000-4000-8000-000000000002', 'a1b2c3d4-0000-4000-8000-000000000001', 'ELK', 'Elektrik-Elektronik Mühendisliği', true),
    ('b2c3d4e5-0000-4000-8000-000000000003', 'a1b2c3d4-0000-4000-8000-000000000001', 'INS', 'İnşaat Mühendisliği', true),
    ('b2c3d4e5-0000-4000-8000-000000000004', 'a1b2c3d4-0000-4000-8000-000000000002', 'BIL', 'Bilgisayar Bilimleri', true),
    ('b2c3d4e5-0000-4000-8000-000000000005', 'a1b2c3d4-0000-4000-8000-000000000002', 'MAT', 'Matematik', true),
    ('b2c3d4e5-0000-4000-8000-000000000006', 'a1b2c3d4-0000-4000-8000-000000000004', 'JEO', 'Jeoloji', true)
ON CONFLICT (id) DO NOTHING;
