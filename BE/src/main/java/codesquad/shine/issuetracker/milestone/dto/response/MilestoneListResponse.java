package codesquad.shine.issuetracker.milestone.dto.response;

import codesquad.shine.issuetracker.milestone.business.dto.MilestoneDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MilestoneListResponse {
    private List<MilestoneDto> Milestones;
}
