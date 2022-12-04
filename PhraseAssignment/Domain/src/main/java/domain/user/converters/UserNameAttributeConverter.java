package domain.user.converters;

import jakarta.persistence.AttributeConverter;
import domain.user.UserName;

public class UserNameAttributeConverter implements AttributeConverter<UserName, String> {
    @Override
    public String convertToDatabaseColumn(UserName attribute) {
        return attribute == null ? null : attribute.toString();
    }

    @Override
    public UserName convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new UserName(dbData);
    }
}
