package codesquad.shine.issuetracker.issue.presentation.dto.response;

import codesquad.shine.issuetracker.common.vo.Assignee;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AssigneesResponse {

    private List<Assignee> assignees;

    public AssigneesResponse(List<Assignee> assignees) {
        this.assignees = assignees;
    }
}
