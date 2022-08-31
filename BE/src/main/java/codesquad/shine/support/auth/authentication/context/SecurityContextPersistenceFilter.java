package codesquad.shine.support.auth.authentication.context;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static codesquad.shine.support.auth.authentication.context.SecurityContextHolder.SPRING_SECURITY_CONTEXT_KEY;

public class SecurityContextPersistenceFilter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SecurityContext contextFromSession = loadContext(request);
        SecurityContextHolder.setContext(contextFromSession);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SecurityContext contextAfterChainExecution = SecurityContextHolder.getContext();
        SecurityContextHolder.clearContext();
        saveContext(contextAfterChainExecution, request);
    }

    private SecurityContext loadContext(HttpServletRequest request) {
        HttpSession session = request.getSession();
        SecurityContext securityContext = readSecurityContextFromSession(session);

        if (securityContext == null) {
            return SecurityContextHolder.createEmptyContext();
        }

        return securityContext;
    }

    private SecurityContext readSecurityContextFromSession(HttpSession httpSession) {
        if (httpSession == null) {
            return null;
        }

        Object contextFromSession = httpSession.getAttribute(SPRING_SECURITY_CONTEXT_KEY);
        if (contextFromSession == null) {
            return null;
        }

        if (!(contextFromSession instanceof SecurityContext)) {
            return null;
        }

        return (SecurityContext) contextFromSession;
    }

    private void saveContext(SecurityContext securityContext, HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute(SPRING_SECURITY_CONTEXT_KEY, securityContext);
    }
}
