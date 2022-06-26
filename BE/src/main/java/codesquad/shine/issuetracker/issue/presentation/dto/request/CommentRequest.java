package codesquad.shine.issuetracker.issue.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {

    @NotBlank
    private String comment;

    public String getComment() {
        return comment;
    }
}
