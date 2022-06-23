package codesquad.shine.issuetracker.comment.integration;

import codesquad.shine.issuetracker.comment.domain.Comment;
import codesquad.shine.issuetracker.exception.unchecked.CommentFormatException;
import codesquad.shine.issuetracker.issue.business.CommentService;
import codesquad.shine.issuetracker.issue.domain.Issue;
import codesquad.shine.issuetracker.issue.domain.IssueRepository;
import codesquad.shine.issuetracker.user.domain.User;
import codesquad.shine.issuetracker.user.domain.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
public class CommentServiceIntegrationTest {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Issue에 1글자 이상의 댓글을 정상 등록시킨다.")
    public void addComment_ValidContent_Success() {
        // given
        Issue testIssue = Issue.createBasic("test1");
        issueRepository.save(testIssue);

        User testUser = new User("test user", "zbqmgldjfh@gmail.com", "url");
        userRepository.save(testUser);

        Comment comment = Comment.builder()
                .description("댓글")
                .issue(testIssue)
                .user(testUser)
                .build();

        // when
        commentService.add(testIssue.getId(), comment.getDescription(), testUser.getEmail());

        // then
        List<Comment> comments = testIssue.getComments();
        assertThat(comments.size()).isEqualTo(1);
        assertThat(comments.get(0).getDescription()).isEqualTo("댓글");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    @DisplayName("Issue에 내용이 없는 댓글을 추가할시 예외를 던진다.")
    public void addComment_InvalidContent_ExceptionThrown(String commentValue) {
        // given
        Issue testIssue = Issue.createBasic("test1");
        issueRepository.save(testIssue);

        User testUser = new User("test user", "zbqmgldjfh@gmail.com", "url");
        userRepository.save(testUser);

        // when
        Exception exception = assertThrows(CommentFormatException.class,
                () -> commentService.add(testIssue.getId(), commentValue, testUser.getEmail())
        );

        // then
        assertThat(exception).isInstanceOf(CommentFormatException.class);
    }
}
