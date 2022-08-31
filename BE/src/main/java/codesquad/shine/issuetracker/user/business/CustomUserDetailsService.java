package codesquad.shine.issuetracker.user.business;

import codesquad.shine.issuetracker.user.domain.User;
import codesquad.shine.issuetracker.user.domain.UserRepository;
import codesquad.shine.support.auth.authentication.AuthenticationException;
import codesquad.shine.support.auth.authentication.userdetails.AuthUser;
import codesquad.shine.support.auth.authentication.userdetails.UserDetails;
import codesquad.shine.support.auth.authentication.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
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
