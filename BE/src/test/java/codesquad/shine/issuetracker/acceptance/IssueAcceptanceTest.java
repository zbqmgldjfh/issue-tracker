package codesquad.shine.issuetracker.acceptance;

import codesquad.shine.issuetracker.issue.presentation.dto.request.CommentRequest;
import codesquad.shine.issuetracker.issue.presentation.dto.request.IssueRequest;
import codesquad.shine.issuetracker.issue.presentation.dto.request.IssueTitleRequest;
import codesquad.shine.issuetracker.issue.presentation.dto.request.StatusRequest;
import codesquad.shine.issuetracker.label.domain.Color;
import codesquad.shine.issuetracker.label.domain.Label;
import codesquad.shine.issuetracker.milestone.dto.request.MilestoneCreateRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.List;

import static codesquad.shine.issuetracker.acceptance.AuthSteps.로그인_되어_있음;
import static codesquad.shine.issuetracker.acceptance.CommentSteps.이슈_생성_요청;
import static codesquad.shine.issuetracker.acceptance.CommentSteps.이슈_응답_상태_확인;
import static codesquad.shine.issuetracker.acceptance.CommentSteps.이슈_응답_확인;
import static codesquad.shine.issuetracker.acceptance.CommentSteps.이슈에_댓글_추가_요청;
import static codesquad.shine.issuetracker.acceptance.LabelSteps.라벨_생성_요청;
import static codesquad.shine.issuetracker.acceptance.LabelSteps.라벨_응답_확인;
import static codesquad.shine.issuetracker.acceptance.MilestoneSteps.마일스톤_생성_요청;
import static codesquad.shine.issuetracker.acceptance.MilestoneSteps.마일스톤_응답_확인;
import static org.assertj.core.api.Assertions.assertThat;

public class IssueAcceptanceTest extends AcceptanceTest {

    /**
     * Scenario: MileStone 스토리 테스트
     * Given 사용자는 로그인 되어있다
     * <p>
     * When 신규 Label 생성 요청
     * Then 신규 Label이 생성됨
     * <p>
     * When 신규 Milestone 생성 요청
     * Then 신규 Milestone 생성됨
     * <p>
     * When 신규 Issue를 생성 요청
     * Then 신규 Issue가 생성됨
     * <p>
     * When 해당 Issue에게 close 요청
     * Then 해당 Issue가 닫힌다
     * <p>
     * When 해당 Issue에게 open 요청
     * Then 해당 Issue가 열린다
     * <p>
     * When 해당 Issue의 제목 편집을 요청한다.
     * Then 해당 Issue의 제목이 변경완료된다.
     * <p>
     * When 해당 Issue에 Comment를 추가 요청을 한다.
     * Then 해당 Issue에 Comment가 추가된다.
     * <p>
     * When 해당 Issue의 담당자를 가져오도록 요청한다.
     * Then 해당 Issue의 담당자 정보를 반환한다.
     * <p>
     * When 해당 Issue의 레이블을 가져오도록 요청한다.
     * Then 해당 Issue의 레이블 정보를 반환한다.
     * <p>
     * When 해당 Issue의 마일스톤을 가져오도록 요청한다.
     * Then 해당 Issue의 마일스톤 정보를 반환한다.
     */
    @Test
    public void issue_crud_test() {
        String accessToken = 로그인_되어_있음(MEMBER_EMAIL, PASSWORD);

        Label label = new Label("title", "테스트 입니다", new Color("bg", "fg"));
        var 라벨_생성_요청_응답 = 라벨_생성_요청(label, accessToken);
        Long labelId = 라벨_응답_확인(라벨_생성_요청_응답, HttpStatus.CREATED);

        MilestoneCreateRequest createRequest = new MilestoneCreateRequest("milestone", "테스트 입니다", LocalDate.now());
        var response = 마일스톤_생성_요청(createRequest, accessToken);
        Long milestoneId = 마일스톤_응답_확인(response, HttpStatus.CREATED);

        var 이슈_생성_요청_결과 = 이슈_생성_요청(accessToken, new IssueRequest("new issue", "", null, List.of(labelId), milestoneId));
        Long issueId = 이슈_응답_확인(이슈_생성_요청_결과, HttpStatus.CREATED);

        StatusRequest statusRequest = new StatusRequest(List.of(issueId));
        ExtractableResponse<Response> 이슈_닫기_변경_요청_결과 = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("status", "closed")
                .body(statusRequest)
                .when()
                .patch("/api/issues")
                .then().log().all()
                .extract();
        이슈_응답_상태_확인(이슈_닫기_변경_요청_결과, HttpStatus.OK);

        ExtractableResponse<Response> 이슈_열기_변경_요청_결과 = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("status", "open")
                .body(statusRequest)
                .when()
                .patch("/api/issues")
                .then().log().all()
                .extract();
        이슈_응답_상태_확인(이슈_열기_변경_요청_결과, HttpStatus.OK);

        IssueTitleRequest titleRequest = new IssueTitleRequest("change title");
        ExtractableResponse<Response> 이슈_타이트_변경_요청_결과 = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(titleRequest)
                .when()
                .patch("/api/issues/{issueId}/title", issueId)
                .then().log().all()
                .extract();
        이슈_응답_상태_확인(이슈_타이트_변경_요청_결과, HttpStatus.OK);

        var 이슈에_댓글_추가_요청_결과 = 이슈에_댓글_추가_요청(accessToken, issueId, new CommentRequest("this is comment!"));
        Long commentId = 이슈_응답_확인(이슈에_댓글_추가_요청_결과, HttpStatus.CREATED);

        ExtractableResponse<Response> 이슈_할당자_조회_요청_결과 = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/issues/{issueId}/assignees", issueId)
                .then().log().all()
                .extract();
        String userName = 이슈_할당자_조회_요청_결과.jsonPath().getString("assignees[0].userName");
        assertThat(userName).isEqualTo("admin");

        ExtractableResponse<Response> 이슈_라벨_조회_요청_결과 = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/issues/{issueId}/labels", issueId)
                .then().log().all()
                .extract();
        assertThat(이슈_라벨_조회_요청_결과.jsonPath().getString("labels[0].title")).isEqualTo("title");

        ExtractableResponse<Response> 이슈_마일스톤_조회_요청_결과 = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/issues/{issueId}/milestones", issueId)
                .then().log().all()
                .extract();
        assertThat(이슈_마일스톤_조회_요청_결과.jsonPath().getString("milestones[0].title")).isEqualTo("milestone");

    }
}
