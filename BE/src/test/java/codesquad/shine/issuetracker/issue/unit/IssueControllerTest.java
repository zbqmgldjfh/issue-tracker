package codesquad.shine.issuetracker.issue.unit;

import codesquad.shine.issuetracker.ControllerTest;
import codesquad.shine.issuetracker.exception.unchecked.NotAvailableException;
import codesquad.shine.issuetracker.exception.unchecked.NotFoundException;
import codesquad.shine.issuetracker.issue.dto.request.CommentRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;

import static codesquad.shine.issuetracker.docs.ApiDocumentUtils.getDocumentRequest;
import static codesquad.shine.issuetracker.docs.ApiDocumentUtils.getDocumentResponse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class IssueControllerTest extends ControllerTest {

    @Test
    @DisplayName("가입된 회원이 Comment를 생성하면 서버에 저장후, OK응답을 반환한다.")
    public void create_comment_login_user_success_test() throws Exception {
        // given
        CommentRequest request = new CommentRequest("test내용 입니다!");

        given(jwtTokenFactory.createAccessToken(any(String.class))).willReturn("testAccessToken");
        given(jwtTokenFactory.parsePayload(any(String.class))).willReturn("test@naver.com");
        willDoNothing().given(commentService).add(any(Long.class), any(String.class), any(String.class));

        // when
        ResultActions resultActions = mockMvc.perform(post("/issues/{issueId}/comments", 1L)
                .header("Authorization", "Bearer " + "testAccessToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andDo(print());

        // then
        resultActions.andExpect(status().isCreated());

        //documentation
        resultActions.andDo(document("comment-create-success",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("header content type"),
                        headerWithName(HttpHeaders.AUTHORIZATION).description("bearer token")
                ),
                requestFields(
                        fieldWithPath("comment").description("description of comment")
                )
        ));
    }

    @Test
    @DisplayName("토큰이 없는 회원이 Comment를 생성하면 예외를 던진다.")
    public void create_comment_without_login_fail_test() throws Exception {
        // given
        CommentRequest request = new CommentRequest("test내용 입니다!");

        given(jwtTokenFactory.createAccessToken(any(String.class))).willReturn("testAccessToken");
        given(jwtTokenFactory.parsePayload(any(String.class))).willThrow(NotAvailableException.class);

        // when
        ResultActions resultActions = mockMvc.perform(post("/issues/{issueId}/comments", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andDo(print());

        // then
        resultActions.andExpect(
                (result) -> assertTrue(result.getResolvedException().getClass().isAssignableFrom(NotFoundException.class))
        ).andReturn();
    }

    @Test
    @DisplayName("가입된 회원이 Comment를 삭제후, OK를 반환한다.")
    public void delete_comment_login_user_success_test() throws Exception {
        // given
        given(jwtTokenFactory.createAccessToken(any(String.class))).willReturn("testAccessToken");
        given(jwtTokenFactory.parsePayload(any(String.class))).willReturn("test@naver.com");
        willDoNothing().given(commentService).delete(any(Long.class), any(Long.class), any(String.class));

        // when
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.delete("/issues/{issueId}/comments/{commentId}", 1L, 2L)
                .header("Authorization", "Bearer " + "testAccessToken")
        ).andDo(print());

        // then
        resultActions.andExpect(status().isOk());

        //documentation
        resultActions.andDo(document("comment-delete-success",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("bearer token")
                ),
                pathParameters(
                        parameterWithName("issueId").description("Id of Issue"),
                        parameterWithName("commentId").description("Id of Comment")
                )
        ));
    }

    @Test
    @DisplayName("가입된 회원이 Comment를 변경한 후, OK를 반환한다.")
    public void edit_comment_login_user_success_test() throws Exception {
        // given
        CommentRequest changeRequest = new CommentRequest("변경된 내용 입니다!");

        // 유저의 token 생성
        given(jwtTokenFactory.createAccessToken(any(String.class))).willReturn("testAccessToken");
        given(jwtTokenFactory.parsePayload(any(String.class))).willReturn("test@naverc.com");
        willDoNothing().given(commentService).edit(any(Long.class), any(Long.class), any(String.class), any(String.class));

        // when
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.patch("/issues/{issueId}/comments/{commentId}", 1L, 2L)
                .header("Authorization", "Bearer " + "testAccessToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changeRequest))
        ).andDo(print());

        // then
        resultActions.andExpect(status().isOk());

        //documentation
        resultActions.andDo(document("comment-edit-success",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("header content type"),
                        headerWithName(HttpHeaders.AUTHORIZATION).description("bearer token")
                ),
                requestFields(
                        fieldWithPath("comment").description("description of comment")
                ),
                pathParameters(
                        parameterWithName("issueId").description("Id of Issue"),
                        parameterWithName("commentId").description("Id of Comment")
                )
        ));
    }
}
