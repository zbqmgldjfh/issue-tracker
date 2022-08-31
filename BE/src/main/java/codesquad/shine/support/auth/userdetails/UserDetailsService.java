package codesquad.shine.support.auth.userdetails;

public interface UserDetailsService {
    UserDetails loadUserByUsername(String username);
}
