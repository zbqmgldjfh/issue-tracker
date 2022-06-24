package codesquad.shine.issuetracker.milestone.domain;

import codesquad.shine.issuetracker.common.imbeddable.BaseTimeEntity;
import codesquad.shine.issuetracker.issue.domain.Issue;
import codesquad.shine.issuetracker.milestone.dto.request.MilestoneCreateRequest;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@EqualsAndHashCode(of = "id")
public class Milestone extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "milestone_id")
    private Long id;

    private String title;
    private String description;
    private LocalDate dueDate;
    private boolean isOpen;

    @OneToMany(mappedBy = "milestone")
    private List<Issue> issues = new ArrayList<>();

    protected Milestone() {
    }

    public Milestone(Long id, String title, String description, LocalDate dueDate, boolean isOpen) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.isOpen = isOpen;
    }

    @Builder
    public Milestone(String title, String description, LocalDate dueDate, boolean isOpen) {
        Assert.hasText(title, "title must not be null and must contain at least one non-whitespace  character");
        Assert.hasText(description, "description must not be null and must contain at least one non-whitespace");
        Assert.notNull(dueDate, "dueDate must not be null");
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.isOpen = isOpen;
    }

    public static Milestone of(MilestoneCreateRequest request) {
        return Milestone.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .dueDate(request.getDueDate())
                .isOpen(true)
                .build();
    }

    public void editProperties(String title, String description, LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    public Long countOpenedIssues() {
        return issues.stream()
                .filter(Issue::isOpen)
                .count();
    }

    public Long countClosedIssues() {
        return issues.stream()
                .filter(Issue::isClosed)
                .count();
    }
}
