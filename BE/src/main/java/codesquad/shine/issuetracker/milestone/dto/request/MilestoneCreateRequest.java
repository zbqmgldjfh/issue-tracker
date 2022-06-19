package codesquad.shine.issuetracker.milestone.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MilestoneCreateRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private LocalDate dueDate;
}
