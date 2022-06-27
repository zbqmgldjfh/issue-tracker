package codesquad.shine.issuetracker.issue.unit;

import codesquad.shine.issuetracker.ControllerTest;
import codesquad.shine.issuetracker.comment.domain.Comment;
import codesquad.shine.issuetracker.comment.presentation.dto.response.CommentDto;
import codesquad.shine.issuetracker.common.vo.Assignee;
import codesquad.shine.issuetracker.exception.unchecked.NotAvailableException;
import codesquad.shine.issuetracker.exception.unchecked.NotFoundException;
import codesquad.shine.issuetracker.issue.domain.Issue;
import codesquad.shine.issuetracker.issue.presentation.dto.request.CommentRequest;
import codesquad.shine.issuetracker.issue.presentation.dto.request.IssueRequest;
import codesquad.shine.issuetracker.issue.presentation.dto.request.IssueTitleRequest;
import codesquad.shine.issuetracker.issue.presentation.dto.response.IssueDetailResponse;
import codesquad.shine.issuetracker.issue.presentation.dto.response.IssueFormResponse;
import codesquad.shine.issuetracker.label.business.dto.response.LabelDto;
import codesquad.shine.issuetracker.label.domain.Color;
import codesquad.shine.issuetracker.label.domain.Label;
import codesquad.shine.issuetracker.milestone.business.dto.MilestoneDto;
import codesquad.shine.issuetracker.milestone.domain.Milestone;
import codesquad.shine.issuetracker.user.domain.User;
import codesquad.shine.issuetracker.user.presentation.dto.UserResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        MilestoneDto milestoneDto = MilestoneDto.of(new Milestone(3L, "mile", "this is mile", LocalDate.now(), true));

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
    @DisplayName("Issue Detail 단건을 조화한다.")
    public void search_issue_detail_success_test() throws Exception {
        // given
        User user = new User(1L, "shine", "test@naver.com", "url");
        Issue issue = new Issue("issue title", true, user);
        Comment comment = new Comment(2L, "comment", issue, user);
        Assignee assignee = Assignee.of(user, true);
        LabelDto labelDto = new LabelDto(new Label(1L, "label", "this is label", new Color("bg", "fe")));
        MilestoneDto milestoneDto = MilestoneDto.of(new Milestone(3L, "mile", "this is mile", LocalDate.now(), true));

        IssueDetailResponse response = IssueDetailResponse.builder()
                .title("this is issue") // 즉시로딩
                .author(UserResponseDto.of(user)) // lazy로딩 fetch-join
                .open(true) // 즉시로딩
                .createdDateTime(LocalDateTime.now()) // 즉시로딩
                .comments(List.of(CommentDto.of(comment))) // lazy로딩 batch-fetch
                .assignees(List.of(assignee)) // lazy로딩 batch-fetch
                .labels(List.of(labelDto)) // lazy로딩 batch-fetch
                .milestone(milestoneDto) // lazy로딩 fetch-join
                .build();

        given(issueService.findIssueDetailById(any(Long.class))).willReturn(response);

        // when
        ResultActions resultActions = mockMvc.perform(get("/issues/{issueId}", 1L)
                .header("Authorization", "Bearer " + "testAccessToken")
        ).andDo(print());

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        //documentation
        resultActions.andDo(document("search-issue-detail-success",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("bearer token")
                ),
                responseHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaType.APPLICATION_JSON)
                ),
                responseFields(
                        fieldWithPath("title").description("Title of Issue"),
                        fieldWithPath("author.userName").description("User name"),
                        fieldWithPath("author.avatarUrl").description("AvatarUrl of User"),
                        fieldWithPath("open").description("Is open issue?"),
                        fieldWithPath("createdDateTime").description("Label created date-time"),

                        fieldWithPath("comments[].user.id").description("Id of User"),
                        fieldWithPath("comments[].user.userName").description("Name of User"),
                        fieldWithPath("comments[].user.email").description("Email of User"),
                        fieldWithPath("comments[].user.avatarUrl").description("AvatarUrl of User"),
                        fieldWithPath("comments[].description").description("Description of comment"),
                        fieldWithPath("comments[].avatarUrl").description("user avatar url"),
                        fieldWithPath("comments[].createdDateTime").description("User created data-time"),

                        fieldWithPath("assignees[].userId").description("Id of assignee"),
                        fieldWithPath("assignees[].userName").description("Title of assignee"),
                        fieldWithPath("assignees[].avatarUrl").description("avatarUrl of assignee"),
                        fieldWithPath("assignees[].assigned").description("Is assigned user?"),

                        fieldWithPath("labels[].id").description("DueDate of labels"),
                        fieldWithPath("labels[].title").description("Title of labels"),
                        fieldWithPath("labels[].description").description("Description of labels"),
                        fieldWithPath("labels[].backgroundColorCode").description("Background Color of labels"),
                        fieldWithPath("labels[].fontColorCode").description("Font Color of labels"),

                        fieldWithPath("milestone.id").description("Id of milestone"),
                        fieldWithPath("milestone.title").description("Title of milestone"),
                        fieldWithPath("milestone.description").description("Description of milestone"),
                        fieldWithPath("milestone.createdDateTime").description("Milestone created date-time"),
                        fieldWithPath("milestone.dueDate").description("Due-date of milestone"),
                        fieldWithPath("milestone.openedIssues").description("OpenedIssues of milestone"),
                        fieldWithPath("milestone.closedIssues").description("ClosedIssues of milestone")
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

    @Test
    public void change_title_login_user_success_test() throws Exception {
        // given
        IssueTitleRequest request = new IssueTitleRequest("변경된 title");

        given(jwtTokenFactory.createAccessToken(any(String.class))).willReturn("testAccessToken");
        given(jwtTokenFactory.parsePayload(any(String.class))).willReturn("test@naverc.com");

        // when
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.patch("/issues/{issueId}/title", 1L)
                .header("Authorization", "Bearer " + "testAccessToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andDo(print());

        // then
        resultActions.andExpect(status().isOk());

        //documentation
        resultActions.andDo(document("issue-title-edit-success",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("header content type"),
                        headerWithName(HttpHeaders.AUTHORIZATION).description("bearer token")
                ),
                requestFields(
                        fieldWithPath("title").description("title of issue")
                ),
                pathParameters(
                        parameterWithName("issueId").description("Id of Issue")
                )
        ));
    }
}
