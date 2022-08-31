package codesquad.shine.issuetracker.acceptance;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AuthAcceptanceTest extends AcceptanceTest {

    public static final String USERNAME_FIELD = "username";
    public static final String PASSWORD_FIELD = "password";

    private static final String EMAIL = "admin@email.com";
    private static final String PASSWORD = "password";
    private static final String NAME = "admin";

    /**
     * Given 이미 가입된 사용자가 있고
     * And 아직 로그인하지 않은 상태일때
     * When Id 와 Password 로 로그인을 시도하면
     * Then 성공적으로 로그인된다
     */
    @DisplayName("Form 인증방식의 로그인")
    @Test
    public void form_login() {
        // when
        var 폼_로그인_응답 = 폼_로그인_후_회원_정보_조회(EMAIL, PASSWORD);

        // then
        회원_정보_조회(폼_로그인_응답, EMAIL, NAME);
    }

    /**
     * Given 이미 가입된 사용자가 있고
     * And 아직 로그인하지 않은 상태일때
     * When Basic 인증으로 로그인을 시도하면
     * Then 성공적으로 로그인된다
     */
    @DisplayName("Basic 인증방식의 로그인")
    @Test
    public void basic_login() {
        // when
        var 베이직_로그인_응답 = 베이직_로그인_후_회원_정보_조회(EMAIL, PASSWORD);

        // then
        회원_정보_조회(베이직_로그인_응답, EMAIL, NAME);
    }

    /**
     * Given 이미 가입된 사용자가 있고
     * And 아직 로그인하지 않은 상태일때
     * When Bearer Token 인증으로 로그인을 시도하면
     * Then 성공적으로 로그인된다
     */
    @DisplayName("Bearer Token 인증방식의 로그인")
    @Test
    public void bearer_token_login() {
        // given
        var 로그인_요청_응답 = 로그인_요청(EMAIL, PASSWORD);
        String accessToken = 로그인_요청_응답.jsonPath().getString("accessToken");

        // when
        ExtractableResponse<Response> 베어러_로그인_응답 = 베어러_인증으로_내_회원_정보_조회_요청(accessToken);

        // then
        회원_정보_조회(베어러_로그인_응답, EMAIL, NAME);
    }

    public static ExtractableResponse<Response> 베어러_인증으로_내_회원_정보_조회_요청(String accessToken) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/users/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }

    private ExtractableResponse<Response> 베이직_로그인_후_회원_정보_조회(String email, String password) {
        return RestAssured
                .given().log().all()
                .auth().preemptive().basic(email, password)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/users/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }

    private ExtractableResponse<Response> 폼_로그인_후_회원_정보_조회(String email, String password) {
        return RestAssured
                .given().log().all()
                .auth().form(email, password, new FormAuthConfig("/login/form", USERNAME_FIELD, PASSWORD_FIELD))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/users/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }

    private void 회원_정보_조회(ExtractableResponse<Response> response, String email, String name) {
        assertAll(
                () -> assertThat(response.jsonPath().getString("id")).isNotNull(),
                () -> assertThat(response.jsonPath().getString("email")).isEqualTo(email),
                () -> assertThat(response.jsonPath().getString("name")).isEqualTo(name)
        );
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
}
