package codesquad.shine.support.auth.authorization.secured;

import codesquad.shine.support.auth.context.Authentication;
import codesquad.shine.support.auth.context.SecurityContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
public class SecuredAnnotationValidator {

    @Before("@annotation(annotation)")
    public void validationAuthorities(JoinPoint joinPoint, Secured annotation) {
        List<String> values = Arrays.stream(annotation.value()).collect(Collectors.toList());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getAuthorities().stream()
                .filter(values::contains)
                .findFirst()
                .orElseThrow(() -> new RoleAuthenticationException("권한이 없습니다."));
    }
}
