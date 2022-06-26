package codesquad.shine.issuetracker.user.presentation.dto;

import codesquad.shine.issuetracker.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {

    private String userName;
    private String avatarUrl;

    private UserResponseDto(String userName, String avatarUrl) {
        this.userName = userName;
        this.avatarUrl = avatarUrl;
    }

    public static UserResponseDto of(User user) {
        return new UserResponseDto(user.getUserName(), user.getAvatarUrl());
    }
}
