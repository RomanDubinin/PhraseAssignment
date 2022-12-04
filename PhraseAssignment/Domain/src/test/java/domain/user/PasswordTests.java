package domain.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PasswordTests {
    @Test
    public void password_notEmpty_success() {
        new Password("***");
    }

    @Test
    public void password_empty_exception() {
        var exception = assertThrows(IllegalArgumentException.class, () -> new Password(""));
        assertEquals(exception.getMessage(), "Password cannot be empty");
    }

    @Test
    public void password_null_exception() {
        var exception = assertThrows(IllegalArgumentException.class, () -> new Password(null));
        assertEquals(exception.getMessage(), "Password cannot be empty");
    }
}
