import re

# Fix SDD
with open('C:/project/bap/doc/BAP_OTOMASYON_SDD.md', 'r', encoding='utf-8') as f:
    sdd = f.read()

# Fix 1: State machine report delay validations (7.2)
old_sdd_1 = """| Bitti → SonucRaporuBekliyor | Süre dolmuş mu? |
|