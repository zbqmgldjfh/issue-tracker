package codesquad.shine.issuetracker.issue.presentation.dto.response;

import codesquad.shine.issuetracker.comment.presentation.dto.response.CommentDto;
import codesquad.shine.issuetracker.common.vo.Assignee;
import codesquad.shine.issuetracker.label.business.dto.response.LabelDto;
import codesquad.shine.issuetracker.milestone.business.dto.MilestoneDto;
import codesquad.shine.issuetracker.user.presentation.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IssueDetailResponse {

    private String title;
    private UserResponseDto author;
    private Boolean open;
    private LocalDateTime createdDateTime;
    private List<CommentDto> comments;
    private List<Assignee> assignees;
    private List<LabelDto> labels;
    private MilestoneDto milestone;
}
