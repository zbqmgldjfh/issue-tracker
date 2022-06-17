package codesquad.shine.issuetracker.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OAuthUser {
    private String oauthId;
    private String email;
    private String name;

    @JsonProperty("avatar_url")
    private String avatarUrl;
}
