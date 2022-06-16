package codesquad.shine.issuetracker.label.dto.response;

import codesquad.shine.issuetracker.label.domain.Color;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class LabelEditResponse {

    private final String title;
    private final String description;
    private final Color color;
}
