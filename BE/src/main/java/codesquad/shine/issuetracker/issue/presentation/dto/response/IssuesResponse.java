package codesquad.shine.issuetracker.issue.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IssuesResponse {

    private Long openCount;
    private Long closedCount;
    private Long labelCount;
    private Long milestoneCount;
    private Page<IssueResponse> issues;
}
