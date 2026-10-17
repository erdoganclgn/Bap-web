INSERT INTO birimler (id, ad, kod, ust_birim_id, birim_turu, aktif) VALUES
    ('c0a8a100-0000-4000-8000-000000000001', 'Mühendislik Fakültesi', 'MUHF', NULL, 'Fakulte', true),
    ('c0a8a100-0000-4000-8000-000000000002', 'Fen Edebiyat Fakültesi', 'FEF', NULL, 'Fakulte', true),
    ('c0a8a100-0000-4000-8000-000000000003', 'Tıp Fakültesi', 'TIPF', NULL, 'Fakulte', true),
    ('c0a8a100-0000-4000-8000-000000000004', 'Eğitim Fakültesi', 'EGF', NULL, 'Fakulte', true),
    ('c0a8a100-0000-4000-8000-000000000005', 'Fen Bilimleri Enstitüsü', 'FBE', NULL, 'Enstitu', true),
    ('c0a8a100-0000-4000-8000-000000000006', 'Sosyal Bilimler Enstitüsü', 'SBE', NULL, 'Enstitu', true),
    ('c0a8a100-0000-4000-8000-000000000007', 'Sağlık Bilimleri Enstitüsü', 'SABE', NULL, 'Enstitu', true),
    ('c0a8a100-0000-4000-8000-000000000101', 'Bilgisayar Mühendisliği Bölümü', 'BMB', 'c0a8a100-0000-4000-8000-000000000001', 'Bolum', true),
    ('c0a8a100-0000-4000-8000-000000000102', 'Elektrik Elektronik Mühendisliği Bölümü', 'EEMB', 'c0a8a100-0000-4000-8000-000000000001', 'Bolum', true),
    ('c0a8a100-0000-4000-8000-000000000103', 'Kimya Bölümü', 'KIM', 'c0a8a100-0000-4000-8000-000000000002', 'Bolum', true),
    ('c0a8a100-0000-4000-8000-000000000104', 'Biyoloji Bölümü', 'BIO', 'c0a8a100-0000-4000-8000-000000000002', 'Bolum', true),
    ('c0a8a100-0000-4000-8000-000000000105', 'Temel Tıp Bilimleri Bölümü', 'TTB', 'c0a8a100-0000-4000-8000-000000000003', 'Bolum', true)
ON CONFLICT (id) DO NOTHING;