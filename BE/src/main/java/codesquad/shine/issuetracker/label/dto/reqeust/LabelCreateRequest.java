package codesquad.shine.issuetracker.label.dto.reqeust;

import codesquad.shine.issuetracker.label.domain.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LabelCreateRequest {

    private String title;
    private String description;
    private Color color;

}
