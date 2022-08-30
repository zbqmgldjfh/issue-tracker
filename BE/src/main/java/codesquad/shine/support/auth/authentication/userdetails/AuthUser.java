package codesquad.shine.support.auth.authentication.userdetails;

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
