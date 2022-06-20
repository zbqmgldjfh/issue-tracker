package codesquad.shine.issuetracker.issue.presentation;

import codesquad.shine.issuetracker.auth.annotation.ForLoginUser;
import codesquad.shine.issuetracker.issue.business.CommentService;
import codesquad.shine.issuetracker.issue.dto.request.CommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/issues")
public class IssueController {

    private final CommentService commentService;

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

}
