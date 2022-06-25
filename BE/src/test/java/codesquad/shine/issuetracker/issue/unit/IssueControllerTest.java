package codesquad.shine.issuetracker.issue.unit;

import codesquad.shine.issuetracker.ControllerTest;
import codesquad.shine.issuetracker.common.vo.Assignee;
import codesquad.shine.issuetracker.exception.unchecked.NotAvailableException;
import codesquad.shine.issuetracker.exception.unchecked.NotFoundException;
import codesquad.shine.issuetracker.issue.presentation.dto.request.CommentRequest;
import codesquad.shine.issuetracker.issue.presentation.dto.request.IssueRequest;
import codesquad.shine.issuetracker.issue.presentation.dto.response.IssueFormResponse;
import codesquad.shine.issuetracker.label.business.dto.response.LabelDto;
import codesquad.shine.issuetracker.label.domain.Color;
import codesquad.shine.issuetracker.label.domain.Label;
import codesquad.shine.issuetracker.milestone.business.dto.MilestoneDto;
import codesquad.shine.issuetracker.milestone.domain.Milestone;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.List;

import static codesquad.shine.issuetracker.docs.ApiDocumentUtils.getDocumentRequest;
import static codesquad.shine.issuetracker.docs.ApiDocumentUtils.getDocumentResponse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class IssueControllerTest extends ControllerTest {

    @Test
    public void get_issue_form_success_test() throws Exception {
        // given
        Assignee assignee = new Assignee(1L, "testUser", "testUrl", false);
        LabelDto labelDto = new LabelDto(new Label(2L, "label", "this is label", new Color("bg", "font")));
        MilestoneDto milestoneDto = new MilestoneDto(new Milestone(3L, "mile", "this is mile", LocalDate.now(), true));

        given(issueService.getIssueForm()).willReturn(new IssueFormResponse(List.of(assignee), List.of(labelDto), List.of(milestoneDto)));

        given(jwtTokenFactory.createAccessToken(any(String.class))).willReturn("testAccessToken");
        given(jwtTokenFactory.parsePayload(any(String.class))).willReturn("test@naver.com");

        // when
        ResultActions resultActions = mockMvc.perform(get("/issues/form")
                .header("Authorization", "Bearer " + "testAccessToken")
        ).andDo(print());

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        //documentation
        resultActions.andDo(document("issue-get-form-success",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("bearer token")
                ),
                responseHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaType.APPLICATION_JSON)
                ),
                responseFields(
                        fieldWithPath("assignees[].userId").description("Id of User"),
                        fieldWithPath("assignees[].userName").description("User name"),
                        fieldWithPath("assignees[].avatarUrl").description("AvatarUrl of User"),
                        fieldWithPath("assignees[].assigned").description("Is user assigned?"),
                        fieldWithPath("labels[].id").description("Id of label"),
                        fieldWithPath("labels[].title").description("Title of label"),
                        fieldWithPath("labels[].description").description("Description of label"),
                        fieldWithPath("labels[].backgroundColorCode").description("Background Color"),
                        fieldWithPath("labels[].fontColorCode").description("FontColor Color"),
                        fieldWithPath("milestones[].id").description("Id of milestones"),
                        fieldWithPath("milestones[].title").description("Title of milestones"),
                        fieldWithPath("milestones[].description").description("Description of milestones"),
                        fieldWithPath("milestones[].createdDateTime").description("CreatedDateTime of milestones"),
                        fieldWithPath("milestones[].dueDate").description("DueDate of milestones"),
                        fieldWithPath("milestones[].openedIssues").description("OpenedIssues count of milestones"),
                        fieldWithPath("milestones[].closedIssues").description("ClosedIssues count of milestones")
                )
        ));
    }

    @Test
    @DisplayName("Issue를 정상생성하고 201 응답을 반환한다.")
    public void create_issue_success_test() throws Exception {
        // given
        IssueRequest request = new IssueRequest("title", "this is comment", null, null, 2L);

        given(jwtTokenFactory.createAccessToken(any(String.class))).willReturn("testAccessToken");
        given(jwtTokenFactory.parsePayload(any(String.class))).willReturn("test@naver.com");
        willDoNothing().given(issueService).create(any(IssueRequest.class), any(String.class));

        // when
        ResultActions resultActions = mockMvc.perform(post("/issues/form")
                .header("Authorization", "Bearer " + "testAccessToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andDo(print());

        // then
        resultActions.andExpect(status().isCreated());

        // documentation
        resultActions.andDo(document("issue-create-success",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("header content type"),
                        headerWithName(HttpHeaders.AUTHORIZATION).description("bearer token")
                ),
                requestFields(
                        fieldWithPath("title").description("Title of comment"),
                        fieldWithPath("comment").description("User created comments"),
                        fieldWithPath("assigneeIds").description("Id of assignee"),
                        fieldWithPath("labelIds").description("Id of Label"),
                        fieldWithPath("milestoneId").description("Id of milestone")
                )
        ));
    }

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
