package codesquad.shine.issuetracker.issue.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MilestoneCheckRequest {
    Long milestoneId;

    public MilestoneCheckRequest(Long milestoneId) {
        this.milestoneId = milestoneId;
    }
}
