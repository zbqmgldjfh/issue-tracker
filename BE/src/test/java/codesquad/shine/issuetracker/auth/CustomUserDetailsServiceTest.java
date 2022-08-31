package codesquad.shine.issuetracker.auth;

import codesquad.shine.issuetracker.user.business.CustomUserDetailsService;
import codesquad.shine.issuetracker.user.domain.User;
import codesquad.shine.issuetracker.user.domain.UserRepository;
import codesquad.shine.support.auth.authentication.AuthenticationException;
import codesquad.shine.support.auth.authentication.userdetails.UserDetails;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    private static final String EMAIL = "admin@email.com";
    private static final String PASSWORD = "password";

    @Mock
    UserRepository userRepository;

    User user;
    CustomUserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        user = new User("shine", EMAIL, PASSWORD, "someUrl");
        userDetailsService = new CustomUserDetailsService(userRepository);
    }

    @DisplayName("Email을 통해 사용자를 찾아서 UserDetails로 반환한다")
    @Test
    public void load_user_by_username_Test() {
        // given
        given(userRepository.findUserByEmail(anyString())).willReturn(Optional.of(user));

        // when
        UserDetails userDetails = userDetailsService.loadUserByUsername(EMAIL);

        // then
        assertAll(
                () -> assertThat(userDetails.getUsername()).isEqualTo(EMAIL),
                () -> assertThat(userDetails.getPassword()).isEqualTo(PASSWORD)
        );
    }

    @DisplayName("Email을 통해 사용자를 찾이 못하는 경우 예외가 발생한다")
    @Test
    public void load_user_by_username_exception_Test() {
        // given
        given(userRepository.findUserByEmail(anyString())).willThrow(new AuthenticationException("해당 사용자를 찾을 수 없습니다."));

        // when
        ThrowableAssert.ThrowingCallable actual = () -> userDetailsService.loadUserByUsername(EMAIL);

        // then
        assertThatThrownBy(actual).isInstanceOf(AuthenticationException.class);
    }
}
