package codesquad.shine.issuetracker.issue.business;

import codesquad.shine.issuetracker.comment.domain.Comment;
import codesquad.shine.issuetracker.common.vo.Assignee;
import codesquad.shine.issuetracker.issue.domain.Issue;
import codesquad.shine.issuetracker.issue.domain.IssueRepository;
import codesquad.shine.issuetracker.issue.presentation.dto.request.IssueRequest;
import codesquad.shine.issuetracker.issue.presentation.dto.response.IssueFormResponse;
import codesquad.shine.issuetracker.label.business.LabelService;
import codesquad.shine.issuetracker.label.business.dto.response.LabelDto;
import codesquad.shine.issuetracker.label.domain.Label;
import codesquad.shine.issuetracker.milestone.business.MilestoneService;
import codesquad.shine.issuetracker.milestone.business.dto.MilestoneDto;
import codesquad.shine.issuetracker.milestone.domain.Milestone;
import codesquad.shine.issuetracker.user.business.UserService;
import codesquad.shine.issuetracker.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final UserService userService;
    private final LabelService labelService;
    private final MilestoneService milestoneService;

    public IssueFormResponse getIssueForm() {
        List<Assignee> assigneeList = userService.getAllAssignee();
        List<LabelDto> labelList = labelService.findAllDto();
        List<MilestoneDto> milestoneList = milestoneService.findAllDto();
        return new IssueFormResponse(assigneeList, labelList, milestoneList);
    }

    public void create(IssueRequest request, String userEmail) {
        User findUser = userService.findUserByEmail(userEmail);

        // 코멘트 생성
        Comment newComment = createComment(findUser, request.getComment());

        // 마일스톤 생성
        Milestone findMilestone = milestoneService.findById(request.getMilestoneId());

        // assignee 찾아오기
        List<User> assigneeList = userService.getAssigneeInId(request.getAssigneeIds());

        // label 찾아오기
        List<Label> labelList = labelService.getLabelsInId(request.getLabelIds());

        // 이슈 생성
        Issue newIssue = new Issue(request.getTitle(), true, findUser);

        // 할당
        newIssue.addComment(newComment);
        newIssue.addMilestone(findMilestone);
        newIssue.addAssignees(assigneeList);
        newIssue.addLabels(labelList);

        // 저장
        issueRepository.save(newIssue);
    }

    private Comment createComment(User findUser, String comment) {
        if (comment != null && !comment.isBlank()) {
            return new Comment(comment, findUser);
        }
        return null;
    }
}
