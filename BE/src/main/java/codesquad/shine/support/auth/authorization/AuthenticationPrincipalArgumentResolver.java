package codesquad.shine.support.auth.authorization;

import codesquad.shine.support.auth.context.Authentication;
import codesquad.shine.support.auth.context.SecurityContextHolder;
import codesquad.shine.support.auth.userdetails.AuthUser;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isUsed = parameter.getParameterAnnotation(AuthenticationPrincipal.class).required();

        if (Objects.isNull(authentication) && isUsed == false) {
            return AuthUser.anonymous();
        }

        return new AuthUser(authentication.getPrincipal().toString(), null, authentication.getAuthorities());
    }
}
