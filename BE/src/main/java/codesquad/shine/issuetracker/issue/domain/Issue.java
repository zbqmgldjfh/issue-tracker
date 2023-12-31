package codesquad.shine.issuetracker.issue.domain;

import codesquad.shine.issuetracker.comment.domain.Comment;
import codesquad.shine.issuetracker.common.imbeddable.BaseTimeEntity;
import codesquad.shine.issuetracker.label.domain.Label;
import codesquad.shine.issuetracker.milestone.domain.Milestone;
import codesquad.shine.issuetracker.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Slf4j
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
        if (comment == null) {
            return;
        }
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
        if (assigneeList == null) {
            return;
        }
        assigneeList.forEach(assignee -> {
            this.issueAssignees.add(new IssueAssignee(this, assignee));
        });
    }

    public void editAssignees(List<User> assigneeList) {
        this.issueAssignees.forEach(IssueAssignee::clearUser);
        this.issueAssignees.clear(); // 우선 초기화 후 선택된 유저만 새롭게 할당
        addAssignees(assigneeList);
    }

    public void addLabels(List<Label> labelList) {
        if (labelList == null) {
            return;
        }
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

    public void changeLabels(List<Label> labels) {
        this.labels = labels;
    }

    public void changeMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public Comment getLastComment() {
        int size = comments.size();
        return comments.get(size - 1);
    }
}
