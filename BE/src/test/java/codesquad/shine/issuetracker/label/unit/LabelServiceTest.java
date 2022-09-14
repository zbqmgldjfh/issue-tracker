package codesquad.shine.issuetracker.label.unit;

import codesquad.shine.issuetracker.issue.domain.Issue;
import codesquad.shine.issuetracker.label.business.LabelService;
import codesquad.shine.issuetracker.label.domain.Color;
import codesquad.shine.issuetracker.label.domain.Label;
import codesquad.shine.issuetracker.label.domain.LabelRepository;
import codesquad.shine.issuetracker.label.dto.reqeust.LabelCreateRequest;
import codesquad.shine.issuetracker.label.dto.reqeust.LabelEditRequest;
import codesquad.shine.issuetracker.label.dto.response.LabelEditResponse;
import codesquad.shine.issuetracker.label.dto.response.LabelIdResponse;
import codesquad.shine.issuetracker.label.dto.response.LabelsResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LabelServiceTest {

    @InjectMocks
    private LabelService labelService;

    @Mock
    private LabelRepository labelRepository;

    @Test
    @DisplayName("로그인 한 유저는 label을 생성한 후 id를 검증한다.")
    public void create_label_test_login_success() {
        // given
        LabelCreateRequest request = new LabelCreateRequest("색상1", "test", new Color("bg", "font"));
        Label newLabel = new Label(1L, request.getTitle(), request.getDescription(), request.getColor());

        given(labelRepository.save(Label.createEntity(request.getTitle(), request.getDescription(), request.getColor()))).willReturn(newLabel);

        // when
        LabelIdResponse labelIdResponse = labelService.create(request);

        // then
        then(labelIdResponse.getId()).isEqualTo(1L);
        verify(labelRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("저장된 모든 label을 조회하고 제목을 검증한다.")
    public void search_all_label_test() {
        // given
        Color color = new Color("bg", "font");
        Label label1 = new Label("제목1", "설명1", color);
        Label label2 = new Label("제목2", "설명2", color);
        Label label3 = new Label("제목3", "설명3", color);
        given(labelRepository.findAll()).willReturn(List.of(label1, label2, label3));

        // when
        LabelsResponse labelList = labelService.findALL();

        // then
        then(labelList.getLabels().size()).isEqualTo(3);
        then(labelList.getLabels()).extracting("title").containsExactly("제목1", "제목2", "제목3");

        verify(labelRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("로그인한 유저는 label의 속성을 변경할 수 있다.")
    public void edit_label_test_login_success() {
        // given
        Color color = new Color("bg", "font");
        Label newLabel = new Label(1L, "변경 전", "변경 전", color);

        LabelEditRequest updateRequest = LabelEditRequest.builder()
                .title("변경 후")
                .description("설명도 변경")
                .color(color)
                .build();

        given(labelRepository.findById(any())).willReturn(Optional.ofNullable(newLabel));

        // when
        LabelEditResponse editResponse = labelService.edit(any(), updateRequest);

        // then
        then(editResponse).usingRecursiveComparison().isEqualTo(updateRequest);
        verify(labelRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("로그인한 유저가 label을 삭제하면 정상삭제되고, label을 추가한 issue에서도 삭제된다.")
    public void delete_label_test_login_sucess() {
        // given
        // 라벨 만들기
        Color color = new Color("bg", "font");
        Label newLabel = new Label(1L, "변경 전", "변경 전", color);

        // 이슈를 만들기
        Issue newIssue = Issue.createBasic("issue 이름");

        // 이슈에 라벨 등록
        newIssue.addLabel(newLabel);

        given(labelRepository.findById(any())).willReturn(Optional.of(newLabel));

        // when
        labelService.delete(1L);

        // then
        then(newIssue.getLabels().isEmpty()).isTrue();
        verify(labelRepository, times(1)).findById(any());
    }
}
