package domain.user;

public class Password {
    private String password;

    public Password(String password){
        if (password == null || password.isEmpty())
            throw new IllegalArgumentException("Password cannot be empty");

        this.password = password;
    }

    @Override
    public String toString() {
        return password;
    }
}
