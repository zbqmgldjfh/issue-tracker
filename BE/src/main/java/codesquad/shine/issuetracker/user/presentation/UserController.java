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
    public ResponseEntity<AuthUserResponse> findMemberOfMine(@AuthenticationPrincipal(required = true) AuthUser user) {
        log.info("User Info : {}", user.getUsername());
        User findUser = userService.findUserByEmail(user.getUsername().toString());
        AuthUserResponse authUserResponse = new AuthUserResponse(findUser.getId(), findUser.getEmail(), findUser.getUserName());
        return ResponseEntity.ok().body(authUserResponse);
    }
}
