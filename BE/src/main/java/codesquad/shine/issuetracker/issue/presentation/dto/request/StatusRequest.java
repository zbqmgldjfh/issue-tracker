package codesquad.shine.issuetracker.issue.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class StatusRequest {

    private List<Long> issueNumbers;

    public StatusRequest(List<Long> issueNumbers) {
        this.issueNumbers = issueNumbers;
    }
}
