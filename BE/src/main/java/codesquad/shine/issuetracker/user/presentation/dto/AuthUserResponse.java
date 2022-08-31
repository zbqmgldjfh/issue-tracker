package codesquad.shine.issuetracker.user.presentation.dto;

import codesquad.shine.issuetracker.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserResponse {

    private Long id;
    private String name;
    private String email;

    public static AuthUserResponse from(User user) {
        return new AuthUserResponse(user.getId(), user.getUserName(), user.getEmail());
    }
}
