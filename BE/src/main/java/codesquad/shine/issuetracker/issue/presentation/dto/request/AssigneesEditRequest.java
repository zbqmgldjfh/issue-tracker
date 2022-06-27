package codesquad.shine.issuetracker.issue.presentation.dto.request;

import codesquad.shine.issuetracker.common.vo.Assignee;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AssigneesEditRequest {
    List<Assignee> assignees;

    public AssigneesEditRequest(List<Assignee> assignees) {
        this.assignees = assignees;
    }
}
