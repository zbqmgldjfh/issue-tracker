package codesquad.shine.issuetracker.config;

import codesquad.shine.issuetracker.common.StringToStatusConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 프로그램에서 제공하는 URL
                .allowedOrigins("http://localhost:3000", "https://d26oajc5ekxmpv.cloudfront.net")
                .allowedMethods("*")
                .allowCredentials(true)
                .exposedHeaders("Authorization");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToStatusConverter());
    }
}
