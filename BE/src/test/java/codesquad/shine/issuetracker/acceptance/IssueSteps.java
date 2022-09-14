package codesquad.shine.issuetracker.acceptance;

import codesquad.shine.issuetracker.issue.presentation.dto.request.IssueTitleRequest;
import codesquad.shine.issuetracker.issue.presentation.dto.request.StatusRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.AbstractStringAssert;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

public class IssueSteps {

    public static AbstractStringAssert<?> 이슈_마일스톤_확인(ExtractableResponse<Response> 이슈_마일스톤_조회_요청_결과) {
        return assertThat(이슈_마일스톤_조회_요청_결과.jsonPath().getString("milestones[0].title")).isEqualTo("milestone");
    }

    public static void 이슈_라벨_확인(ExtractableResponse<Response> 이슈_라벨_조회_요청_결과) {
        assertThat(이슈_라벨_조회_요청_결과.jsonPath().getString("labels[0].title")).isEqualTo("title");
    }

    public static ExtractableResponse<Response> 이슈_마일스톤_조회_요청(String accessToken, Long issueId) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/issues/{issueId}/milestones", issueId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 이슈_라벨_조회_요청(String accessToken, Long issueId) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/issues/{issueId}/labels", issueId)
                .then().log().all()
                .extract();
    }

    public static void 이슈_할당자_확인(ExtractableResponse<Response> 이슈_할당자_조회_요청_결과) {
        assertThat(이슈_할당자_조회_요청_결과.jsonPath().getString("assignees[0].userName")).isEqualTo("admin");
    }

    public static ExtractableResponse<Response> 이슈_할당자_조회_요청(String accessToken, Long issueId) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/issues/{issueId}/assignees", issueId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 이슈_타이틀_변경_요청(String accessToken, Long issueId, IssueTitleRequest titleRequest) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(titleRequest)
                .when()
                .patch("/api/issues/{issueId}/title", issueId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 이슈_상태_변경_요청(String accessToken, StatusRequest statusRequest, String status) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("status", status)
                .body(statusRequest)
                .when()
                .patch("/api/issues")
                .then().log().all()
                .extract();
    }
}
