package codesquad.shine.issuetracker.comment.presentation.dto.response;

import codesquad.shine.issuetracker.comment.domain.Comment;
import codesquad.shine.issuetracker.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentDto {
    private User user;
    private String description;
    private String avatarUrl;
    private LocalDateTime createdDateTime;

    private CommentDto(User user, String description, String avatarUrl, LocalDateTime createdDateTime) {
        this.user = user;
        this.description = description;
        this.avatarUrl = avatarUrl;
        this.createdDateTime = createdDateTime;
    }

    public static CommentDto of(Comment comment) {
        return new CommentDto(comment.getUser(), comment.getDescription(), comment.getUser().getAvatarUrl(), comment.getCreatedDateTime());
    }
}
