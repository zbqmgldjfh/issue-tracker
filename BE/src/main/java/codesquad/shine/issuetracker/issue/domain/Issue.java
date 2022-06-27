package codesquad.shine.issuetracker.issue.domain;

import codesquad.shine.issuetracker.comment.domain.Comment;
import codesquad.shine.issuetracker.common.imbeddable.BaseTimeEntity;
import codesquad.shine.issuetracker.label.domain.Label;
import codesquad.shine.issuetracker.milestone.domain.Milestone;
import codesquad.shine.issuetracker.user.domain.User;
import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@Entity
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Issue extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_id")
    private Long id;

    private String title;

    private Boolean open;

    @JoinColumn
    @ManyToOne(fetch = LAZY)
    private User author;

    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL)
    private List<IssueAssignee> issueAssignees = new ArrayList<>();

    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @JoinColumn(name = "milestone_id")
    @ManyToOne(fetch = LAZY)
    private Milestone milestone;

    @ManyToMany
    @JoinColumn(name = "label_id")
    private List<Label> labels = new ArrayList<>();

    public static Issue createBasic(String title) {
        return Issue.builder()
                .title(title)
                .open(true)
                .milestone(null)
                .build();
    }

    public Issue(String title, Boolean open, User author) {
        this.title = title;
        this.open = open;
        this.author = author;
    }

    @Builder
    public Issue(String title, boolean open, User author, Milestone milestone) {
        Assert.hasText(title, "title must not be null");
        this.title = title;
        this.open = open;
        this.author = author;
        this.milestone = milestone;
    }

    public void addLabel(Label newLabel) {
        labels.add(newLabel);
        newLabel.addIssue(this);
    }

    public void detachLabel(Label label) {
        labels.remove(label);
    }

    public boolean isOpen() {
        return open;
    }

    public boolean isClosed() {
        return !isOpen();
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.addIssue(this);
    }

    public void deleteComment(Comment comment) {
        this.comments.remove(comment);
        comment.deleteIssue();
    }

    public void addMilestone(Milestone milestone) {
        if (milestone == null) {
            return;
        }
        this.milestone = milestone;
        milestone.getIssues().add(this);
    }

    public void addAssignees(List<User> assigneeList) {
        assigneeList.forEach(assignee -> {
            this.issueAssignees.add(new IssueAssignee(this, assignee));
        });
    }

    public void addLabels(List<Label> labelList) {
        labelList.forEach(label -> addLabel(label));
    }

    public boolean isSameAuthor(User user) {
        return this.author.equals(user);
    }

    public void changeOpenStatus(Boolean open) {
        this.open = open;
    }

    public void changeTitle(String title) {
        this.title = title;
    }
}
