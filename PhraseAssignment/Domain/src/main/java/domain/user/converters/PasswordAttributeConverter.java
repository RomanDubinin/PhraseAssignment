package domain.user.converters;

import jakarta.persistence.AttributeConverter;
import domain.user.Password;

public class PasswordAttributeConverter implements AttributeConverter<Password, String> {
    @Override
    public String convertToDatabaseColumn(Password attribute) {
        return attribute == null ? null : attribute.toString();
    }

    @Override
    public Password convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new Password(dbData);
    }
}