package codesquad.shine.issuetracker.issue.domain;

import codesquad.shine.issuetracker.comment.domain.Comment;
import codesquad.shine.issuetracker.common.imbeddable.BaseTimeEntity;
import codesquad.shine.issuetracker.image.domain.Image;
import codesquad.shine.issuetracker.label.domain.Label;
import codesquad.shine.issuetracker.milestone.domain.Milestone;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Entity
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Issue extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_id")
    private Long id;

    private String title;
    private boolean isOpen;

    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @JoinColumn(name = "milestone_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Milestone milestone;

    @OneToMany(mappedBy = "issue")
    private List<Image> images = new ArrayList<>();

    @ManyToMany
    @JoinColumn(name = "label_id")
    private List<Label> labels = new ArrayList<>();

    private Issue(String title) {
        this.title = title;
    }

    public static Issue createBasic(String title) {
        return new Issue(title);
    }

    public void addLabel(Label newLabel) {
        labels.add(newLabel);
        newLabel.addIssue(this);
    }

    public void detachLabel(Label label) {
        labels.remove(label);
    }

    public boolean isOpen() {
        return isOpen;
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
