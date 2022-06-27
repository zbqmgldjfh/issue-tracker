package codesquad.shine.issuetracker.issue.unit;

import codesquad.shine.issuetracker.common.vo.Assignee;
import codesquad.shine.issuetracker.issue.business.IssueService;
import codesquad.shine.issuetracker.issue.domain.Issue;
import codesquad.shine.issuetracker.issue.domain.IssueRepository;
import codesquad.shine.issuetracker.issue.presentation.dto.request.IssueRequest;
import codesquad.shine.issuetracker.issue.presentation.dto.request.IssueTitleRequest;
import codesquad.shine.issuetracker.issue.presentation.dto.request.StatusRequest;
import codesquad.shine.issuetracker.issue.presentation.dto.response.IssueFormResponse;
import codesquad.shine.issuetracker.label.business.LabelService;
import codesquad.shine.issuetracker.label.business.dto.response.LabelDto;
import codesquad.shine.issuetracker.label.domain.Color;
import codesquad.shine.issuetracker.label.domain.Label;
import codesquad.shine.issuetracker.milestone.business.MilestoneService;
import codesquad.shine.issuetracker.milestone.business.dto.MilestoneDto;
import codesquad.shine.issuetracker.milestone.domain.Milestone;
import codesquad.shine.issuetracker.user.business.UserService;
import codesquad.shine.issuetracker.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
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
        MilestoneDto milestoneDto = MilestoneDto.of(milestone);

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

    @Test
    @DisplayName("Issue Service에 issue 생성 요청시 정상적으로 생성한다.")
    public void create_issue_test() {
        // given
        User user = new User(1L, "shine", "shine@naver.com", "avatarurl");
        Label label = new Label(2L, "label", "this is label", new Color("bg", "font"));
        Milestone milestone = new Milestone(3L, "mile", "this is mile", LocalDate.now(), true);

        IssueRequest request = new IssueRequest("title", "this is comment", null, null, 2L);

        given(userService.findUserByEmail(any())).willReturn(user);
        given(milestoneService.findById(any())).willReturn(milestone);
        given(userService.getAssigneeInId(any())).willReturn(List.of(user));
        given(labelService.getLabelsInId(any())).willReturn(List.of(label));

        // when
        issueService.create(request, "shine@naver.com");

        // then
        verify(userService, times(1)).findUserByEmail(any());
        verify(userService, times(1)).getAssigneeInId(any());
        verify(labelService, times(1)).getLabelsInId(any());
        verify(milestoneService, times(1)).findById(any());
    }

    @Test
    @DisplayName("Issue의 상태를 변경한다.")
    public void change_issue_status_test() {
        // given
        User owner = new User(1L, "shine", "shine@naver.com", "avatar");
        User other = new User(2L, "test", "test@naver.com", "avatar test");

        Issue issue1 = new Issue("title1", true, owner);
        Issue issue2 = new Issue("title2", true, owner);
        Issue issue3 = new Issue("title3", false, owner);
        Issue issue4 = new Issue("title-test-1", true, other);
        Issue issue5 = new Issue("title-test-2", false, other);
        List<Issue> issueList = List.of(issue1, issue2, issue3, issue4, issue5);

        StatusRequest request = new StatusRequest(false, new ArrayList<>());

        given(userService.findUserByEmail(any(String.class))).willReturn(owner);
        given(issueRepository.findAllById(any())).willReturn(issueList);

        // when
        issueService.changeOpenStatus(request, "test@naver.com");

        // then
        then(issueList).extracting("open").containsExactly(false, false, false, true, false);
        then(issueList).extracting("author").containsExactly(owner, owner, owner, other, other);
    }

    @Test
    @DisplayName("Issue의 title을 변경한다.")
    public void change_issue_title_test() {
        // given
        User owner = new User(1L, "shine", "shine@naver.com", "avatar");
        Issue issue = new Issue("title1", true, owner);

        IssueTitleRequest request = new IssueTitleRequest("변경된 title");
        given(issueRepository.optimizationFindById(any())).willReturn(Optional.of(issue));
        given(userService.findUserByEmail(any(String.class))).willReturn(owner);

        // when
        issueService.changeTitle(1L, request, "test@naver.com");

        // then
        then(issue.getTitle()).isEqualTo("변경된 title");
        verify(issueRepository, times(1)).optimizationFindById(any());
        verify(userService, times(1)).findUserByEmail(any());
    }
}
