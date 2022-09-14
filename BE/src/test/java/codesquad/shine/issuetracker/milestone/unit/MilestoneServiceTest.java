package codesquad.shine.issuetracker.milestone.unit;

import codesquad.shine.issuetracker.milestone.business.MilestoneService;
import codesquad.shine.issuetracker.milestone.domain.Milestone;
import codesquad.shine.issuetracker.milestone.domain.MilestoneRepository;
import codesquad.shine.issuetracker.milestone.dto.request.MilestoneCreateRequest;
import codesquad.shine.issuetracker.milestone.dto.request.MilestoneEditRequest;
import codesquad.shine.issuetracker.milestone.dto.response.MilestoneEditResponse;
import codesquad.shine.issuetracker.milestone.dto.response.MilestoneIdResponse;
import codesquad.shine.issuetracker.milestone.dto.response.MilestoneListResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MilestoneServiceTest {

    @InjectMocks
    private MilestoneService milestoneService;

    @Mock
    private MilestoneRepository milestoneRepository;

    @Test
    @DisplayName("로그인 한 유저는 milestone을 생성한 후 id를 검증한다.")
    public void create_milestone_test() {
        // given
        MilestoneCreateRequest request = new MilestoneCreateRequest("test milestone", "test!!", LocalDate.now());

        Milestone milestone = new Milestone(1L, "test milestone", "test!!", LocalDate.now(), true);

        given(milestoneRepository.save(any(Milestone.class))).willReturn(milestone);

        // when
        MilestoneIdResponse milestoneIdResponse = milestoneService.create(request);

        // then
        then(milestoneIdResponse.getId()).isNotNull();
        verify(milestoneRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("milestone을 정상 삭제한다.")
    public void delete_milestone_test_login_sucess() {
        // given
        Milestone milestone = new Milestone(1L, "test milestone", "test!!", LocalDate.now(), true);

        given(milestoneRepository.findById(any())).willReturn(Optional.of(milestone));

        // when
        milestoneService.delete(milestone.getId());

        // then
        verify(milestoneRepository, times(1)).delete(any(Milestone.class));
    }

    @Test
    public void edit_milestone_test_login_success() {
        // given
        MilestoneEditRequest updateRequest = new MilestoneEditRequest("편집제목", "설명!!", LocalDate.now());
        Milestone origin = Milestone.builder()
                .title("origin")
                .description("origin!!!")
                .dueDate(LocalDate.now())
                .build();

        given(milestoneRepository.findById(any())).willReturn(Optional.of(origin));

        // when
        MilestoneEditResponse response = milestoneService.edit(1L, updateRequest);

        // then
        then(response).usingRecursiveComparison().isEqualTo(updateRequest);
        verify(milestoneRepository, times(1)).findById(any(Long.class));
    }

    @Test
    public void search_all_milestone_test() {
        // given
        Milestone mile1 = Milestone.builder()
                .title("mile1")
                .description("mile2")
                .dueDate(LocalDate.now())
                .build();

        Milestone mile2 = Milestone.builder()
                .title("mile2")
                .description("mile2")
                .dueDate(LocalDate.now())
                .build();

        given(milestoneRepository.findAll()).willReturn(List.of(mile1, mile2));

        // when
        MilestoneListResponse labelList = milestoneService.findALL();

        // then
        then(labelList.getMilestones().size()).isEqualTo(2);
        then(labelList.getMilestones()).extracting("title").containsExactly("mile1", "mile2");

        verify(milestoneRepository, times(1)).findAll();
    }
}
