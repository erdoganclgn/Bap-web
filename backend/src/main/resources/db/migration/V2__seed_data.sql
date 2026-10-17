DELETE FROM project_types;
DELETE FROM parameters;

INSERT INTO project_types (id, kod, ad, butce_tavani, min_sure_ay, max_sure_ay, ek_butce_oran, ek_sure_ay, performans_artisi_uygulanir, bursiyer_olabilir, yayin_sarti_zorunlu, kariyer_boyu_kez, yilda_kez, eszamanli_limit, hakem_atanabilir, sozlu_sunum_istenebilir, ara_rapor_gerekli, kongre_destegi_var, protokol_gerekli, rektor_onayi_gerekli) VALUES
    (gen_random_uuid(), 'TP1', 'Lisans Ogrencisi Arastirma Projesi', 10000, 6, 12, 0, 1, false, false, false, 1, 0, 5, false, false, false, false, true, false),
    (gen_random_uuid(), 'TP2', 'Yuksek Lisans Tez Projesi', 45000, 0, 24, 50, 6, false, false, true, 0, 0, 3, true, false, true, true, true, false),
    (gen_random_uuid(), 'TP3', 'Doktora/Uzmanlik/SY Tez Projesi', 90000, 0, 36, 50, 6, false, false, true, 0, 0, 3, true, false, true, true, true, false),
    (gen_random_uuid(), 'AP1', 'Kariyer Baslangic Arastirma Projesi', 100000, 12, 36, 50, 0, true, true, true, 1, 0, 1, true, false, true, true, true, false),
    (gen_random_uuid(), 'AP2', 'Bireysel Arastirma Projesi', 75000, 12, 36, 50, 0, true, true, true, 0, 0, 1, true, false, true, true, true, false),
    (gen_random_uuid(), 'AP3', 'Oncelikli Alan Arastirma Projesi', 150000, 12, 36, 50, 0, true, true, true, 0, 1, 1, true, true, true, true, true, false),
    (gen_random_uuid(), 'AP4', 'Kapsamli Arastirma Projesi', 250000, 12, 36, 50, 0, false, true, true, 0, 0, 1, true, true, true, true, true, true),
    (gen_random_uuid(), 'AP5', 'Gudumlu Proje', 0, 0, 24, 50, 12, false, false, false, 0, 1, 99, true, true, false, false, true, true),
    (gen_random_uuid(), 'AP6', 'Isbirligi Projesi', 250000, 12, 36, 50, 0, false, true, true, 0, 1, 99, true, true, true, true, true, false),
    (gen_random_uuid(), 'AP7', 'Hizli Destek Arastirma Projesi', 50000, 0, 12, 0, 1, false, false, true, 3, 1, 1, false, false, false, false, true, false),
    (gen_random_uuid(), 'AP8', 'Es Finansmanli Bilimsel Arastirma Projesi', 250000, 0, 0, 0, 0, false, true, true, 0, 0, 1, true, true, false, false, true, true),
    (gen_random_uuid(), 'AP9', 'Yarismalara Katilim Arastirma Projesi', 30000, 0, 12, 0, 1, false, false, false, 0, 0, 2, false, false, false, false, true, false);

INSERT INTO parameters (id, grup, anahtar, deger, aciklama, aktif) VALUES
    (gen_random_uuid(), 'butce', 'tp1.butce_tavani', '10000', 'TP1 butce tavani', true),
    (gen_random_uuid(), 'butce', 'ap1.butce_tavani', '100000', 'AP1 butce tavani', true),
    (gen_random_uuid(), 'butce', 'ap2.butce_tavani', '75000', 'AP2 butce tavani', true),
    (gen_random_uuid(), 'butce', 'burs_limit_oran', '0.30', 'Burs butcesi toplam butcenin %30unu asamaz', true),
    (gen_random_uuid(), 'sure', 'tp1.max_sure_ay', '12', 'TP1 azami sure', true),
    (gen_random_uuid(), 'sure', 'tp1.ek_sure_ay', '1', 'TP1 ek sure', true),
    (gen_random_uuid(), 'sure', 'sonuc_raporu_teslim_suresi_ay', '3', 'Proje bitisi sonrasi sonuc raporu teslim suresi', true),
    (gen_random_uuid(), 'sure', 'protokol_imza_suresi_gun', '30', 'Kabul sonrasi protokol imza suresi', true),
    (gen_random_uuid(), 'kontenjan', 'ap1_4.toplam_eszamanli', '1', 'AP1-AP4 toplam eszamanli limit', true),
    (gen_random_uuid(), 'kontenjan', 'arastirmaci_max_proje', '5', 'Arastirmacinin maksimum proje sayisi', true),
    (gen_random_uuid(), 'hakem', 'davet_cevap_suresi_gun', '7', 'Hakem davet cevap suresi', true),
    (gen_random_uuid(), 'hakem', 'degerlendirme_suresi_gun', '14', 'Hakem degerlendirme suresi', true),
    (gen_random_uuid(), 'hakem', 'ek_sure_gun', '5', 'Hakem ek sure', true),
    (gen_random_uuid(), 'hakem', 'yillik_odeme_limit', '6', 'Yillik maksimum hakemlik odemesi', true),
    (gen_random_uuid(), 'rapor', 'gecikme_yaptirim_1_gun', '30', '1. kademe gecikme esigi (gun)', true),
    (gen_random_uuid(), 'rapor', 'gecikme_yaptirim_2_gun', '45', '2. kademe gecikme esigi (gun) - iptal', true),
    (gen_random_uuid(), 'avans', 'mahsup_suresi_gun', '45', 'Avans mahsup suresi', true),
    (gen_random_uuid(), 'avans', 'seyahat_mahsup_suresi_gun', '30', 'Seyahat avansi mahsup suresi', true),
    (gen_random_uuid(), 'agno', 'tp1.agno_min', '2.50', 'TP1 minimum AGNO', true),
    (gen_random_uuid(), 'agno', 'tp1.kacinci_yariyil', '4', 'TP1 icin tamamlanmasi gereken yariyil sayisi', true);
