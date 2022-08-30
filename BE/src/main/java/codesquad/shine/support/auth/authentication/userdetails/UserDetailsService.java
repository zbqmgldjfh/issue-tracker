package codesquad.shine.support.auth.authentication.userdetails;

public interface UserDetailsService {
    UserDetails loadUserByUsername(String username);
}
