package codesquad.shine.issuetracker.acceptance;

import codesquad.shine.issuetracker.issue.presentation.dto.request.CommentRequest;
import codesquad.shine.issuetracker.issue.presentation.dto.request.IssueRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CommentSteps {

    public static ExtractableResponse<Response> 이슈에_댓글_삭제_요청(String accessToken, Long issueId, Long commentId) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .delete("/api/issues/" + issueId + "/comments/" + commentId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 이슈에_댓글_편집_요청(String accessToken, Long issueId, Long commentId, CommentRequest commentEditRequest) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(commentEditRequest)
                .when()
                .patch("/api/issues/" + issueId + "/comments/" + commentId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 이슈에_댓글_추가_요청(String accessToken, Long issueId, CommentRequest commentRequest) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(commentRequest)
                .when()
                .post("/api/issues/" + issueId + "/comments")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 이슈_생성_요청(String accessToken, IssueRequest issueRequest) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(issueRequest)
                .when()
                .post("/api/issues/form")
                .then().log().all()
                .extract();
    }

    public static void 이슈_응답_상태_확인(ExtractableResponse<Response> response, HttpStatus httpStatus) {
        assertThat(response.statusCode()).isEqualTo(httpStatus.value());
    }

    public static Long 이슈_응답_확인(ExtractableResponse<Response> response, HttpStatus httpStatus) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(httpStatus.value()),
                () -> assertThat(response.jsonPath().getLong("id")).isNotNull()
        );
        return response.jsonPath().getLong("id");
    }
}
