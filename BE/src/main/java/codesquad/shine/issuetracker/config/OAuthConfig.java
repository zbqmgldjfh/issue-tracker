package codesquad.shine.issuetracker.config;

import codesquad.shine.issuetracker.auth.InMemoryProviderRepository;
import codesquad.shine.issuetracker.auth.OAuthAdapter;
import codesquad.shine.issuetracker.auth.OAuthProperties;
import codesquad.shine.issuetracker.auth.OAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(OAuthProperties.class)
public class OAuthConfig {

    private final OAuthProperties properties;

    @Bean
    public InMemoryProviderRepository inMemoryProviderRepository() {
        Map<String, OAuthProvider> providers = OAuthAdapter.getOauthProviders(properties);
        return new InMemoryProviderRepository(providers);
    }
}
