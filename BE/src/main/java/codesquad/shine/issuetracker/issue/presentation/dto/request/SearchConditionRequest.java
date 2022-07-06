package codesquad.shine.issuetracker.issue.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchConditionRequest {

    private String query;
    private Boolean open;
    private Boolean close;
    private Boolean owner;
    private Boolean assignee;
    private Boolean comment;
}
