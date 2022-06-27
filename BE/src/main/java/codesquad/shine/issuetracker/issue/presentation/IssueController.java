package codesquad.shine.issuetracker.issue.presentation;

import codesquad.shine.issuetracker.auth.annotation.ForLoginUser;
import codesquad.shine.issuetracker.issue.business.CommentService;
import codesquad.shine.issuetracker.issue.business.IssueService;
import codesquad.shine.issuetracker.issue.presentation.dto.request.*;
import codesquad.shine.issuetracker.issue.presentation.dto.response.AssigneesResponse;
import codesquad.shine.issuetracker.issue.presentation.dto.response.IssueDetailResponse;
import codesquad.shine.issuetracker.issue.presentation.dto.response.IssueFormResponse;
import codesquad.shine.issuetracker.issue.presentation.dto.response.IssuesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/issues")
public class IssueController {

    private final CommentService commentService;
    private final IssueService issueService;

    @ForLoginUser
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{issueId}/comments")
    public void addComment(@PathVariable Long issueId, @RequestBody CommentRequest request, @RequestAttribute String userEmail) {
        commentService.add(issueId, request.getComment(), userEmail);
    }

    @ForLoginUser
    @DeleteMapping("/{issueId}/comments/{commentId}")
    public void deleteComment(@PathVariable Long issueId, @PathVariable Long commentId, @RequestAttribute String userEmail) {
        commentService.delete(issueId, commentId, userEmail);
    }

    @ForLoginUser
    @PatchMapping("/{issueId}/comments/{commentId}")
    public void editComment(
            @PathVariable Long issueId,
            @PathVariable Long commentId,
            @RequestBody CommentRequest request,
            @RequestAttribute String userEmail) {
        commentService.edit(issueId, commentId, request.getComment(), userEmail);
    }

    @GetMapping
    public IssuesResponse filterIssueByStatus(
            @RequestParam(defaultValue = "true") Boolean status,
            @PageableDefault(size = 5, page = 0, sort = "createdDateTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return issueService.searchIssueByStatus(status, pageable);
    }

    @ForLoginUser
    @GetMapping("/form")
    public IssueFormResponse showIssueForm() {
        return issueService.getIssueForm();
    }

    @ForLoginUser
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/form")
    public void createIssue(@RequestBody IssueRequest request, @RequestAttribute String userEmail) {
        issueService.create(request, userEmail);
    }

    @ForLoginUser
    @GetMapping("/{issueId}")
    public IssueDetailResponse showIssueDetail(@PathVariable Long issueId) {
        return issueService.findIssueDetailById(issueId);
    }

    @ForLoginUser
    @PatchMapping
    public void changeOpenStatus(@RequestBody StatusRequest request, @RequestAttribute String userEmail) {
        issueService.changeOpenStatus(request, userEmail);
    }

    @ForLoginUser
    @PatchMapping("/{issueId}/title")
    public void changeTitle(@RequestBody IssueTitleRequest request, @PathVariable Long issueId, @RequestAttribute String userEmail) {
        issueService.changeTitle(issueId, request, userEmail);
    }

    @ForLoginUser
    @GetMapping("/{issueId}/assignees")
    public AssigneesResponse getIssueAssignee(@PathVariable Long issueId) {
        return issueService.getAssignees(issueId);
    }

    @ForLoginUser
    @PatchMapping("/{issueId}/assignees")
    public void editIssueAssignee(@PathVariable Long issueId, @RequestBody AssigneesEditRequest request) {
        issueService.editAssignees(issueId, request);
    }
}
