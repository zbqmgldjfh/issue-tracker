package codesquad.shine.issuetracker.label.intergrate;

import codesquad.shine.issuetracker.auth.JwtTokenFactory;
import codesquad.shine.issuetracker.exception.unchecked.NotAvailableException;
import codesquad.shine.issuetracker.label.domain.Color;
import codesquad.shine.issuetracker.label.dto.reqeust.LabelCreateRequest;
import codesquad.shine.issuetracker.user.domain.User;
import codesquad.shine.issuetracker.user.domain.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static codesquad.shine.issuetracker.docs.ApiDocumentUtils.getDocumentRequest;
import static codesquad.shine.issuetracker.docs.ApiDocumentUtils.getDocumentResponse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ActiveProfiles("local")
class LabelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenFactory jwtTokenFactory;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("가입된 회원이 Label을 생성하면 서버에 저장후, OK응답을 반환한다.")
    public void create_label_login_user_success_test() throws Exception {
        // given

        // 가입된 유저
        User newUser = new User("test user", "zbqmgldjfh@gmail.com", "url");
        userRepository.save(newUser);

        // 유저의 token 생성
        String token = jwtTokenFactory.createAccessToken("zbqmgldjfh@gmail.com");

        // 요청 생성
        LabelCreateRequest request = new LabelCreateRequest("색상1", "test", new Color("bg", "font"));

        // when
        ResultActions resultActions = mockMvc.perform(post("/labels")
                .header("Authorization", "Bearer " + token)
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
    @DisplayName("가입되지 않은 회원이 Label을 생성하면 예외를 던진다.")
    public void create_label_without_login_fail_test() throws Exception {
        // given
        LabelCreateRequest request = new LabelCreateRequest("색상1", "test", new Color("bg", "font"));

        // when
        ResultActions resultActions = mockMvc.perform(post("/labels")
                .header("Authorization", "Bearer " + "")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andDo(print());

        // then
        resultActions.andExpect(
                (rslt) -> assertTrue(rslt.getResolvedException().getClass().isAssignableFrom(NotAvailableException.class))
        ).andReturn();

        resultActions.andExpect(status().isUnauthorized());

        //documentation
        resultActions.andDo(document("label-create-fail",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
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
    @DisplayName("가입된 회원이 Label을 조회하면, 전체 라벨을 반환한다.")
    public void get_label_list_login_user_success_test() throws Exception {
        // given

        // 가입된 유저
        User newUser = new User("test user", "zbqmgldjfh@gmail.com", "url");
        userRepository.save(newUser);

        // 유저의 token 생성
        String token = jwtTokenFactory.createAccessToken("zbqmgldjfh@gmail.com");

        // when
        ResultActions resultActions = mockMvc.perform(get("/labels")
                .header("Authorization", "Bearer " + token)
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
                        fieldWithPath("labels[].id").description("result validation"),
                        fieldWithPath("labels[].title").description("result message"),
                        fieldWithPath("labels[].description").description("result message"),
                        fieldWithPath("labels[].backgroundColorCode").description("result message"),
                        fieldWithPath("labels[].fontColorCode").description("result message")
                )
        ));
    }
}
