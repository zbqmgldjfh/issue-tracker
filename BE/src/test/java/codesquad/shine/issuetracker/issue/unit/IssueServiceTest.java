package codesquad.shine.issuetracker.issue.unit;

import codesquad.shine.issuetracker.common.vo.Assignee;
import codesquad.shine.issuetracker.issue.business.IssueService;
import codesquad.shine.issuetracker.issue.domain.IssueRepository;
import codesquad.shine.issuetracker.issue.presentation.dto.response.IssueFormResponse;
import codesquad.shine.issuetracker.label.business.LabelService;
import codesquad.shine.issuetracker.label.business.dto.response.LabelDto;
import codesquad.shine.issuetracker.label.domain.Color;
import codesquad.shine.issuetracker.label.domain.Label;
import codesquad.shine.issuetracker.milestone.business.MilestoneService;
import codesquad.shine.issuetracker.milestone.business.dto.MilestoneDto;
import codesquad.shine.issuetracker.milestone.domain.Milestone;
import codesquad.shine.issuetracker.user.business.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class IssueServiceTest {

    @InjectMocks
    private IssueService issueService;

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private UserService userService;

    @Mock
    private LabelService labelService;

    @Mock
    private MilestoneService milestoneService;

    @Test
    @DisplayName("이슈 생성 요청시 Issue 폼을 정상 반환한다.")
    public void get_issue_form_test() {
        // given
        Assignee assignee = new Assignee(1L, "shine", "testUrl", false);

        // 라벨 만들기
        Color color = new Color("bg", "font");
        Label newLabel = new Label(1L, "변경 전", "변경 전", color);
        LabelDto labelDto = new LabelDto(newLabel);

        // 마일스톤 만들기
        Milestone milestone = new Milestone(1L, "test milestone", "test!!", LocalDate.now(), true);
        MilestoneDto milestoneDto = new MilestoneDto(milestone);

        given(userService.getAllAssignee()).willReturn(List.of(assignee));
        given(labelService.findAllDto()).willReturn(List.of(labelDto));
        given(milestoneService.findAllDto()).willReturn(List.of(milestoneDto));

        // when
        IssueFormResponse issueForm = issueService.getIssueForm();

        // then
        then(issueForm.getAssignees().get(0)).isEqualTo(assignee);
        then(issueForm.getLabels().get(0)).isEqualTo(labelDto);
        then(issueForm.getMilestones().get(0)).isEqualTo(milestoneDto);

        verify(userService, times(1)).getAllAssignee();
        verify(labelService, times(1)).findAllDto();
        verify(milestoneService, times(1)).findAllDto();

    }
}