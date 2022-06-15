package codesquad.shine.issuetracker.auth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OAuthProvider {
    // user
    private String clientId;
    private String clientSecret;
    private String redirectUri;

    // path
    private String accessTokenPath;
    private String resourcePath;

    public OAuthProvider(OAuthProperties.User user, OAuthProperties.Provider provider) {
        this.clientId = user.getClientId();
        this.clientSecret = user.getClientSecret();
        this.redirectUri = user.getRedirectUri();
        this.accessTokenPath = provider.getAccessTokenPath();
        this.resourcePath = provider.getResourcePath();
    }
}
