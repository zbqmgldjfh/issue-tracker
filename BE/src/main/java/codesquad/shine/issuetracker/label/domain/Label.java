package codesquad.shine.issuetracker.label.domain;

import codesquad.shine.issuetracker.issue.domain.Issue;
import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "label_id")
    private Long id;

    private String title;
    private String description;

    @ManyToMany(mappedBy = "labels")
    private List<Issue> issues = new ArrayList<>();

    public Label(Long id, String title, String description, Color color) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.color = color;
    }

    @Builder
    public Label(String title, String description, Color color) {
        Assert.hasText(title, "title must not be null and must contain at least one non-whitespace  character");
        Assert.hasText(description, "description must not be null and must contain at least one non-whitespace  character");
        Assert.notNull(color, "color must not be null");
        this.title = title;
        this.description = description;
        this.color = color;
    }

    @Embedded
    private Color color;

    public static Label createEntity(String title, String description, Color color) {
        return new Label(title, description, color);
    }

    public void editProperties(String title, String description, Color color) {
        this.title = title;
        this.description = description;
        this.color = color;
    }

    public void addIssue(Issue issue) {
        issues.add(issue);
    }
}
