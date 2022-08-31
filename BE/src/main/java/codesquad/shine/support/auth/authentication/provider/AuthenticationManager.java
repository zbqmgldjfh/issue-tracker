package codesquad.shine.support.auth.authentication.provider;

import codesquad.shine.support.auth.authentication.AuthenticationException;
import codesquad.shine.support.auth.authentication.AuthenticationToken;
import codesquad.shine.support.auth.context.Authentication;

public interface AuthenticationManager {
    Authentication authenticate(AuthenticationToken authentication) throws AuthenticationException;
}
