package codesquad.shine.issuetracker.issue.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor
public class IssueRequest {

    @NotBlank
    private String title;
    private String comment;
    private List<Long> assigneeIds;
    private List<Long> labelIds;
    private Long milestoneId;
}
