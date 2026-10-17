-- Temel alan grubu artik dinamik: temel_alanlar tablosundan gelir.
-- Sabit A-G sinirini kaldir (yeni kodlar kurul karariyla eklenebilir).
ALTER TABLE projects DROP CONSTRAINT IF EXISTS ck_projects_temel_alan;