package codesquad.shine.issuetracker.issue.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AssigneesEditRequest {
    List<Long> assigneeIds;

    public AssigneesEditRequest(List<Long> assigneeIds) {
        this.assigneeIds = assigneeIds;
    }
}
