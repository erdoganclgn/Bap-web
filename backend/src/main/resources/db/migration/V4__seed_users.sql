INSERT INTO users (id, email, password_hash, ad_soyad, rol, unvan, apbs_guncel, yoksis_guncel, tez_yasagi, aktif, created_at, updated_at) VALUES
    (gen_random_uuid(), 'admin@mersin.edu.tr', '$2a$10$VHMht5UTKcE/Pdf5sQOe5e5Eahw66qlHHFCcmYl83Tv6AuGuiW6Wq', 'BAP Sistem Admini', 'Admin', NULL, false, false, false, true, now(), now()),
    (gen_random_uuid(), 'koordinator@mersin.edu.tr', '$2a$10$VHMht5UTKcE/Pdf5sQOe5e5Eahw66qlHHFCcmYl83Tv6AuGuiW6Wq', 'BAP Koordinatoru', 'Koordinator', NULL, false, false, false, true, now(), now());
