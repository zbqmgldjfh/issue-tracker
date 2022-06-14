package codesquad.shine.issuetracker.auth.interceptor;

import codesquad.shine.issuetracker.auth.JwtTokenFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
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
        String token = resolveToken(request);
        String userEmail = tokenFactory.parsePayload(token);
        log.debug("token is : {}", token);
        log.debug("userEmail is : {}", userEmail);
        request.setAttribute("userEmail", userEmail);
        return true;
    }

    private String resolveToken(HttpServletRequest request) {
        String authorizationInfo = request.getHeader("Authorization");
        if (authorizationInfo == null) {
            throw new IllegalStateException("토큰이 없습니다. 로그인 먼저 해주세요.");
        }
        String[] parts = authorizationInfo.split(" ");
        if (isInvalidToken(parts)) {
            throw new IllegalStateException("정상적인 형태로 토큰을 전달해주세요.");
        }
        return parts[1];
    }

    private boolean isInvalidToken(String[] parts) {
        return parts.length != 2 || !parts[0].equals("Bearer");
    }
}
