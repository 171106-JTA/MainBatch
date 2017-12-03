package me.daxterix.trms.model;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanToStringConverter implements AttributeConverter<Boolean, String> {
    // assumes value is not null
    @Override
    public String convertToDatabaseColumn(Boolean theBoolean) {
        if (theBoolean == null)
            return null;
        return theBoolean? "Y" : "N";
    }
    // assumes value is not null
    @Override
    public Boolean convertToEntityAttribute(String s) {
        if (s == null)
            return null;
        return s.equals("Y");
    }
}

