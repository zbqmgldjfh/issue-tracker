package codesquad.shine.issuetracker.acceptance;

import codesquad.shine.issuetracker.label.domain.Label;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

public class LabelSteps {

    public static void 라벨_생성_응답_확인(ExtractableResponse<Response> response, HttpStatus httpStatus) {
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
}
