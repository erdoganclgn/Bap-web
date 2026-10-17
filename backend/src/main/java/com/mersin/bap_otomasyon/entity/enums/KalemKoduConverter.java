package com.mersin.bap_otomasyon.entity.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class KalemKoduConverter implements AttributeConverter<KalemKodu, String> {

    @Override
    public String convertToDatabaseColumn(KalemKodu attribute) {
        if (attribute == null) return null;
        return attribute.getValue();
    }

    @Override
    public KalemKodu convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        for (KalemKodu kod : KalemKodu.values()) {
            if (kod.getValue().equals(dbData)) {
                return kod;
            }
        }
        throw new IllegalArgumentException("Unknown kalem kodu: " + dbData);
    }
}
