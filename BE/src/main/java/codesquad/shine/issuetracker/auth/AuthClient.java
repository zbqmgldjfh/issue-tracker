package codesquad.shine.issuetracker.auth;

public interface AuthClient {
    OAuthToken getToken(String code, OAuthProvider provider);

    OAuthUser getUser(String accessToken, OAuthProvider provider);
}
