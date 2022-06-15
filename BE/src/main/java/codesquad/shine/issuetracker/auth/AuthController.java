package codesquad.shine.issuetracker.auth;

import codesquad.shine.issuetracker.auth.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/{provider}/callback")
    public ResponseEntity<LoginResponse> login(@PathVariable String provider, @RequestParam String code) {
        LoginResponse response = authService.login(provider, code);
        return ResponseEntity.ok().body(response);
    }
}
