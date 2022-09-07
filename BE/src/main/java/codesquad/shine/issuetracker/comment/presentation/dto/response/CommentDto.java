package codesquad.shine.issuetracker.comment.presentation.dto.response;

import codesquad.shine.issuetracker.comment.domain.Comment;
import codesquad.shine.issuetracker.user.presentation.dto.UserResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CommentDto {

    private final Long id;
    private final UserResponseDto userResponseDto;
    private final String description;
    private final String avatarUrl;
    private final LocalDateTime createdDateTime;

    public static CommentDto of(Comment comment) {
        return new CommentDto(comment.getId(),
                UserResponseDto.of(comment.getUser()),
                comment.getDescription(),
                comment.getUser().getAvatarUrl(),
                comment.getCreatedDateTime()
        );
    }
}
