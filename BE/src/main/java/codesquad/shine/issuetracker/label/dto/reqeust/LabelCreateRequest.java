package codesquad.shine.issuetracker.label.dto.reqeust;

import codesquad.shine.issuetracker.label.domain.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LabelCreateRequest {

    private final String title;
    private final Color color;
    private final String description;
}
