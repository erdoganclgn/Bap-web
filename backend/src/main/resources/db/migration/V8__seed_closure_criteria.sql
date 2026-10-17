INSERT INTO closure_criteria (id, olcut_no, ad, aciklama, gecerli_alan_gruplari, gecerli_proje_turleri, aktif) VALUES
    ('1bc703b9-c3be-42ab-8bbe-dcd0be02c18a', 1, 'Q1/Q2 WoS Makale (SCI/SCI-E/SSCI/AHCI)', 'Yayin turu: Arastirma / Derleme / Editore Mektup. Q1 veya Q2 ceyreklik dilim.', '{A,B,C,D,E,F,G}', '{TP2,TP3,AP1,AP2,AP3,AP4,AP5,AP6,AP7,AP8}', true),
    ('5aab3520-981c-43bc-b7b5-c765b177e1b5', 2, 'Q3/Q4 WoS Makale (SCI/SCI-E/SSCI/AHCI)', 'Yayin turu: Arastirma / Derleme / Editore Mektup. Q3 veya Q4 ceyreklik dilim.', '{A,B,C,D,E,F,G}', '{TP2,TP3,AP2,AP3,AP4,AP5}', true),
    ('66e27df5-cee2-4858-b307-061064470a1c', 3, 'WoS ESCI veya Alan Indeksli Makale', 'ESCI veya diger alan indekslerinde taranan dergilerde makale.', '{A,B,C,D,E,F,G}', '{TP2,TP3,AP3,AP7}', true),
    ('261b69a8-7961-4071-97e1-7f51c13f726f', 4, 'Uluslararasi Yayinevinden Kitap', 'Tanimis uluslararasi yayinevlerinden kitap yayini.', '{C,E,F,G}', '{AP2,AP4,AP5}', true),
    ('f6ad104e-0981-4f3c-bcd9-5ab6c1fe66e3', 5, 'Uluslararasi Yayinevinde Kitap Bolumu', 'Tanimis uluslararasi yayinevlerinde kitap bolumu.', '{C,E,F,G}', '{TP3,AP2,AP4,AP5}', true),
    ('9c7a0972-09c6-4c64-b074-313a8baf24bb', 6, 'Ulusal Yayinevinden Kitap', 'Tanimis ulusal yayinevlerinden kitap yayini.', '{C,E,F,G}', '{TP3,AP3,AP7}', true),
    ('29edc37b-126a-4bf5-a8ee-97b82cd993e1', 7, 'Ulusal Yayinevinde Kitap Bolumu', 'Tanimis ulusal yayinevlerinde kitap bolumu.', '{C,E,F,G}', '{TP2,TP3,AP7}', true),
    ('101b631b-2070-4935-a132-be3f59aac8da', 8, 'Patent Tescili', 'Ulusal veya uluslararasi patent tescili.', '{A,B,D}', '{TP2,TP3,AP1,AP2,AP3,AP5,AP8}', true),
    ('2d6c3e34-928c-416c-b167-c07e32e26657', 9, 'Faydali Model / Endustriyel Tasarim Tescili', 'Ulusal veya uluslararasi faydali model / tasarim tescili.', '{A,B,D}', '{TP2,TP3,AP1,AP2,AP3}', true),
    ('259537eb-c379-4699-b92c-40263e7b8de9', 10, 'TR Dizin Makale', 'TR Dizin kapsamindaki dergilerde makale.', '{C,E,F,G}', '{TP2,TP3,AP7,AP9}', true),
    ('f1ba6caa-9c12-4fb0-8830-c986a569bfdf', 11, 'Bildiri (Uluslararasi Kongre)', 'Uluslararasi kongrede tam metin veya ozet bildiri.', '{A,B,C,D,E,F,G}', '{TP1,TP2,AP7,AP9}', true),
    ('9ebab63c-1886-4699-b3a1-fad52ec47a15', 12, 'Sanatsal Etkinlik / Sergi / Performans', 'Ulusal veya uluslararasi sanatsal etkinlik, sergi, performans.', '{F,G}', '{TP2,TP3,AP1,AP2,AP3,AP7}', true)
ON CONFLICT (id) DO NOTHING;