package codesquad.shine.issuetracker;

import codesquad.shine.issuetracker.user.business.CustomUserDetailsService;
import codesquad.shine.support.auth.authentication.filter.BasicAuthenticationFilter;
import codesquad.shine.support.auth.authentication.filter.BearerTokenAuthenticationFilter;
import codesquad.shine.support.auth.authentication.filter.UsernamePasswordAuthenticationFilter;
import codesquad.shine.support.auth.authentication.handler.AuthenticationFailureHandler;
import codesquad.shine.support.auth.authentication.handler.AuthenticationSuccessHandler;
import codesquad.shine.support.auth.authentication.handler.DefaultAuthenticationFailureHandler;
import codesquad.shine.support.auth.authentication.handler.DefaultAuthenticationSuccessHandler;
import codesquad.shine.support.auth.authentication.handler.LoginAuthenticationFailureHandler;
import codesquad.shine.support.auth.authentication.handler.TokenAuthenticationSuccessHandler;
import codesquad.shine.support.auth.authentication.provider.AuthenticationManager;
import codesquad.shine.support.auth.authentication.provider.DaoAuthenticationProvider;
import codesquad.shine.support.auth.authentication.provider.TokenAuthenticationProvider;
import codesquad.shine.support.auth.authorization.AuthenticationPrincipalArgumentResolver;
import codesquad.shine.support.auth.authorization.secured.SecuredAnnotationChecker;
import codesquad.shine.support.auth.context.SecurityContextPersistenceFilter;
import codesquad.shine.support.auth.token.JwtTokenProvider;
import codesquad.shine.support.auth.token.TokenAuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class AuthConfig implements WebMvcConfigurer {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length}")
    private long validityInMilliseconds;

    private CustomUserDetailsService customUserDetailsService;

    public AuthConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SecurityContextPersistenceFilter());
        registry.addInterceptor(new UsernamePasswordAuthenticationFilter(successHandler(), loginFailureHandler(), daoAuthenticationProvider())).addPathPatterns("/login/form");
        registry.addInterceptor(new TokenAuthenticationInterceptor(tokenAuthenticationSuccessHandler(), loginFailureHandler(), daoAuthenticationProvider())).addPathPatterns("/login/token");
        registry.addInterceptor(new BasicAuthenticationFilter(successHandler(), failureHandler(), daoAuthenticationProvider()));
        registry.addInterceptor(new BearerTokenAuthenticationFilter(successHandler(), failureHandler(), tokenAuthenticationProvider()));
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        return new DaoAuthenticationProvider(customUserDetailsService);
    }

    @Override
    public void addArgumentResolvers(List argumentResolvers) {
        argumentResolvers.add(new AuthenticationPrincipalArgumentResolver());
    }

    @Bean
    AuthenticationManager tokenAuthenticationProvider() {
        return new TokenAuthenticationProvider(jwtTokenProvider());
    }

    @Bean
    TokenAuthenticationSuccessHandler tokenAuthenticationSuccessHandler() {
        return new TokenAuthenticationSuccessHandler(jwtTokenProvider());
    }

    @Bean
    AuthenticationSuccessHandler successHandler() {
        return new DefaultAuthenticationSuccessHandler();
    }

    @Bean
    AuthenticationFailureHandler failureHandler() {
        return new DefaultAuthenticationFailureHandler();
    }

    @Bean
    AuthenticationFailureHandler loginFailureHandler() {
        return new LoginAuthenticationFailureHandler();
    }

    @Bean
    JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider(secretKey, validityInMilliseconds);
    }

    @Bean
    SecuredAnnotationChecker securedAnnotationChecker() {
        return new SecuredAnnotationChecker();
    }
}
