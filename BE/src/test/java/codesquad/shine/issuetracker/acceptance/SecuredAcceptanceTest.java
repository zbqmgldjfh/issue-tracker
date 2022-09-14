package codesquad.shine.issuetracker.acceptance;

import codesquad.shine.issuetracker.label.domain.Color;
import codesquad.shine.issuetracker.label.domain.Label;
import codesquad.shine.support.auth.context.SecurityContextHolder;
import org.json.JSONException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static codesquad.shine.issuetracker.acceptance.AuthSteps.로그인_되어_있음;
import static codesquad.shine.issuetracker.acceptance.LabelSteps.라벨_생성_요청;
import static codesquad.shine.issuetracker.acceptance.LabelSteps.라벨_응답_상태_확인;
import static codesquad.shine.issuetracker.acceptance.LabelSteps.라벨_응답_확인;

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
        var 라벨_생성_요청_응답 = 라벨_생성_요청(label, accessToken);

        // then
        라벨_응답_확인(라벨_생성_요청_응답, HttpStatus.CREATED);
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
        String accessToken = 로그인_되어_있음(ADMIN_EMAIL, PASSWORD);

        // when
        var 라벨_생성_요청_응답 = 라벨_생성_요청(label, accessToken);

        // then
        라벨_응답_상태_확인(라벨_생성_요청_응답, HttpStatus.UNAUTHORIZED);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }
}
