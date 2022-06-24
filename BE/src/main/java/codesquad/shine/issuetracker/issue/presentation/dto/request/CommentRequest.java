package codesquad.shine.issuetracker.issue.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    private String comment;

    public String getComment() {
        return comment;
    }
}
