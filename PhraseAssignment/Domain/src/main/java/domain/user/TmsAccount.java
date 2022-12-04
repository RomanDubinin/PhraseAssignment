package domain.user;

import domain.user.converters.PasswordAttributeConverter;
import domain.user.converters.UserNameAttributeConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TmsAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Convert(converter = UserNameAttributeConverter.class)
    private UserName userName;

    @Convert(converter = PasswordAttributeConverter.class)
    private Password password;
}