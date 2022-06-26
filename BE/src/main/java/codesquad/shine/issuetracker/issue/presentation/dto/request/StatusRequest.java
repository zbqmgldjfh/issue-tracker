package codesquad.shine.issuetracker.issue.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class StatusRequest {

    private Boolean open;
    private List<Long> issueNumbers;

    public StatusRequest(Boolean open, List<Long> issueNumbers) {
        this.open = open;
        this.issueNumbers = issueNumbers;
    }
}
