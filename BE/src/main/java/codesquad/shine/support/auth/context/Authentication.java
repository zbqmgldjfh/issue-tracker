package codesquad.shine.support.auth.context;

import java.io.Serializable;
import java.util.List;

public class Authentication implements Serializable {
    private Object principal;
    private Object credentials;
    private List<String> authorities;

    public Authentication(Object principal, Object credentials, List<String> authorities) {
        this.principal = principal;
        this.credentials = credentials;
        this.authorities = authorities;
    }

    public Object getPrincipal() {
        return principal;
    }

    public Object getCredentials() {
        return credentials;
    }

    public List<String> getAuthorities() {
        return authorities;
    }
}
