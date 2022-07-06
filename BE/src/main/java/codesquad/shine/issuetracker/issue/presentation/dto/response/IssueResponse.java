package codesquad.shine.issuetracker.issue.presentation.dto.response;

import codesquad.shine.issuetracker.common.vo.Assignee;
import codesquad.shine.issuetracker.label.business.dto.response.LabelDto;
import codesquad.shine.issuetracker.milestone.business.dto.MilestoneDto;
import codesquad.shine.issuetracker.user.presentation.dto.UserResponseDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
public class IssueResponse {

    private String title;
    private UserResponseDto author;
    private LocalDateTime createdDateTime;
    private List<Assignee> assignees;
    private List<LabelDto> labels;
    private MilestoneDto milestone;

    @QueryProjection
    public IssueResponse(String title, UserResponseDto author, LocalDateTime createdDateTime, List<Assignee> assignees, List<LabelDto> labels, MilestoneDto milestone) {
        this.title = title;
        this.author = author;
        this.createdDateTime = createdDateTime;
        this.assignees = assignees;
        this.labels = labels;
        this.milestone = milestone;
    }
}
