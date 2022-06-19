package codesquad.shine.issuetracker.milestone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
public class MilestoneEditResponse {

    private String title;
    private String description;
    private LocalDate dueDate;
}
