package codesquad.shine.issuetracker.auth;

import codesquad.shine.issuetracker.user.business.CustomUserDetailsService;
import codesquad.shine.issuetracker.user.domain.User;
import codesquad.shine.issuetracker.user.domain.UserRepository;
import codesquad.shine.support.auth.authentication.AuthenticationException;
import codesquad.shine.support.auth.authentication.filter.BasicAuthenticationFilter;
import codesquad.shine.support.auth.authentication.filter.TokenAuthenticationInterceptor;
import codesquad.shine.support.auth.authentication.filter.UsernamePasswordAuthenticationFilter;
import codesquad.shine.support.auth.authentication.handler.DefaultAuthenticationFailureHandler;
import codesquad.shine.support.auth.authentication.handler.DefaultAuthenticationSuccessHandler;
import codesquad.shine.support.auth.authentication.provider.AuthenticationManager;
import codesquad.shine.support.auth.authentication.provider.DaoAuthenticationProvider;
import codesquad.shine.support.auth.context.Authentication;
import com.amazonaws.util.Base64;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class AbstractAuthenticationProcessingFilterTest {

    public static final String USERNAME_FIELD = "username";
    public static final String PASSWORD_FIELD = "password";

    private static final String EMAIL = "admin@email.com";
    private static final String PASSWORD = "password";

    MockHttpServletRequest request;
    MockHttpServletResponse response;

    User user;

    DefaultAuthenticationSuccessHandler successHandler;
    DefaultAuthenticationFailureHandler failureHandler;
    AuthenticationManager provider;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.setMethod("POST");
        user = new User("shine", EMAIL, PASSWORD, "someUrl");

        successHandler = new DefaultAuthenticationSuccessHandler();
        failureHandler = new DefaultAuthenticationFailureHandler();
        provider = new DaoAuthenticationProvider(new CustomUserDetailsService(userRepository));
    }

    @DisplayName("Form 로그인에 성공하고 인증객체를 생성한다.")
    @Test
    public void form_authentication_success() throws IOException {
        // given
        request.setParameter(USERNAME_FIELD, EMAIL);
        request.setParameter(PASSWORD_FIELD, PASSWORD);

        UsernamePasswordAuthenticationFilter authenticationFilter =
                new UsernamePasswordAuthenticationFilter(successHandler, failureHandler, provider);

        given(userRepository.findUserByEmail(EMAIL)).willReturn(Optional.of(user));

        // when
        Authentication authentication = authenticationFilter.attemptAuthentication(request, response);

        // then
        assertAll(
                () -> assertThat(authentication.getPrincipal()).isEqualTo(EMAIL),
                () -> assertThat(authentication.getCredentials()).isEqualTo(PASSWORD)
        );
    }

    @DisplayName("존재하지 않는 회원은 Form 로그인에 실패하고 예외를 발생시킨다.")
    @Test
    public void form_authentication_exception() throws IOException {
        // given
        request.setParameter(USERNAME_FIELD, EMAIL);
        request.setParameter(PASSWORD_FIELD, PASSWORD);

        UsernamePasswordAuthenticationFilter authenticationFilter =
                new UsernamePasswordAuthenticationFilter(successHandler, failureHandler, provider);

        given(userRepository.findUserByEmail(EMAIL)).willThrow(new AuthenticationException());

        // when
        ThrowableAssert.ThrowingCallable actual = () ->
                authenticationFilter.attemptAuthentication(request, response);

        // then
        assertThatThrownBy(actual)
                .isInstanceOf(AuthenticationException.class);
    }

    @DisplayName("Basic 인증방식으로 로그인에 성공한다")
    @Test
    public void basic_authentication_success() throws IOException {
        // given
        String origin = EMAIL + ":" + PASSWORD;
        String basicAuthPayload = "Basic " + Base64.encodeAsString(origin.getBytes());
        request.addHeader("authorization", basicAuthPayload);

        BasicAuthenticationFilter authenticationFilter = new BasicAuthenticationFilter(successHandler, failureHandler, provider);

        given(userRepository.findUserByEmail(EMAIL)).willReturn(Optional.of(user));

        // when
        Authentication authentication = authenticationFilter.attemptAuthentication(request, response);

        // then
        assertAll(
                () -> assertThat(authentication.getPrincipal()).isEqualTo(EMAIL),
                () -> assertThat(authentication.getCredentials()).isEqualTo(PASSWORD)
        );
    }

    @DisplayName("Bearer 인증방식으로 로그인에 성공한다")
    @Test
    public void bearer_authentication_success() throws IOException {
        // given
        String requestBody = "{\"email\": \"admin@email.com\", \"password\": \"password\"}";

        request.setContent(requestBody.getBytes());

        TokenAuthenticationInterceptor authenticationInterceptor = new TokenAuthenticationInterceptor(successHandler, failureHandler, provider);

        given(userRepository.findUserByEmail(EMAIL)).willReturn(Optional.of(user));

        // when
        Authentication authentication = authenticationInterceptor.attemptAuthentication(request, response);

        // then
        assertAll(
                () -> assertThat(authentication.getPrincipal()).isEqualTo(EMAIL),
                () -> assertThat(authentication.getCredentials()).isEqualTo(PASSWORD)
        );
    }

    @AfterEach
    void tearDown() {
        request.clearAttributes();
        response.reset();
    }
}
