package codesquad.shine.issuetracker.comment.domain;

import codesquad.shine.issuetracker.common.imbeddable.BaseTimeEntity;
import codesquad.shine.issuetracker.issue.domain.Issue;
import codesquad.shine.issuetracker.user.domain.User;
import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.*;

@Getter
@Entity
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String description;

    @ManyToOne
    @JoinColumn(name = "issue_id")
    private Issue issue;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(Long id, String description, Issue issue, User user) {
        Assert.notNull(id, "id must not be null");
        Assert.notNull(description, "description must not be null");
        Assert.notNull(issue, "issue must not be null");
        Assert.notNull(user, "user must not be null");

        this.id = id;
        this.description = description;
        this.issue = issue;
        this.user = user;
    }

    @Builder
    public Comment(String description, Issue issue, User user) {
        Assert.notNull(description, "description must not be null");
        Assert.notNull(issue, "issue must not be null");
        Assert.notNull(user, "user must not be null");
        this.description = description;
        this.issue = issue;
        this.user = user;
    }

    public void addIssue(Issue issue) {
        this.issue = issue;
    }

    public boolean isOwner(User findUser) {
        return user.equals(findUser);
    }

    public void deleteIssue() {
        this.issue = null;
    }

    public boolean isSameCommentId(Long commentId) {
        return this.id.equals(commentId);
    }

    public void edit(String comment) {
        this.description = comment;
    }
}
