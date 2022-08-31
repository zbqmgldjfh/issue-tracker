package codesquad.shine.issuetracker.user.presentation;

import codesquad.shine.issuetracker.user.business.UserService;
import codesquad.shine.issuetracker.user.domain.User;
import codesquad.shine.issuetracker.user.presentation.dto.AuthUserResponse;
import codesquad.shine.support.auth.authorization.AuthenticationPrincipal;
import codesquad.shine.support.auth.userdetails.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/me")
    public ResponseEntity<AuthUserResponse> findMemberOfMine(@AuthenticationPrincipal AuthUser user) {
        User findUser = userService.findUserByEmail(user.getUsername().toString());
        return ResponseEntity.ok().body(AuthUserResponse.from(findUser));
    }
}
