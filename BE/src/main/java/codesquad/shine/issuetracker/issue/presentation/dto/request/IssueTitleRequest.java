package codesquad.shine.issuetracker.issue.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueTitleRequest {
    private String title;

    public IssueTitleRequest(String title) {
        this.title = title;
    }
}
