package codesquad.shine.issuetracker.config;

import codesquad.shine.issuetracker.auth.interceptor.AuthenticationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;

    public WebConfig(AuthenticationInterceptor authenticationInterceptor) {
        this.authenticationInterceptor = authenticationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/auth/**", "/health", "/favicon.ico", "/css/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 프로그램에서 제공하는 URL
                .allowedOrigins("http://localhost:3000") // 청을 허용할 출처를 명시, 전체 허용 (가능하다면 목록을 작성한다.
                .allowCredentials(true)
                .exposedHeaders("authorization");
    }
}
