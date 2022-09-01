package codesquad.shine.issuetracker.acceptance;

import codesquad.shine.issuetracker.label.domain.Color;
import codesquad.shine.issuetracker.label.domain.Label;
import codesquad.shine.support.auth.context.SecurityContextHolder;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.json.JSONException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static codesquad.shine.issuetracker.acceptance.AuthSteps.로그인_되어_있음;
import static org.assertj.core.api.Assertions.assertThat;

public class SecuredAcceptanceTest extends AcceptanceTest {

    /**
     * Scenario: 로그인 한 사용자중 Member등급만 Label을 만들 수 있다.
     * Given 사용자가 ROLE_MEMBER로 로그인되어 있다
     * When ROLE_MEMBER 등급으로 Label 생성 요청시
     * Then 신규 Label이 생성된다
     */
    @DisplayName("로그인에 성공한 ROLE_MEMBER 등급의 사람만 라벨을 생성한다")
    @Test
    public void authorized_success_member_create_label_test() {
        // given
        Label label = new Label("title", "테스트 입니다", new Color("bg", "fg"));

        String accessToken = 로그인_되어_있음("member@email.com", PASSWORD);

        // when
        ExtractableResponse<Response> Label_생성_요청_응답 = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .body(label)
                .when()
                .post("/api/labels")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        // then
        assertThat(Label_생성_요청_응답.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    /**
     * Scenario: 로그인 한 사용자중 Member 권한이 없다면 Label을 만들 수 없다.
     * Given 사용자가 ROLE_MEMBER로 로그인되어 있다
     * When ROLE_MEMBER 등급으로 Label 생성 요청시
     * Then 예외가 발생한다
     */
    @DisplayName("ROLE_MEMBER 등급의 사람이 아니면 라벨을 생성할 수 없다")
    @Test
    public void authorized_fail_member_create_label_test() throws JSONException {
        // given
        Label label = new Label("title", "테스트 입니다", new Color("bg", "fg"));
        String accessToken = 로그인_되어_있음(EMAIL, PASSWORD);

        // when
        ExtractableResponse<Response> Label_생성_요청_응답 = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .body(label)
                .when()
                .post("/api/labels")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract();

        // then
        assertThat(Label_생성_요청_응답.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }
}
