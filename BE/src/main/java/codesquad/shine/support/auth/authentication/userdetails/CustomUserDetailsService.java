package codesquad.shine.support.auth.authentication.userdetails;

import codesquad.shine.issuetracker.user.domain.User;
import codesquad.shine.issuetracker.user.domain.UserRepository;
import codesquad.shine.support.auth.authentication.AuthenticationException;

public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(AuthenticationException::new);

        return new AuthUser(user.getEmail(), user.getPassword(), user.getRoles());
    }
}
