package codesquad.shine.issuetracker.label.dto.reqeust;

import codesquad.shine.issuetracker.label.domain.Color;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LabelEditRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private Color color;
}
