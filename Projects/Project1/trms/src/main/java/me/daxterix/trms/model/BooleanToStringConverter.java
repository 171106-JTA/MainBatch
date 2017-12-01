package me.daxterix.trms.model;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanToStringConverter implements AttributeConverter<Boolean, String> {
    // assumes value is not null
    @Override
    public String convertToDatabaseColumn(Boolean theBoolean) {
        return theBoolean? "Y" : "N";
    }
    // assumes value is not null
    @Override
    public Boolean convertToEntityAttribute(String s) {
        return s.equals("Y");
    }
}

