package domain.user;

public class UserName {
    private String userName;

    public UserName(String userName){
        if (userName == null || userName.isEmpty())
            throw new IllegalArgumentException("User name cannot be empty");

        this.userName = userName;
    }

    @Override
    public String toString() {
        return userName;
    }
}
