package codesquad.shine.issuetracker.common.vo;

import codesquad.shine.issuetracker.user.domain.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "userId")
public class Assignee {

    private static final String INVALID_RANGE_ID = "회원 아이디는 1부터 가능합니다.";
    private static final String INAVLID_LENGTH_NAME = "회원 이름의 길이는 1이상 이여야만 합니다.";
    private final Long userId;
    private final String userName;
    private final String avatarUrl;
    private final boolean isAssigned;

    public Assignee(Long userId, String userName, String avatarUrl, boolean isAssigned) {
        isValidRangeId(userId);
        isValidLengthName(userName);
        this.userId = userId;
        this.userName = userName;
        this.avatarUrl = avatarUrl;
        this.isAssigned = isAssigned;
    }

    public static Assignee of(User user, Boolean isAssigned) {
        return new Assignee(user.getId(), user.getUserName(), user.getAvatarUrl(), isAssigned);
    }

    private void isValidRangeId(Long userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException(INVALID_RANGE_ID);
        }
    }

    private void isValidLengthName(String userName) {
        if (userName == null || userName.isBlank()) {
            throw new IllegalArgumentException(INAVLID_LENGTH_NAME);
        }
    }
}
