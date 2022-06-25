package codesquad.shine.issuetracker.issue.domain;

import codesquad.shine.issuetracker.user.domain.User;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IssueAssignee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_assignee_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id")
    private Issue issue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public IssueAssignee(Issue issue, User user) {
        this.issue = issue;
        this.user = user;
    }
}
