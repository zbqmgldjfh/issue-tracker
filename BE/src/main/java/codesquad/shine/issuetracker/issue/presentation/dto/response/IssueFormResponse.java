package codesquad.shine.issuetracker.issue.presentation.dto.response;

import codesquad.shine.issuetracker.common.vo.Assignee;
import codesquad.shine.issuetracker.label.business.dto.response.LabelDto;
import codesquad.shine.issuetracker.milestone.business.dto.MilestoneDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IssueFormResponse {
    private List<Assignee> assignees;
    private List<LabelDto> labels;
    private List<MilestoneDto> milestones;
}
