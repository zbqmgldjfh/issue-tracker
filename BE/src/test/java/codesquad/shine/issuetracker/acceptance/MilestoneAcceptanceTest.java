package codesquad.shine.issuetracker.acceptance;

import codesquad.shine.issuetracker.milestone.domain.Milestone;
import codesquad.shine.issuetracker.milestone.dto.request.MilestoneCreateRequest;
import codesquad.shine.issuetracker.milestone.dto.request.MilestoneEditRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static codesquad.shine.issuetracker.acceptance.AuthSteps.로그인_되어_있음;
import static org.assertj.core.api.Assertions.assertThat;

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
        Milestone milestone = new Milestone(1L, "milestone", "test입니다", LocalDate.now(), true);
        MilestoneCreateRequest createRequest = new MilestoneCreateRequest("milestone", "테스트 입니다", LocalDate.now());

        String accessToken = 로그인_되어_있음(MEMBER_EMAIL, PASSWORD);
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .body(createRequest)
                .when()
                .post("/api/milestones")
                .then().log().all()
                .extract();
        마일스톤_응답_확인(response, HttpStatus.CREATED);

        ExtractableResponse<Response> 마일스톤_조회_응답 = RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .param("status", "open")
                .when()
                .get("/api/milestones")
                .then().log().all()
                .extract();
        String mileStoneId = 마일스톤_아이디_조회(마일스톤_조회_응답);

        MilestoneEditRequest editRequest = new MilestoneEditRequest("change", "설명입니다.", LocalDate.now());
        ExtractableResponse<Response> 마일스톤_수정_응답 = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .body(editRequest)
                .when()
                .patch("/api/milestones/" + mileStoneId)
                .then().log().all()
                .extract();
        마일스톤_제목_수정_확인(마일스톤_수정_응답, "change");

        ExtractableResponse<Response> 마일스톤_삭제_응답 = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .delete("/api/milestones/" + mileStoneId)
                .then().log().all()
                .extract();
        마일스톤_응답_확인(마일스톤_삭제_응답, HttpStatus.NO_CONTENT);
    }

    public void 마일스톤_응답_확인(ExtractableResponse<Response> response, HttpStatus httpStatus) {
        assertThat(response.statusCode()).isEqualTo(httpStatus.value());
    }

    public String 마일스톤_아이디_조회(ExtractableResponse<Response> 마일스톤_조회_요청_응답) {
        return 마일스톤_조회_요청_응답.jsonPath().getString("milestones[0].id");
    }

    public void 마일스톤_제목_수정_확인(ExtractableResponse<Response> 마일스톤_수정_요청_응답, String editedTitle) {
        assertThat(마일스톤_수정_요청_응답.jsonPath().getString("title")).isEqualTo(editedTitle);
    }
}
