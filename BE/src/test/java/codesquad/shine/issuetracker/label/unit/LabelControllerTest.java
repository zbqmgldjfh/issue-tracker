package codesquad.shine.issuetracker.label.unit;

import codesquad.shine.issuetracker.ControllerTest;
import codesquad.shine.issuetracker.exception.unchecked.NotAvailableException;
import codesquad.shine.issuetracker.exception.unchecked.NotFoundException;
import codesquad.shine.issuetracker.label.business.dto.response.LabelDto;
import codesquad.shine.issuetracker.label.domain.Color;
import codesquad.shine.issuetracker.label.domain.Label;
import codesquad.shine.issuetracker.label.dto.reqeust.LabelCreateRequest;
import codesquad.shine.issuetracker.label.dto.reqeust.LabelEditRequest;
import codesquad.shine.issuetracker.label.dto.response.LabelEditResponse;
import codesquad.shine.issuetracker.label.dto.response.LabelsResponse;
import codesquad.shine.issuetracker.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class LabelControllerTest extends ControllerTest {

    @Test
    @DisplayName("가입된 회원이 Label을 생성하면 서버에 저장후, OK응답을 반환한다.")
    public void create_label_login_user_success_test() throws Exception {
        // given

        // 요청 생성
        LabelCreateRequest request = new LabelCreateRequest("색상1", "test", new Color("bg", "font"));

        given(jwtTokenFactory.createAccessToken(any(String.class))).willReturn("testAccessToken");
        given(jwtTokenFactory.parsePayload(any(String.class))).willReturn("test@naverc.com");

        // when
        ResultActions resultActions = mockMvc.perform(post("/labels")
                .header("Authorization", "Bearer " + "testAccessToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andDo(print());

        // then
        resultActions.andExpect(status().isCreated());

        //documentation
        resultActions.andDo(document("label-create-success",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("header content type"),
                        headerWithName(HttpHeaders.AUTHORIZATION).description("bearer token")
                ),
                requestFields(
                        fieldWithPath("title").description("title of label"),
                        fieldWithPath("description").description("description of label"),
                        fieldWithPath("color.backgroundColorCode").description("backgroundColorCode of label"),
                        fieldWithPath("color.fontColorCode").description("fontColorCode of label")
                )
        ));
    }

    @Test
    @DisplayName("토큰이 없는 회원이 Label을 생성하면 예외를 던진다.")
    public void create_label_without_login_fail_test() throws Exception {
        // given
        LabelCreateRequest request = new LabelCreateRequest("색상1", "test", new Color("bg", "font"));

        given(jwtTokenFactory.createAccessToken(any(String.class))).willReturn("testAccessToken");
        given(jwtTokenFactory.parsePayload(any(String.class))).willThrow(NotAvailableException.class);

        // when
        ResultActions resultActions = mockMvc.perform(post("/labels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andDo(print());

        // then
        resultActions.andExpect(
                (result) -> assertTrue(result.getResolvedException().getClass().isAssignableFrom(NotFoundException.class))
        ).andReturn();

        resultActions.andExpect(status().isNotFound());

        //documentation
        resultActions.andDo(document("label-create-fail",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("header content type")
                ),
                requestFields(
                        fieldWithPath("title").description("title of label"),
                        fieldWithPath("description").description("description of label"),
                        fieldWithPath("color.backgroundColorCode").description("backgroundColorCode of label"),
                        fieldWithPath("color.fontColorCode").description("fontColorCode of label")
                )
        ));
    }

    @Test
    @DisplayName("가입된 회원이 Label을 조회하면, 전체 라벨을 반환한다.")
    public void get_label_list_login_user_success_test() throws Exception {
        // given
        Label label1 = createLabel("test색", 1);
        Label label2 = createLabel("test색", 2);
        LabelDto labelDto1 = new LabelDto(label1);
        LabelDto labelDto2 = new LabelDto(label2);
        List<LabelDto> labelDtoList = new ArrayList<>();
        labelDtoList.add(labelDto1);
        labelDtoList.add(labelDto2);
        LabelsResponse response = new LabelsResponse(labelDtoList);

        // 가입된 유저
        User newUser = new User("test user", "zbqmgldjfh@gmail.com", "url");
        userRepository.save(newUser);

        // 유저의 token 생성
        given(jwtTokenFactory.createAccessToken(any(String.class))).willReturn("testAccessToken");
        given(jwtTokenFactory.parsePayload(any(String.class))).willReturn("test@naverc.com");
        given(labelService.findALL()).willReturn(response);

        // when
        ResultActions resultActions = mockMvc.perform(get("/labels")
                .header("Authorization", "Bearer " + "testAccessToken")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
        ).andDo(print());

        // then
        resultActions.andExpect(status().isOk());

        //documentation
        resultActions.andDo(document("label-create-success",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("bearer token")
                ),
                responseHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type")
                ),
                responseFields(
                        fieldWithPath("labels[].id").description("id of label"),
                        fieldWithPath("labels[].title").description("title of label"),
                        fieldWithPath("labels[].description").description("description of label"),
                        fieldWithPath("labels[].backgroundColorCode").description("backgroundColorCode of label"),
                        fieldWithPath("labels[].fontColorCode").description("fontColorCode of label"),
                        fieldWithPath("labels[].checked").description("Is checked label?")
                )
        ));
    }

    private Label createLabel(String title, int num) {
        return Label.builder()
                .title(title)
                .description("test" + num)
                .color(new Color("bg" + num, "font" + num))
                .build();
    }

    @Test
    @DisplayName("가입된 회원이 Label을 삭제후, OK를 반환한다.")
    public void delete_label_login_user_success_test() throws Exception {
        // given

        // 가입된 유저의 토큰 반환
        given(jwtTokenFactory.createAccessToken(any(String.class))).willReturn("testAccessToken");
        given(jwtTokenFactory.parsePayload(any(String.class))).willReturn("test@naverc.com");
        willDoNothing().given(labelService).delete(any(Long.class));

        // when
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.delete("/labels/{labelId}", 1L)
                .header("Authorization", "Bearer " + "testAccessToken")
        ).andDo(print());

        // then
        resultActions.andExpect(status().isOk());

        //documentation
        resultActions.andDo(document("label-delete-success",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("bearer token")
                ),
                pathParameters(
                        parameterWithName("labelId").description("Id of Label")
                )
        ));
    }

    @Test
    @DisplayName("가입된 회원이 Label을 변경한 후, OK를 반환한다.")
    public void edit_label_login_user_success_test() throws Exception {
        // given
        LabelEditResponse response = LabelEditResponse.builder()
                .title("색상 after")
                .description("edit!!")
                .color(new Color("bg", "fg"))
                .build();

        // 유저의 token 생성
        given(jwtTokenFactory.createAccessToken(any(String.class))).willReturn("testAccessToken");
        given(jwtTokenFactory.parsePayload(any(String.class))).willReturn("test@naverc.com");

        // 유저의 edit 호출
        given(labelService.edit(any(Long.class), any(LabelEditRequest.class))).willReturn(response);

        // 변경 request
        LabelEditRequest request = new LabelEditRequest("색상 after", "edit!!", new Color("bg", "fg"));

        // when
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.patch("/labels/{labelId}", 1L)
                .header("Authorization", "Bearer " + "testAccessToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andDo(print());

        // then
        resultActions.andExpect(status().isOk());

        //documentation
        resultActions.andDo(document("label-edit-success",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("bearer token")
                ),
                pathParameters(
                        parameterWithName("labelId").description("Id of Label")
                )
        ));
    }
}
