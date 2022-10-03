package codesquad.shine.issuetracker;

import codesquad.shine.issuetracker.common.redis.RedisService;
import codesquad.shine.issuetracker.user.business.CustomUserDetailsService;
import codesquad.shine.support.auth.authentication.filter.BasicAuthenticationFilter;
import codesquad.shine.support.auth.authentication.filter.BearerTokenAuthenticationFilter;
import codesquad.shine.support.auth.authentication.filter.TokenAuthenticationInterceptor;
import codesquad.shine.support.auth.authentication.filter.TokenRefreshInterceptor;
import codesquad.shine.support.auth.authentication.filter.UsernamePasswordAuthenticationFilter;
import codesquad.shine.support.auth.authentication.handler.AuthenticationFailureHandler;
import codesquad.shine.support.auth.authentication.handler.AuthenticationSuccessHandler;
import codesquad.shine.support.auth.authentication.handler.DefaultAuthenticationFailureHandler;
import codesquad.shine.support.auth.authentication.handler.DefaultAuthenticationSuccessHandler;
import codesquad.shine.support.auth.authentication.handler.LoginAuthenticationFailureHandler;
import codesquad.shine.support.auth.authentication.handler.RefreshTokenAuthenticationSuccessHandler;
import codesquad.shine.support.auth.authentication.handler.TokenAuthenticationSuccessHandler;
import codesquad.shine.support.auth.authentication.provider.AuthenticationManager;
import codesquad.shine.support.auth.authentication.provider.DaoAuthenticationProvider;
import codesquad.shine.support.auth.authentication.provider.RefreshTokenAuthenticationProvider;
import codesquad.shine.support.auth.authentication.provider.TokenAuthenticationProvider;
import codesquad.shine.support.auth.authorization.AuthenticationPrincipalArgumentResolver;
import codesquad.shine.support.auth.authorization.secured.SecuredAnnotationValidator;
import codesquad.shine.support.auth.context.SecurityContextPersistenceFilter;
import codesquad.shine.support.auth.token.JwtTokenFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class AuthConfig implements WebMvcConfigurer {

    @Value("${jwt.token.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expire-length}")
    private long validityInMilliseconds;

    @Value("${jwt.token.refresh-length}")
    private long refreshInMilliseconds;

    private CustomUserDetailsService customUserDetailsService;
    private RedisService redisService;

    public AuthConfig(CustomUserDetailsService customUserDetailsService, RedisService redisService) {
        this.customUserDetailsService = customUserDetailsService;
        this.redisService = redisService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SecurityContextPersistenceFilter());
        registry.addInterceptor(new UsernamePasswordAuthenticationFilter(successHandler(), loginFailureHandler(), daoAuthenticationProvider())).addPathPatterns("/login/form");
        registry.addInterceptor(new TokenRefreshInterceptor(refreshTokenAuthenticationSuccessHandler(), failureHandler(), refreshTokenAuthenticationProvider())).addPathPatterns("/login/reissue");
        registry.addInterceptor(new TokenAuthenticationInterceptor(tokenAuthenticationSuccessHandler(), failureHandler(), daoAuthenticationProvider())).addPathPatterns("/login/token");
        registry.addInterceptor(new BasicAuthenticationFilter(successHandler(), failureHandler(), daoAuthenticationProvider()));
        registry.addInterceptor(new BearerTokenAuthenticationFilter(successHandler(), failureHandler(), tokenAuthenticationProvider()));
    }

    @Override
    public void addArgumentResolvers(List argumentResolvers) {
        argumentResolvers.add(new AuthenticationPrincipalArgumentResolver());
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        return new DaoAuthenticationProvider(customUserDetailsService);
    }

    @Bean
    AuthenticationManager tokenAuthenticationProvider() {
        return new TokenAuthenticationProvider(jwtTokenFactory());
    }

    @Bean
    TokenAuthenticationSuccessHandler tokenAuthenticationSuccessHandler() {
        return new TokenAuthenticationSuccessHandler(jwtTokenFactory());
    }

    @Bean
    RefreshTokenAuthenticationProvider refreshTokenAuthenticationProvider() {
        return new RefreshTokenAuthenticationProvider();
    }

    @Bean
    RefreshTokenAuthenticationSuccessHandler refreshTokenAuthenticationSuccessHandler() {
        return new RefreshTokenAuthenticationSuccessHandler(jwtTokenFactory());
    }

    @Bean
    JwtTokenFactory jwtTokenFactory() {
        return new JwtTokenFactory(secretKey, validityInMilliseconds, refreshInMilliseconds, redisService);
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
    SecuredAnnotationValidator securedAnnotationValidator() {
        return new SecuredAnnotationValidator();
    }
}
