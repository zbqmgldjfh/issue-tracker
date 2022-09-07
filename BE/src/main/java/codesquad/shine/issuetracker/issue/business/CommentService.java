package codesquad.shine.issuetracker.issue.business;

import codesquad.shine.issuetracker.comment.domain.Comment;
import codesquad.shine.issuetracker.comment.domain.CommentRepository;
import codesquad.shine.issuetracker.comment.presentation.dto.response.CommentDto;
import codesquad.shine.issuetracker.exception.ErrorCode;
import codesquad.shine.issuetracker.exception.unchecked.CommentFormatException;
import codesquad.shine.issuetracker.exception.unchecked.NotAvailableException;
import codesquad.shine.issuetracker.exception.unchecked.NotFoundException;
import codesquad.shine.issuetracker.issue.domain.Issue;
import codesquad.shine.issuetracker.issue.domain.IssueRepository;
import codesquad.shine.issuetracker.user.domain.User;
import codesquad.shine.issuetracker.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    public CommentDto add(Long issueId, String comment, String userEmail) {
        Issue findIssue = issueRepository.findById(issueId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ISSUE_NOT_FOUND));

        User findUser = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        if (comment == null || comment.isBlank()) {
            throw new CommentFormatException(ErrorCode.COMMENT_IS_EMPTY);
        }

        Comment newComment = Comment.builder()
                .description(comment)
                .issue(findIssue)
                .user(findUser)
                .build();
        log.info("create new Comment");

        findIssue.addComment(newComment); // 변경감지로 저장?
        Issue savedIssue = issueRepository.save(findIssue);
        return CommentDto.of(savedIssue.getLastComment());
    }

    public void delete(Long issueId, Long commentId, String userEmail) {
        Issue findIssue = issueRepository.findById(issueId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ISSUE_NOT_FOUND));

        User findUser = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.COMMENT_NOT_FOUND));

        if (!findComment.isOwner(findUser)) {
            throw new NotAvailableException(ErrorCode.INVALID_USER);
        }
        findIssue.deleteComment(findComment);
        commentRepository.delete(findComment);
    }

    public void edit(Long issueId, Long commentId, String comment, String userEmail) {
        User findUser = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        Issue findIssue = issueRepository.findById(issueId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ISSUE_NOT_FOUND));

        // app 상에서도 반영되야함
        Comment findComment = findIssue.getComments().stream()
                .filter(i -> i.isSameCommentId(commentId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ErrorCode.COMMENT_NOT_FOUND));

        if (!findComment.isOwner(findUser)) {
            throw new NotAvailableException(ErrorCode.INVALID_USER);
        }

        findComment.edit(comment);
    }
}
