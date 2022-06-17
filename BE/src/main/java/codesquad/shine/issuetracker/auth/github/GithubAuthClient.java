package codesquad.shine.issuetracker.auth.github;

import codesquad.shine.issuetracker.auth.AuthClient;
import codesquad.shine.issuetracker.auth.OAuthProvider;
import codesquad.shine.issuetracker.auth.OAuthUser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@Component(value = "github")
public class GithubAuthClient implements AuthClient {

    @Override
    public GithubToken getToken(String code, OAuthProvider provider) {
        return WebClient.create()
                .post()
                .uri(URI.create(provider.getAccessTokenPath()))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(createParams(code, provider))
                .retrieve()
                .bodyToMono(GithubToken.class)
                .block();
    }

    @Override
    public OAuthUser getUser(String accessToken, OAuthProvider provider) {
        return WebClient.create()
                .get()
                .uri(URI.create(provider.getResourcePath()))
                .header(HttpHeaders.ACCEPT, "application/vnd.github.v3+json")
                .header(HttpHeaders.AUTHORIZATION, "token " + accessToken)
                .retrieve()
                .bodyToMono(OAuthUser.class)
                .block();
    }

    private MultiValueMap<String, String> createParams(String code, OAuthProvider provider) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", provider.getClientId());
        params.add("client_secret", provider.getClientSecret());
        params.add("code", code);
        return params;
    }
}
