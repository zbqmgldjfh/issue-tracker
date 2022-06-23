package codesquad.shine.issuetracker.issue.domain;

import codesquad.shine.issuetracker.comment.domain.Comment;
import codesquad.shine.issuetracker.common.imbeddable.BaseTimeEntity;
import codesquad.shine.issuetracker.label.domain.Label;
import codesquad.shine.issuetracker.milestone.domain.Milestone;
import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @JoinColumn(name = "milestone_id")
    @ManyToOne(fetch = FetchType.LAZY)
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

    @Builder
    public Issue(String title, boolean open, Milestone milestone) {
        Assert.hasText(title, "title must not be null");
        this.title = title;
        this.open = open;
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
}
