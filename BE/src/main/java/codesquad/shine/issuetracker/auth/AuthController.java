package codesquad.shine.issuetracker.auth;

import codesquad.shine.issuetracker.auth.dto.LoginResponse;
import codesquad.shine.issuetracker.auth.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/{provider}/callback")
    public ResponseEntity<LoginResponse> login(@PathVariable String provider, @RequestParam String code, HttpServletResponse response) {
        UserInfo userInfo = authService.login(provider, code);
        response.addHeader("Authorization", "Bearer " + userInfo.getAccessToken());
        return ResponseEntity.ok().body(LoginResponse.of(userInfo));
    }
}
