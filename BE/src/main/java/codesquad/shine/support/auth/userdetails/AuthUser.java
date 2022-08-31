package codesquad.shine.support.auth.userdetails;

import codesquad.shine.issuetracker.user.domain.RoleType;

import java.util.List;

public class AuthUser implements UserDetails {

    private String username;
    private String password;
    private List<String> authorities;

    public AuthUser(String username, String password, List<String> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public static AuthUser anonymous() {
        return new AuthUser("Anonymous", "Anonymous", List.of(RoleType.ROLE_ANONYMOUS.name()));
    }

    @Override
    public Object getUsername() {
        return username;
    }

    @Override
    public Object getPassword() {
        return password;
    }

    @Override
    public List<String> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean checkCredentials(Object credentials) {
        return password.equals(credentials.toString());
    }
}
