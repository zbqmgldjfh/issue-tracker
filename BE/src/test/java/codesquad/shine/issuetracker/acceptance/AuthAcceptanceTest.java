package codesquad.shine.issuetracker.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

import static codesquad.shine.issuetracker.acceptance.AuthSteps.로그인_되어_있음;
import static codesquad.shine.issuetracker.acceptance.AuthSteps.로그인_요청;
import static codesquad.shine.issuetracker.acceptance.AuthSteps.베어러_인증으로_내_회원_정보_조회_요청;
import static codesquad.shine.issuetracker.acceptance.AuthSteps.베이직_로그인_후_회원_정보_조회;
import static codesquad.shine.issuetracker.acceptance.AuthSteps.폼_로그인_후_회원_정보_조회;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AuthAcceptanceTest extends AcceptanceTest {

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
        var 폼_로그인_응답 = 폼_로그인_후_회원_정보_조회(ADMIN_EMAIL, PASSWORD);

        // then
        회원_정보_조회(폼_로그인_응답, ADMIN_EMAIL, NAME);
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
        var 베이직_로그인_응답 = 베이직_로그인_후_회원_정보_조회(ADMIN_EMAIL, PASSWORD);

        // then
        회원_정보_조회(베이직_로그인_응답, ADMIN_EMAIL, NAME);
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
        String accessToken = 로그인_되어_있음(ADMIN_EMAIL, PASSWORD);

        // when
        ExtractableResponse<Response> 베어러_로그인_응답 = 베어러_인증으로_내_회원_정보_조회_요청(accessToken);

        // then
        회원_정보_조회(베어러_로그인_응답, ADMIN_EMAIL, NAME);
    }

    /**
     * Scenario: AccessToken이 만료된 경우 RefreshToken을 통해 재발급 받을 수 있다.
     * Given 사용자가 ROLE_MEMBER로 로그인되어 있다
     * When RefreshToken을 통해 Bearer Token 인증으로 로그인을 시도하면
     * Then 신규 AccessToken이 발급된다
     */
    @DisplayName("Refresh Token으로 요청할 경우 새로운 AccessToken을 반환한다.")
    @Test
    public void bearer_token_login_with_refresh_token() throws InterruptedException {
        // given
        ExtractableResponse<Response> response = 로그인_요청(ADMIN_EMAIL, PASSWORD);
        String accessToken = response.jsonPath().getString("accessToken");
        String refreshToken = response.jsonPath().getString("refreshToken");

        Thread.sleep(500); // 0.5초 정도 시간이 지난 후

        // when
        Map<String, String> params = new HashMap<>();
        params.put("refreshToken", refreshToken);

        ExtractableResponse<Response> refreshResponse = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post("/login/reissue")
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract();

        // then
        String newAccessToken = refreshResponse.jsonPath().getString("accessToken");

        assertThat(newAccessToken).isNotNull();
        assertThat(newAccessToken).isNotEqualTo(accessToken);
    }


    private void 회원_정보_조회(ExtractableResponse<Response> response, String email, String name) {
        assertAll(
                () -> assertThat(response.jsonPath().getString("id")).isNotNull(),
                () -> assertThat(response.jsonPath().getString("email")).isEqualTo(email),
                () -> assertThat(response.jsonPath().getString("name")).isEqualTo(name)
        );
    }
}
