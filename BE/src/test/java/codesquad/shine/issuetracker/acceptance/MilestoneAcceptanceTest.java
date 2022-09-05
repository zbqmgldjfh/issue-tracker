package codesquad.shine.issuetracker.acceptance;

import codesquad.shine.issuetracker.milestone.dto.request.MilestoneCreateRequest;
import codesquad.shine.issuetracker.milestone.dto.request.MilestoneEditRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

import static codesquad.shine.issuetracker.acceptance.AuthSteps.로그인_되어_있음;
import static codesquad.shine.issuetracker.acceptance.MilestoneSteps.마일스톤_삭제_요청;
import static codesquad.shine.issuetracker.acceptance.MilestoneSteps.마일스톤_생성_요청;
import static codesquad.shine.issuetracker.acceptance.MilestoneSteps.마일스톤_수정_요청;
import static codesquad.shine.issuetracker.acceptance.MilestoneSteps.마일스톤_아이디_조회;
import static codesquad.shine.issuetracker.acceptance.MilestoneSteps.마일스톤_응답_확인;
import static codesquad.shine.issuetracker.acceptance.MilestoneSteps.마일스톤_제목_수정_확인;
import static codesquad.shine.issuetracker.acceptance.MilestoneSteps.마일스톤_조회_요청;

public class MilestoneAcceptanceTest extends AcceptanceTest {

    /**
     * Scenario: MileStone 스토리 테스트
     * Given 사용자는 로그인 되어있다
     * When 신규 MileStone을 생성 요청
     * Then 신규 MileStone이 생성됨
     * When 모든 MileStone을 조회한다
     * Then 직전에 생성한 MiteStone이 조회된다
     * When 해당 MileStone을 편집 요청
     * Then 해당 MileStone이 편집된다
     * When 해당 MileStone을 삭제 요청
     * Then 해당 MileStone이 삭제된다
     */
    @Test
    public void milestone_crud_test() {
        // given
        MilestoneCreateRequest createRequest = new MilestoneCreateRequest("milestone", "테스트 입니다", LocalDate.now());
        String accessToken = 로그인_되어_있음(MEMBER_EMAIL, PASSWORD);

        var response = 마일스톤_생성_요청(createRequest, accessToken);
        마일스톤_응답_확인(response, HttpStatus.CREATED);

        var 마일스톤_조회_응답 = 마일스톤_조회_요청(accessToken);
        String mileStoneId = 마일스톤_아이디_조회(마일스톤_조회_응답);

        MilestoneEditRequest editRequest = new MilestoneEditRequest("change", "설명입니다.", LocalDate.now());
        var 마일스톤_수정_응답 = 마일스톤_수정_요청(accessToken, mileStoneId, editRequest);
        마일스톤_제목_수정_확인(마일스톤_수정_응답, "change");

        var 마일스톤_삭제_응답 = 마일스톤_삭제_요청(accessToken, mileStoneId);
        마일스톤_응답_확인(마일스톤_삭제_응답, HttpStatus.NO_CONTENT);
    }
}
