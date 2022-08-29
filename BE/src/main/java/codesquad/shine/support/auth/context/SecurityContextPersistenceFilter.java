package codesquad.shine.support.auth.context;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static codesquad.shine.support.auth.context.SecurityContextHolder.SPRING_SECURITY_CONTEXT_KEY;

public class SecurityContextPersistenceFilter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        if (session == null) {
            return false;
        }

        Object contextFromSession = session.getAttribute(SPRING_SECURITY_CONTEXT_KEY);
        if (contextFromSession == null) {
            contextFromSession = SecurityContextHolder.createEmptyContext();
        }

        if (!(contextFromSession instanceof SecurityContext)) {
            return false;
        }

        SecurityContextHolder.setContext((SecurityContext) contextFromSession);
        return true;
    }
}
