package codesquad.shine.issuetracker.issue.presentation;

import codesquad.shine.issuetracker.comment.presentation.dto.response.CommentDto;
import codesquad.shine.issuetracker.common.vo.Status;
import codesquad.shine.issuetracker.issue.business.CommentService;
import codesquad.shine.issuetracker.issue.business.IssueService;
import codesquad.shine.issuetracker.issue.presentation.dto.request.AssigneesEditRequest;
import codesquad.shine.issuetracker.issue.presentation.dto.request.CommentRequest;
import codesquad.shine.issuetracker.issue.presentation.dto.request.IssueRequest;
import codesquad.shine.issuetracker.issue.presentation.dto.request.IssueTitleRequest;
import codesquad.shine.issuetracker.issue.presentation.dto.request.LabelsCheckRequest;
import codesquad.shine.issuetracker.issue.presentation.dto.request.MilestoneCheckRequest;
import codesquad.shine.issuetracker.issue.presentation.dto.request.SearchConditionRequest;
import codesquad.shine.issuetracker.issue.presentation.dto.request.StatusRequest;
import codesquad.shine.issuetracker.issue.presentation.dto.response.AssigneesResponse;
import codesquad.shine.issuetracker.issue.presentation.dto.response.IssueDetailResponse;
import codesquad.shine.issuetracker.issue.presentation.dto.response.IssueFormResponse;
import codesquad.shine.issuetracker.issue.presentation.dto.response.IssueIdResponse;
import codesquad.shine.issuetracker.issue.presentation.dto.response.IssuesResponse;
import codesquad.shine.issuetracker.label.dto.response.LabelsResponse;
import codesquad.shine.issuetracker.milestone.dto.response.MilestoneListResponse;
import codesquad.shine.support.auth.authorization.AuthenticationPrincipal;
import codesquad.shine.support.auth.authorization.secured.Secured;
import codesquad.shine.support.auth.userdetails.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/issues")
public class IssueController {

    private final CommentService commentService;
    private final IssueService issueService;

    @Secured("ROLE_MEMBER")
    @GetMapping("/search")
    public IssuesResponse search(
            SearchConditionRequest condition,
            @PageableDefault(size = 5, page = 0, sort = "createdDateTime", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestAttribute String userEmail
    ) {
        return issueService.search(userEmail, condition, pageable);
    }

    @Secured("ROLE_MEMBER")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{issueId}/comments")
    public CommentDto addComment(@PathVariable Long issueId, @RequestBody CommentRequest request, @AuthenticationPrincipal AuthUser user) {
        return commentService.add(issueId, request.getComment(), user.getUsername().toString());
    }

    @Secured("ROLE_MEMBER")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{issueId}/comments/{commentId}")
    public void deleteComment(@PathVariable Long issueId, @PathVariable Long commentId, @AuthenticationPrincipal AuthUser user) {
        commentService.delete(issueId, commentId, user.getUsername().toString());
    }

    @Secured("ROLE_MEMBER")
    @PatchMapping("/{issueId}/comments/{commentId}")
    public void editComment(
            @PathVariable Long issueId,
            @PathVariable Long commentId,
            @RequestBody CommentRequest request,
            @AuthenticationPrincipal AuthUser user) {
        commentService.edit(issueId, commentId, request.getComment(), user.getUsername().toString());
    }

    @GetMapping
    public IssuesResponse filterIssueByStatus(
            @RequestParam(defaultValue = "true") Boolean status,
            @PageableDefault(size = 5, page = 0, sort = "createdDateTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return issueService.searchIssueByStatus(status, pageable);
    }

    @Secured("ROLE_MEMBER")
    @GetMapping("/form")
    public IssueFormResponse showIssueForm() {
        return issueService.getIssueForm();
    }

    @Secured("ROLE_MEMBER")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/form")
    public IssueIdResponse createIssue(@RequestBody IssueRequest request, @AuthenticationPrincipal AuthUser user) {
        return issueService.create(request, user.getUsername().toString());
    }

    @Secured("ROLE_MEMBER")
    @GetMapping("/{issueId}")
    public IssueDetailResponse showIssueDetail(@PathVariable Long issueId) {
        return issueService.findIssueDetailById(issueId);
    }

    @Secured("ROLE_MEMBER")
    @PatchMapping
    public void changeOpenStatus(@RequestBody StatusRequest request, @RequestParam Status status, @AuthenticationPrincipal AuthUser user) {
        issueService.changeOpenStatus(request, status, user.getUsername().toString());
    }

    @Secured("ROLE_MEMBER")
    @PatchMapping("/{issueId}/title")
    public void changeTitle(@RequestBody IssueTitleRequest request, @PathVariable Long issueId, @AuthenticationPrincipal AuthUser user) {
        issueService.changeTitle(issueId, request, user.getUsername().toString());
    }

    @Secured("ROLE_MEMBER")
    @GetMapping("/{issueId}/assignees")
    public AssigneesResponse getIssueAssignee(@PathVariable Long issueId) {
        return issueService.getAssignees(issueId);
    }

    @Secured("ROLE_MEMBER")
    @PatchMapping("/{issueId}/assignees")
    public void editIssueAssignee(@PathVariable Long issueId, @RequestBody AssigneesEditRequest request) {
        issueService.editAssignees(issueId, request);
    }

    @Secured("ROLE_MEMBER")
    @GetMapping("/{issueId}/labels")
    public LabelsResponse getLabels(@PathVariable Long issueId) {
        return issueService.getLabelsByIssueId(issueId);
    }

    @Secured("ROLE_MEMBER")
    @PatchMapping("/{issueId}/labels")
    public void editLabels(@PathVariable Long issueId, @RequestBody LabelsCheckRequest request) {
        issueService.editLabelsByIssueId(issueId, request);
    }

    @Secured("ROLE_MEMBER")
    @GetMapping("/{issueId}/milestones")
    public MilestoneListResponse getMilestoneList(@PathVariable Long issueId) {
        return issueService.getMilestonesByIssueId(issueId);
    }

    @Secured("ROLE_MEMBER")
    @PatchMapping("/{issueId}/milestones")
    public void editMilestone(@PathVariable Long issueId, @RequestBody MilestoneCheckRequest request) {
        issueService.editMilestoneByIssueId(issueId, request);
    }
}
