package codesquad.shine.issuetracker.label.dto.reqeust;

import codesquad.shine.issuetracker.label.domain.Color;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@AllArgsConstructor
public class LabelEditRequest {

    @NotBlank
    private final String title;

    @NotBlank
    private final String description;

    @NotNull
    private final Color color;
}
