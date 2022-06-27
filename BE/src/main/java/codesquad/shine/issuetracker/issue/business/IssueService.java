package codesquad.shine.issuetracker.issue.business;

import codesquad.shine.issuetracker.comment.domain.Comment;
import codesquad.shine.issuetracker.comment.presentation.dto.response.CommentDto;
import codesquad.shine.issuetracker.common.vo.Assignee;
import codesquad.shine.issuetracker.exception.ErrorCode;
import codesquad.shine.issuetracker.exception.unchecked.NotAvailableException;
import codesquad.shine.issuetracker.exception.unchecked.NotFoundException;
import codesquad.shine.issuetracker.issue.domain.Issue;
import codesquad.shine.issuetracker.issue.domain.IssueRepository;
import codesquad.shine.issuetracker.issue.presentation.dto.request.IssueRequest;
import codesquad.shine.issuetracker.issue.presentation.dto.request.IssueTitleRequest;
import codesquad.shine.issuetracker.issue.presentation.dto.request.StatusRequest;
import codesquad.shine.issuetracker.issue.presentation.dto.response.*;
import codesquad.shine.issuetracker.label.business.LabelService;
import codesquad.shine.issuetracker.label.business.dto.response.LabelDto;
import codesquad.shine.issuetracker.label.domain.Label;
import codesquad.shine.issuetracker.milestone.business.MilestoneService;
import codesquad.shine.issuetracker.milestone.business.dto.MilestoneDto;
import codesquad.shine.issuetracker.milestone.domain.Milestone;
import codesquad.shine.issuetracker.user.business.UserService;
import codesquad.shine.issuetracker.user.domain.User;
import codesquad.shine.issuetracker.user.presentation.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final UserService userService;
    private final LabelService labelService;
    private final MilestoneService milestoneService;

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public IssueDetailResponse findIssueDetailById(Long issueId) {
        Issue findIssue = issueRepository.optimizationFindById(issueId) // toOne relation fetch-join
                .orElseThrow(() -> new NotFoundException(ErrorCode.ISSUE_NOT_FOUND));

        return IssueDetailResponse.builder()
                .title(findIssue.getTitle()) // 즉시로딩
                .author(UserResponseDto.of(findIssue.getAuthor())) // lazy로딩 fetch-join
                .open(findIssue.getOpen()) // 즉시로딩
                .createdDateTime(findIssue.getCreatedDateTime()) // 즉시로딩
                .comments(commentToDto(findIssue.getComments())) // lazy로딩 batch-fetch
                .assignees(AssigneeGraph(findIssue)) // lazy로딩 batch-fetch
                .labels(LabelToDto(findIssue.getLabels())) // lazy로딩 batch-fetch
                .milestone(MilestoneDto.of(findIssue.getMilestone())) // lazy로딩 fetch-join
                .build();
    }

    public void changeOpenStatus(StatusRequest request, String userEmail) {
        User findUser = userService.findUserByEmail(userEmail);

        List<Issue> issueList = issueRepository.findAllById(request.getIssueNumbers());
        issueList.stream()
                .filter(issue -> issue.isSameAuthor(findUser))
                .forEach(issue -> issue.changeOpenStatus(request.getOpen()));
    }

    @Transactional(readOnly = true)
    public IssuesResponse searchIssueByStatus(Boolean status, Pageable pageable) {
        Page<IssueResponse> issues = issueRepository.optimizationFindIssueByStatus(status, pageable)
                .map(i -> new IssueResponse(i.getTitle(), UserResponseDto.of(i.getAuthor()), i.getCreatedDateTime(),
                        AssigneeGraph(i), LabelToDto(i.getLabels()), MilestoneDto.of(i.getMilestone())
                ));

        // TODO : count를 구하는 다른 방법 찾아보기, 너무 매번 쿼리를 날려서 count 하고 있음
        Long totalElementsCount = issueRepository.count();
        Long openCount = issues.getTotalElements();
        Long closedCount = totalElementsCount - openCount;
        Long labelCount = labelService.count();
        Long milestoneCount = milestoneService.count();

        return new IssuesResponse(openCount, closedCount, labelCount, milestoneCount, issues);
    }

    private List<CommentDto> commentToDto(List<Comment> comments) {
        log.info("[commentToDto]");
        return comments.stream()
                .map(comment -> CommentDto.of(comment))
                .collect(Collectors.toList());
    }

    private List<Assignee> AssigneeGraph(Issue issue) {
        log.info("[AssigneeGraph]");
        return issue.getIssueAssignees()
                .stream()
                .map(ia -> ia.getUser()) // 지연로딩 batch-fetch
                .map(u -> Assignee.of(u, true))
                .collect(Collectors.toList());
    }

    private List<LabelDto> LabelToDto(List<Label> labels) {
        log.info("[LabelToDto]");
        return labels.stream()
                .map(LabelDto::new)
                .collect(Collectors.toList());
    }

    public void changeTitle(Long issueId, IssueTitleRequest request, String userEmail) {
        Issue findIssue = issueRepository.optimizationFindById(issueId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ISSUE_NOT_FOUND));

        User findUser = userService.findUserByEmail(userEmail);

        if (!findIssue.isSameAuthor(findUser)) {
            throw new NotAvailableException(ErrorCode.INVALID_USER);
        }

        findIssue.changeTitle(request.getTitle());
    }

    public AssigneesResponse getAssignees(Long issueId) {
        Issue findIssue = issueRepository.optimizationFindById(issueId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ISSUE_NOT_FOUND));

        return new AssigneesResponse(AssigneeGraph(findIssue));
    }
}
