package codesquad.shine.issuetracker.acceptance;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AuthAcceptanceTest extends AcceptanceTest {

    public static final String USERNAME_FIELD = "username";
    public static final String PASSWORD_FIELD = "password";

    private static final String EMAIL = "admin@email.com";
    private static final String PASSWORD = "password";
    private static final String NAME = "Shine";

    /**
     * Given 이미 가입된 사용자가 있고
     * And 아직 로그인하지 않은 상태일때
     * When Id 와 Password 로 로그인을 시도하면
     * Then 성공적으로 로그인된다
     */
    @DisplayName("Form 인증방식의 로그인")
    @Test
    public void form_login() {
        // given
        ExtractableResponse<Response> 폼_로그인_응답 = RestAssured
                .given().log().all()
                .auth().form(EMAIL, PASSWORD, new FormAuthConfig("/api/login/form", USERNAME_FIELD, PASSWORD_FIELD))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/auth/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        // when, then
        assertAll(
                () -> assertThat(폼_로그인_응답.jsonPath().getString("id")).isNotNull(),
                () -> assertThat(폼_로그인_응답.jsonPath().getString("email")).isEqualTo(EMAIL),
                () -> assertThat(폼_로그인_응답.jsonPath().getInt("name")).isEqualTo(NAME)
        );
    }
}
