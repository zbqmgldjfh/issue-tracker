package codesquad.shine.issuetracker.auth;

public interface OAuthToken {

    String getAccessToken();

    String getTokenType();
}
