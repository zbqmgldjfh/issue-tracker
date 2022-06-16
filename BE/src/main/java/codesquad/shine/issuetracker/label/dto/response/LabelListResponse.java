package codesquad.shine.issuetracker.label.dto.response;

import codesquad.shine.issuetracker.label.business.dto.response.LabelDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LabelListResponse {

    private List<LabelDto> labels;
}
