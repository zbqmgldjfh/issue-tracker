package codesquad.shine.issuetracker.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private Long id;
    private String name;
    private String email;
    private String avatarUrl;
    private String token;

    public static LoginResponse of(UserInfo userInfo) {
        return new LoginResponse(userInfo.getId(), userInfo.getName(), userInfo.getEmail(), userInfo.getAvatarUrl(), userInfo.getAccessToken());
    }
}
