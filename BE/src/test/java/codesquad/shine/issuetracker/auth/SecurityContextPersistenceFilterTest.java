package codesquad.shine.issuetracker.auth;

import codesquad.shine.support.auth.context.SecurityContext;
import codesquad.shine.support.auth.context.SecurityContextHolder;
import codesquad.shine.support.auth.context.SecurityContextPersistenceFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


public class SecurityContextPersistenceFilterTest {

    public static final String USERNAME_FIELD = "username";
    public static final String PASSWORD_FIELD = "password";

    private static final String EMAIL = "admin@email.com";
    private static final String PASSWORD = "password";

    MockHttpServletRequest request;
    MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.setAttribute(USERNAME_FIELD, EMAIL);
        request.setAttribute(PASSWORD_FIELD, PASSWORD);
    }

    @Test
    public void before_authentication() throws Exception {
        // given
        SecurityContextPersistenceFilter securityContextPersistenceFilter = new SecurityContextPersistenceFilter();

        // when
        boolean isSecurityContextExists = securityContextPersistenceFilter.preHandle(request, response, new Object());
        SecurityContext securityContext = SecurityContextHolder.getContext();

        // then
        assertAll(
                () -> assertThat(isSecurityContextExists).isTrue(),
                () -> assertThat(securityContext).isNotNull()
        );

    }
}
