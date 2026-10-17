package com.mersin.bap_otomasyon.entity.enums;

public enum KalemKodu {
    KOD_03_1("03.1"), KOD_03_2("03.2"), KOD_03_3("03.3"),
    KOD_03_4("03.4"), KOD_03_5("03.5"), Burs("Burs"), AcikErisim("AcikErisim");

    private final String value;

    KalemKodu(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
