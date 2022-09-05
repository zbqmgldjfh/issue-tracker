package codesquad.shine.issuetracker.acceptance;

import codesquad.shine.issuetracker.label.domain.Label;
import codesquad.shine.issuetracker.label.dto.reqeust.LabelEditRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

public class LabelSteps {

    public static void 라벨_응답_확인(ExtractableResponse<Response> response, HttpStatus httpStatus) {
        assertThat(response.statusCode()).isEqualTo(httpStatus.value());
    }

    public static ExtractableResponse<Response> 라벨_생성_요청(Label label, String accessToken) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .body(label)
                .when()
                .post("/api/labels")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 라벨_삭제_요청(String labelId, String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .delete("/api/labels/" + labelId)
                .then().log().all()
                .extract();
    }

    public static void 라벨_제목_수정_확인(ExtractableResponse<Response> 라벨_수정_요청_응답, String editedTitle) {
        assertThat(라벨_수정_요청_응답.jsonPath().getString("title")).isEqualTo(editedTitle);
    }

    public static String 라벨_아이디_조회(ExtractableResponse<Response> 라벨_조회_요청_응답) {
        return 라벨_조회_요청_응답.jsonPath().getString("labels[0].id");
    }

    public static ExtractableResponse<Response> 라벨_수정_요청(String labelId, LabelEditRequest editRequest, String accessToken) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .body(editRequest)
                .when()
                .patch("/api/labels/" + labelId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 라벨_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .when()
                .get("/api/labels")
                .then().log().all()
                .extract();
    }
}
