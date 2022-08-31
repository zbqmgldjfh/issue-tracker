package codesquad.shine.issuetracker;

import codesquad.shine.issuetracker.user.business.CustomUserDetailsService;
import codesquad.shine.support.auth.authentication.context.SecurityContextPersistenceFilter;
import codesquad.shine.support.auth.authentication.filter.UsernamePasswordAuthenticationFilter;
import codesquad.shine.support.auth.authentication.handler.AuthenticationFailureHandler;
import codesquad.shine.support.auth.authentication.handler.AuthenticationSuccessHandler;
import codesquad.shine.support.auth.authentication.handler.DefaultAuthenticationFailureHandler;
import codesquad.shine.support.auth.authentication.handler.DefaultAuthenticationSuccessHandler;
import codesquad.shine.support.auth.authentication.handler.LoginAuthenticationFailureHandler;
import codesquad.shine.support.auth.authentication.provider.DaoAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthConfig implements WebMvcConfigurer {

    private CustomUserDetailsService customUserDetailsService;

    public AuthConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SecurityContextPersistenceFilter());
        registry.addInterceptor(new UsernamePasswordAuthenticationFilter(successHandler(), loginFailureHandler(), daoAuthenticationProvider())).addPathPatterns("/login/form");
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        return new DaoAuthenticationProvider(customUserDetailsService);
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
}
