package codesquad.shine.issuetracker.issue.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class LabelsCheckRequest {

    private List<Long> labelIds;

    public LabelsCheckRequest(List<Long> labelIds) {
        this.labelIds = labelIds;
    }
}
