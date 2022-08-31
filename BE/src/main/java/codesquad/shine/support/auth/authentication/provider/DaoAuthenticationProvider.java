package codesquad.shine.support.auth.authentication.provider;

import codesquad.shine.support.auth.authentication.AuthenticationException;
import codesquad.shine.support.auth.authentication.AuthenticationToken;
import codesquad.shine.support.auth.context.Authentication;
import codesquad.shine.support.auth.userdetails.UserDetails;
import codesquad.shine.support.auth.userdetails.UserDetailsService;

public class DaoAuthenticationProvider implements AuthenticationManager {

    private final UserDetailsService userDetailService;

    public DaoAuthenticationProvider(UserDetailsService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Override
    public Authentication authenticate(AuthenticationToken authentication) throws AuthenticationException {
        Object principal = authentication.getPrincipal();
        UserDetails loadedUser = userDetailService.loadUserByUsername(principal.toString());
        if (loadedUser == null) {
            throw new AuthenticationException("UserDetailsService returned null, which is an interface contract violation");
        }
        checkAuthentication(loadedUser, authentication);
        return new Authentication(loadedUser.getUsername(), loadedUser.getPassword(), loadedUser.getAuthorities());
    }

    private void checkAuthentication(UserDetails userDetails, AuthenticationToken authentication) {
        if (userDetails == null) {
            throw new AuthenticationException("사용자를 찾을 수 없습니다.");
        }

        if (!userDetails.checkCredentials(authentication.getCredentials())) {
            throw new AuthenticationException("비밀번호가 일치하지 않습니다.");
        }
    }
}
