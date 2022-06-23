package codesquad.shine.issuetracker.comment.unit;

import codesquad.shine.issuetracker.comment.domain.Comment;
import codesquad.shine.issuetracker.comment.domain.CommentRepository;
import codesquad.shine.issuetracker.issue.business.CommentService;
import codesquad.shine.issuetracker.issue.domain.Issue;
import codesquad.shine.issuetracker.issue.domain.IssueRepository;
import codesquad.shine.issuetracker.issue.dto.request.CommentRequest;
import codesquad.shine.issuetracker.user.domain.User;
import codesquad.shine.issuetracker.user.domain.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private IssueRepository issueRepository;

    @Test
    @DisplayName("issue에 comment를 추가하먄 저장되고 id값을 반환한다")
    public void create_comment_test() {
        // given
        Issue test1 = Issue.createBasic("test1");
        CommentRequest commentRequest = new CommentRequest("test내용 입니다!");
        User newUser = new User("test user", "zbqmgldjfh@gmail.com", "url");

        given(issueRepository.findById(any(Long.class))).willReturn(Optional.of(test1));
        given(userRepository.findUserByEmail(any(String.class))).willReturn(Optional.of(newUser));

        // when
        commentService.add(1L, commentRequest.getComment(), "testUser@naver.com");
        List<Comment> comments = test1.getComments();

        // then
        then(comments.size()).isEqualTo(1);
        verify(issueRepository, times(1)).findById(any());
        verify(userRepository, times(1)).findUserByEmail(any());
    }

    @Test
    @DisplayName("issueId를 통해 issue를 삭제하면 정상 삭제된다.")
    public void delete_comment_test() {
        // given
        Issue testIssue = Issue.createBasic("test1");
        User newUser = new User("test user", "zbqmgldjfh@gmail.com", "url");
        Comment comment = Comment.builder()
                .description("댓글")
                .issue(testIssue)
                .user(newUser)
                .build();

        testIssue.addComment(comment);

        given(issueRepository.findById(any(Long.class))).willReturn(Optional.of(testIssue));
        given(userRepository.findUserByEmail(any(String.class))).willReturn(Optional.of(newUser));
        given(commentRepository.findById(any(Long.class))).willReturn(Optional.of(comment));

        // when
        commentService.delete(1L, 2L, "testUser@naver.com");

        // then
        then(testIssue.getComments().size()).isEqualTo(0);
        verify(commentRepository, times(1)).delete(any());
        verify(issueRepository, times(1)).findById(any());
        verify(userRepository, times(1)).findUserByEmail(any());
    }

    @Test
    @DisplayName("issueId를 통해 issue를 편집하면 정상 수정 된다.")
    public void edit_comment_test() {
        // given
        Issue testIssue = Issue.createBasic("test1");
        User newUser = new User("test user", "testUser@naver.com", "url");
        Comment comment = new Comment(2L, "댓글", testIssue, newUser);

        testIssue.addComment(comment);

        given(userRepository.findUserByEmail(any(String.class))).willReturn(Optional.of(newUser));
        given(issueRepository.findById(any(Long.class))).willReturn(Optional.of(testIssue));

        // when
        commentService.edit(1L, 2L, "변경", "testUser@naver.com");

        // then
        then(comment.getDescription()).isEqualTo("변경");
        verify(issueRepository, times(1)).findById(any());
        verify(userRepository, times(1)).findUserByEmail(any());
    }
}
