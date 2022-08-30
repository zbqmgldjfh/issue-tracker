package codesquad.shine.support.auth.authentication.provider;

import codesquad.shine.support.auth.authentication.AuthenticationException;
import codesquad.shine.support.auth.authentication.userdetails.CustomUserDetailsService;
import codesquad.shine.support.auth.authentication.userdetails.UserDetails;
import codesquad.shine.support.auth.context.Authentication;

public class DaoAuthenticationProvider implements AuthenticationManager {

    private final CustomUserDetailsService userDetailService;

    public DaoAuthenticationProvider(CustomUserDetailsService customUserDetailsService) {
        this.userDetailService = customUserDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Object principal = authentication.getPrincipal();
        UserDetails loadedUser = userDetailService.loadUserByUsername(principal.toString());
        if (loadedUser == null) {
            throw new AuthenticationException("UserDetailsService returned null, which is an interface contract violation");
        }
        checkAuthentication(loadedUser, authentication);
        return new Authentication(loadedUser.getUsername(), loadedUser.getPassword(), loadedUser.getAuthorities());
    }

    private void checkAuthentication(UserDetails userDetails, Authentication authentication) {
        if (userDetails == null) {
            throw new AuthenticationException("사용자를 찾을 수 없습니다.");
        }

        if (!userDetails.checkCredentials(authentication.getCredentials())) {
            throw new AuthenticationException("비밀번호가 일치하지 않습니다.");
        }
    }
}
