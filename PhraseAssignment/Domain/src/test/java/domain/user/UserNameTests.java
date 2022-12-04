package domain.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserNameTests {
    @Test
    public void userNAme_notEmpty_success() {
        new UserName("name");
    }

    @Test
    public void userNAme_empty_exception() {
        var exception = assertThrows(IllegalArgumentException.class, () -> new UserName(""));
        assertEquals(exception.getMessage(), "User name cannot be empty");
    }

    @Test
    public void userNAme_null_exception() {
        var exception = assertThrows(IllegalArgumentException.class, () -> new UserName(null));
        assertEquals(exception.getMessage(), "User name cannot be empty");
    }
}
