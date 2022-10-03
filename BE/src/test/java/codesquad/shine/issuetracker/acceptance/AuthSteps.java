package codesquad.shine.issuetracker.acceptance;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AuthSteps {

    public static final String USERNAME_FIELD = "username";
    public static final String PASSWORD_FIELD = "password";

    public static ExtractableResponse<Response> 베어러_인증으로_내_회원_정보_조회_요청(String accessToken) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/users/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }

    public static ExtractableResponse<Response> 베이직_로그인_후_회원_정보_조회(String email, String password) {
        return RestAssured
                .given().log().all()
                .auth().preemptive().basic(email, password)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/users/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }

    public static ExtractableResponse<Response> 폼_로그인_후_회원_정보_조회(String email, String password) {
        return RestAssured
                .given().log().all()
                .auth().form(email, password, new FormAuthConfig("/login/form", USERNAME_FIELD, PASSWORD_FIELD))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/users/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }


    public static ExtractableResponse<Response> 로그인_요청(String email, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract();
    }

    public static String 로그인_되어_있음(String email, String password) {
        ExtractableResponse<Response> response = 로그인_요청(email, password);
        return response.jsonPath().getString("accessToken");
    }

    public static void access_token_재발급_요청_확인(String accessToken, ExtractableResponse<Response> refreshResponse) {
        String newAccessToken = refreshResponse.jsonPath().getString("accessToken");
        assertThat(newAccessToken).isNotNull();
        assertThat(newAccessToken).isNotEqualTo(accessToken);
    }

    public static ExtractableResponse<Response> access_token_재발급_요청(String refreshToken) {
        Map<String, String> params = new HashMap<>();
        params.put("refreshToken", refreshToken);

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post("/login/reissue")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }


    public static void 회원_정보_조회(ExtractableResponse<Response> response, String email, String name) {
        assertAll(
                () -> assertThat(response.jsonPath().getString("id")).isNotNull(),
                () -> assertThat(response.jsonPath().getString("email")).isEqualTo(email),
                () -> assertThat(response.jsonPath().getString("name")).isEqualTo(name)
        );
    }
}
