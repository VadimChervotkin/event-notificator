package chervotkin.dev.user;

public class AuthenticatedUser {

    private final Long id;
    private final String login;
    private final String role;

    public AuthenticatedUser(Long id, String login, String role) {
        this.id = id;
        this.login = login;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getRole() {
        return role;
    }
}

