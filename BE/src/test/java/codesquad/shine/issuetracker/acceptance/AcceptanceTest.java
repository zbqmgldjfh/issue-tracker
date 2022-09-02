package codesquad.shine.issuetracker.acceptance;

import codesquad.shine.issuetracker.DataLoader;
import codesquad.shine.issuetracker.utils.DatabaseCleanup;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {

    public static final String USERNAME_FIELD = "username";
    public static final String PASSWORD_FIELD = "password";

    protected static final String ADMIN_EMAIL = "admin@email.com";
    protected static final String MEMBER_EMAIL = "member@email.com";
    protected static final String PASSWORD = "password";
    protected static final String NAME = "admin";

    @LocalServerPort
    int port;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private DataLoader dataLoader;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        databaseCleanup.execute();
        dataLoader.loadData();
    }
}
