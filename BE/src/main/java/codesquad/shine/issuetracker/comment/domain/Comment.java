package codesquad.shine.issuetracker.comment.domain;

import codesquad.shine.issuetracker.common.imbeddable.BaseTimeEntity;
import codesquad.shine.issuetracker.issue.domain.Issue;
import codesquad.shine.issuetracker.user.domain.User;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Entity
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
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

    public Comment(String description, Issue issue, User user) {
        this.description = description;
        this.issue = issue;
        this.user = user;
    }

    public static Comment create(String comment, Issue issue, User user) {
        return new Comment(comment, issue, user);
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
        return this.id == commentId;
    }

    public void edit(String comment) {
        this.description = comment;
    }
}
