package codesquad.shine.issuetracker.acceptance;

import codesquad.shine.issuetracker.label.domain.Color;
import codesquad.shine.issuetracker.label.domain.Label;
import codesquad.shine.issuetracker.label.dto.reqeust.LabelEditRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static codesquad.shine.issuetracker.acceptance.AuthSteps.로그인_되어_있음;
import static codesquad.shine.issuetracker.acceptance.LabelSteps.라벨_삭제_요청;
import static codesquad.shine.issuetracker.acceptance.LabelSteps.라벨_생성_요청;
import static codesquad.shine.issuetracker.acceptance.LabelSteps.라벨_수정_요청;
import static codesquad.shine.issuetracker.acceptance.LabelSteps.라벨_아이디_조회;
import static codesquad.shine.issuetracker.acceptance.LabelSteps.라벨_응답_상태_확인;
import static codesquad.shine.issuetracker.acceptance.LabelSteps.라벨_응답_확인;
import static codesquad.shine.issuetracker.acceptance.LabelSteps.라벨_제목_수정_확인;
import static codesquad.shine.issuetracker.acceptance.LabelSteps.라벨_조회_요청;

public class LabelAcceptanceTest extends AcceptanceTest {

    /**
     * Scenario: Label 스토리 테스트
     * Given 사용자는 로그인 되어있다
     * When 신규 Label을 생성 요청
     * Then 신규 Label이 생성됨
     * When 라벨 조회 요청
     * Then 방금 생성된 라벨이 조회된다
     * When 해당 Label을 편집 요청
     * And Label편집 사항을 반환한다
     * Then 해당 Label이 편집됨을 확인한다
     * When 해당 Label을 삭제 요청
     * Then 해당 Label이 삭제된다
     */
    @DisplayName("Label 통합 인수테스트")
    @Test
    public void label_crud_test() {
        // given
        Label label = new Label("title", "테스트 입니다", new Color("bg", "fg"));
        String accessToken = 로그인_되어_있음(MEMBER_EMAIL, PASSWORD);

        var 라벨_생성_요청_응답 = 라벨_생성_요청(label, accessToken);
        라벨_응답_확인(라벨_생성_요청_응답, HttpStatus.CREATED);

        var 라벨_조회_요청_응답 = 라벨_조회_요청(accessToken);
        String labelId = 라벨_아이디_조회(라벨_조회_요청_응답);

        LabelEditRequest editRequest = new LabelEditRequest("change title", "테스트 입니다", new Color("bg", "fg"));
        var 라벨_수정_요청_응답 = 라벨_수정_요청(labelId, editRequest, accessToken);
        라벨_제목_수정_확인(라벨_수정_요청_응답, "change title");

        var 라벨_삭제_요청_응답 = 라벨_삭제_요청(labelId, accessToken);
        라벨_응답_상태_확인(라벨_삭제_요청_응답, HttpStatus.NO_CONTENT);
    }
}
