package codesquad.shine.issuetracker.auth;

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
    private String avatarUrl;
}
