package codesquad.shine.issuetracker.acceptance;

import codesquad.shine.issuetracker.milestone.dto.request.MilestoneCreateRequest;
import codesquad.shine.issuetracker.milestone.dto.request.MilestoneEditRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class MilestoneSteps {

    public static ExtractableResponse<Response> 마일스톤_삭제_요청(String accessToken, String mileStoneId) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .delete("/api/milestones/" + mileStoneId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 마일스톤_수정_요청(String accessToken, String mileStoneId, MilestoneEditRequest editRequest) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .body(editRequest)
                .when()
                .patch("/api/milestones/" + mileStoneId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 마일스톤_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .param("status", "open")
                .when()
                .get("/api/milestones")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 마일스톤_생성_요청(MilestoneCreateRequest createRequest, String accessToken) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .body(createRequest)
                .when()
                .post("/api/milestones")
                .then().log().all()
                .extract();
    }

    public static Long 마일스톤_응답_확인(ExtractableResponse<Response> response, HttpStatus httpStatus) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(httpStatus.value()),
                () -> assertThat(response.jsonPath().getLong("id")).isNotNull()
        );
        return response.jsonPath().getLong("id");
    }

    public static String 마일스톤_아이디_조회(ExtractableResponse<Response> 마일스톤_조회_요청_응답) {
        return 마일스톤_조회_요청_응답.jsonPath().getString("milestones[0].id");
    }

    public static void 마일스톤_제목_수정_확인(ExtractableResponse<Response> 마일스톤_수정_요청_응답, String editedTitle) {
        assertThat(마일스톤_수정_요청_응답.jsonPath().getString("title")).isEqualTo(editedTitle);
    }
}
