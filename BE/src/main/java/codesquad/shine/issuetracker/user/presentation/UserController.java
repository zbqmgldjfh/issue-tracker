package codesquad.shine.issuetracker.user.presentation;

import codesquad.shine.issuetracker.user.presentation.dto.UserResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/users/me")
    public ResponseEntity<UserResponseDto> findMemberOfMine() {
        return ResponseEntity.ok().build();
    }
}
