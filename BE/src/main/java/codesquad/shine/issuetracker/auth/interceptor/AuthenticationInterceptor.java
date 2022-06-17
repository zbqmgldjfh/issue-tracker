package codesquad.shine.issuetracker.auth.interceptor;

import codesquad.shine.issuetracker.auth.JwtTokenFactory;
import codesquad.shine.issuetracker.auth.annotation.ForLoginUser;
import codesquad.shine.issuetracker.exception.ErrorCode;
import codesquad.shine.issuetracker.exception.unchecked.NotAvailableException;
import codesquad.shine.issuetracker.exception.unchecked.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationInterceptor.class);
    private final JwtTokenFactory tokenFactory;

    public AuthenticationInterceptor(JwtTokenFactory tokenFactory) {
        this.tokenFactory = tokenFactory;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (loginRequired(handler)) {
            String token = resolveToken(request);
            String userEmail = tokenFactory.parsePayload(token);
            log.info("token is : {}", token);
            log.info("userEmail is : {}", userEmail);
            request.setAttribute("userEmail", userEmail);
        }
        return true;
    }

    private boolean loginRequired(Object handler) {
        return (handler instanceof HandlerMethod) && ((HandlerMethod) handler).hasMethodAnnotation(ForLoginUser.class);
    }

    private String resolveToken(HttpServletRequest request) {
        String authorizationInfo = request.getHeader("Authorization");
        if (authorizationInfo == null) {
            throw new NotFoundException(ErrorCode.TOKEN_NOT_FOUND);
        }
        String[] parts = authorizationInfo.split(" ");
        if (isInvalidToken(parts)) {
            throw new NotAvailableException(ErrorCode.INVALID_TOKEN);
        }
        return parts[1];
    }

    private boolean isInvalidToken(String[] parts) {
        return parts.length != 2 || !parts[0].equals("Bearer");
    }
}
