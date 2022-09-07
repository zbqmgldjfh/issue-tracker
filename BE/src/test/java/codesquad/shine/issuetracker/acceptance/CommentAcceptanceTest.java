package codesquad.shine.issuetracker.acceptance;

import codesquad.shine.issuetracker.issue.presentation.dto.request.CommentRequest;
import codesquad.shine.issuetracker.issue.presentation.dto.request.IssueRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static codesquad.shine.issuetracker.acceptance.AuthSteps.로그인_되어_있음;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CommentAcceptanceTest extends AcceptanceTest {

    /**
     * Scenario: Comment 스토리 테스트
     * Given 사용자는 로그인 되어있다
     * When 신규 Issue를 생성 요청
     * Then 신규 Issue가 생성됨
     * When 생성된 Issue에 Comment를 생성 요청
     * Then 신규 Comment가 생성됨
     * When 해당 Issue의 Comment를 편집 요청
     * Then 해당 Comment가 편집된다
     * When 해당 Issue의 Comment를 삭제 요청
     * Then 해당 Comment가 삭제된다
     */
    @Test
    public void comment_crud_test() {
        String accessToken = 로그인_되어_있음(MEMBER_EMAIL, PASSWORD);

        IssueRequest issueRequest = new IssueRequest("new issue", "", null, null, null);
        ExtractableResponse<Response> 이슈_생성_요청_결과 = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(issueRequest)
                .when()
                .post("/api/issues/form")
                .then().log().all()
                .extract();
        Long issueId = 이슈_응답_확인(이슈_생성_요청_결과, HttpStatus.CREATED);

        CommentRequest commentRequest = new CommentRequest("this is comment!");
        ExtractableResponse<Response> 이슈에_댓글_추가_요청_결과 = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(commentRequest)
                .when()
                .post("/api/issues/" + issueId + "/comments")
                .then().log().all()
                .extract();
        Long commentId = 이슈_응답_확인(이슈에_댓글_추가_요청_결과, HttpStatus.CREATED);

        CommentRequest commentEditRequest = new CommentRequest("edit comment!");
        ExtractableResponse<Response> 이슈에_댓글_편집_요청_결과 = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(commentEditRequest)
                .when()
                .patch("/api/issues/" + issueId + "/comments/" + commentId)
                .then().log().all()
                .extract();
        이슈_응답_상태_확인(이슈에_댓글_편집_요청_결과, HttpStatus.OK);

        ExtractableResponse<Response> 이슈에_댓글_삭제_요청_결과 = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .delete("/api/issues/" + issueId + "/comments/" + commentId)
                .then().log().all()
                .extract();
        이슈_응답_상태_확인(이슈에_댓글_삭제_요청_결과, HttpStatus.NO_CONTENT);
    }

    public void 이슈_응답_상태_확인(ExtractableResponse<Response> response, HttpStatus httpStatus) {
        assertThat(response.statusCode()).isEqualTo(httpStatus.value());
    }

    public Long 이슈_응답_확인(ExtractableResponse<Response> response, HttpStatus httpStatus) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(httpStatus.value()),
                () -> assertThat(response.jsonPath().getLong("id")).isNotNull()
        );
        return response.jsonPath().getLong("id");
    }
}
