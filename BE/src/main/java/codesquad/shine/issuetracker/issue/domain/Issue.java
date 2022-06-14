package codesquad.shine.issuetracker.issue.domain;

import codesquad.shine.issuetracker.comment.domain.Comment;
import codesquad.shine.issuetracker.common.imbeddable.BaseTimeEntity;
import codesquad.shine.issuetracker.milestone.domain.Milestone;
import codesquad.shine.issuetracker.photo.domain.Photo;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @OneToMany(mappedBy = "issue")
    private List<Comment> comments = new ArrayList<>();

    @JoinColumn(name = "milestone_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Milestone milestone;

    @OneToMany(mappedBy = "issue")
    private List<Photo> photos = new ArrayList<>();

    @OneToMany(mappedBy = "issue")
    private List<IssueLabel> issueLabels = new ArrayList<>();
}
