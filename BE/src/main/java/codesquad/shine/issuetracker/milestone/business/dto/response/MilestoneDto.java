package codesquad.shine.issuetracker.milestone.business.dto.response;

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

    public MilestoneDto(Milestone milestone) {
        this.id = milestone.getId();
        this.title = milestone.getTitle();
        this.description = milestone.getDescription();
        this.createdDateTime = milestone.getCreatedDateTime();
        this.dueDate = milestone.getDueDate();
        this.openedIssues = milestone.countOpenedIssues();
        this.closedIssues = milestone.countClosedIssues();
    }
}
