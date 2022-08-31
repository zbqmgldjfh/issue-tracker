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
        ExtractableResponse<Response> 폼_로그인_응답 = RestAssured
                .given().log().all()
                .auth().form(EMAIL, PASSWORD, new FormAuthConfig("/login/form", USERNAME_FIELD, PASSWORD_FIELD))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/users/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        // then
        assertAll(
                () -> assertThat(폼_로그인_응답.jsonPath().getString("id")).isNotNull(),
                () -> assertThat(폼_로그인_응답.jsonPath().getString("email")).isEqualTo(EMAIL),
                () -> assertThat(폼_로그인_응답.jsonPath().getString("name")).isEqualTo(NAME)
        );
    }

    /**
     * Given 이미 가입된 사용자가 있고
     * And 아직 로그인하지 않은 상태일때
     * When Basic방식으로 로 로그인을 시도하면
     * Then 성공적으로 로그인된다
     */
    @DisplayName("Basic 인증방식의 로그인")
    @Test
    public void basic_login() {
        // when
        ExtractableResponse<Response> 베이직_로그인_응답 = RestAssured.given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/users/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        // then
        assertAll(
                () -> assertThat(베이직_로그인_응답.jsonPath().getString("id")).isNotNull(),
                () -> assertThat(베이직_로그인_응답.jsonPath().getString("email")).isEqualTo(EMAIL),
                () -> assertThat(베이직_로그인_응답.jsonPath().getString("name")).isEqualTo(NAME)
        );
    }

    /**
     * Given 이미 가입된 사용자가 있고
     * And 아직 로그인하지 않은 상태일때
     * When Bearer 로 로그인을 시도하면
     * Then 성공적으로 로그인된다
     */
    @DisplayName("Bearer 인증방식의 로그인")
    @Test
    public void bearer_login() {
        // given
        Map<String, String> params = new HashMap<>();
        params.put("email", EMAIL);
        params.put("password", PASSWORD);

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when()
                .post("/login/token")
                .then().log().all()
                .extract();
        String accessToken = response.jsonPath().getString("accessToken");

        // when
        ExtractableResponse<Response> 토큰_로그인_응답 = RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/users/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        // then
        assertAll(
                () -> assertThat(토큰_로그인_응답.jsonPath().getString("id")).isNotNull(),
                () -> assertThat(토큰_로그인_응답.jsonPath().getString("email")).isEqualTo(EMAIL),
                () -> assertThat(토큰_로그인_응답.jsonPath().getString("name")).isEqualTo(NAME)
        );
    }
}
