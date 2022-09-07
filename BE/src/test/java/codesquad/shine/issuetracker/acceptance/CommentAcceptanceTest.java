package codesquad.shine.issuetracker.acceptance;

import codesquad.shine.issuetracker.issue.presentation.dto.request.CommentRequest;
import codesquad.shine.issuetracker.issue.presentation.dto.request.IssueRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static codesquad.shine.issuetracker.acceptance.AuthSteps.로그인_되어_있음;
import static codesquad.shine.issuetracker.acceptance.CommentSteps.이슈_생성_요청;
import static codesquad.shine.issuetracker.acceptance.CommentSteps.이슈_응답_상태_확인;
import static codesquad.shine.issuetracker.acceptance.CommentSteps.이슈_응답_확인;
import static codesquad.shine.issuetracker.acceptance.CommentSteps.이슈에_댓글_삭제_요청;
import static codesquad.shine.issuetracker.acceptance.CommentSteps.이슈에_댓글_추가_요청;
import static codesquad.shine.issuetracker.acceptance.CommentSteps.이슈에_댓글_편집_요청;

public class CommentAcceptanceTest extends AcceptanceTest {

    /**
     * Scenario: Comment 스토리 테스트
     * Given 사용자는 로그인 되어있다
     * When 신규 Issue를 생성 요청
     * Then 신규 Issue가 생성됨
     * When 생성된 Issue에 Comment를 생성 요청
     * Then 신규 Comment가 생성됨
     * When 해당 Issue의 Comment를 편집 요청
     * Then 해당 Comment가 편집된다
     * When 해당 Issue의 Comment를 삭제 요청
     * Then 해당 Comment가 삭제된다
     */
    @Test
    public void comment_crud_test() {
        String accessToken = 로그인_되어_있음(MEMBER_EMAIL, PASSWORD);

        var 이슈_생성_요청_결과 = 이슈_생성_요청(accessToken, new IssueRequest("new issue", "", null, null, null));
        Long issueId = 이슈_응답_확인(이슈_생성_요청_결과, HttpStatus.CREATED);

        var 이슈에_댓글_추가_요청_결과 = 이슈에_댓글_추가_요청(accessToken, issueId, new CommentRequest("this is comment!"));
        Long commentId = 이슈_응답_확인(이슈에_댓글_추가_요청_결과, HttpStatus.CREATED);

        var 이슈에_댓글_편집_요청_결과 = 이슈에_댓글_편집_요청(accessToken, issueId, commentId, new CommentRequest("edit comment!"));
        이슈_응답_상태_확인(이슈에_댓글_편집_요청_결과, HttpStatus.OK);

        var 이슈에_댓글_삭제_요청_결과 = 이슈에_댓글_삭제_요청(accessToken, issueId, commentId);
        이슈_응답_상태_확인(이슈에_댓글_삭제_요청_결과, HttpStatus.NO_CONTENT);
    }
}
