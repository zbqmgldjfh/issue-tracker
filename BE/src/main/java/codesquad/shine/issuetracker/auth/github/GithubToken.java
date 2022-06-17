package codesquad.shine.issuetracker.auth.github;

import codesquad.shine.issuetracker.auth.OAuthToken;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GithubToken implements OAuthToken {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    private String scope;

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public String getTokenType() {
        return tokenType;
    }
}
