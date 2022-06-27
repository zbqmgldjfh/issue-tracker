package codesquad.shine.issuetracker.milestone.business.dto;

import codesquad.shine.issuetracker.milestone.domain.Milestone;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MilestoneDto {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdDateTime;
    private LocalDate dueDate;
    private Long openedIssues;
    private Long closedIssues;
    private Boolean checked;

    private MilestoneDto(Milestone milestone, Boolean checked) {
        this.id = milestone.getId();
        this.title = milestone.getTitle();
        this.description = milestone.getDescription();
        this.createdDateTime = milestone.getCreatedDateTime();
        this.dueDate = milestone.getDueDate();
        this.openedIssues = milestone.countOpenedIssues();
        this.closedIssues = milestone.countClosedIssues();
        this.checked = checked;
    }

    public static MilestoneDto of(Milestone milestone) {
        return new MilestoneDto(milestone, true);
    }

    public static MilestoneDto of(Milestone milestone, Boolean open) {
        return new MilestoneDto(milestone, open);
    }
}
