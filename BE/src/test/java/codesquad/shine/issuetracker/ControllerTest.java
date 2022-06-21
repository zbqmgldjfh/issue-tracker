package codesquad.shine.issuetracker;

import codesquad.shine.issuetracker.auth.JwtTokenFactory;
import codesquad.shine.issuetracker.issue.business.CommentService;
import codesquad.shine.issuetracker.issue.presentation.IssueController;
import codesquad.shine.issuetracker.label.business.LabelService;
import codesquad.shine.issuetracker.label.presentation.LabelController;
import codesquad.shine.issuetracker.user.domain.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest({LabelController.class, IssueController.class})
@AutoConfigureRestDocs
public class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected JwtTokenFactory jwtTokenFactory;

    @MockBean
    protected UserRepository userRepository;

    @MockBean
    protected LabelService labelService;

    @MockBean
    protected CommentService commentService;
}
