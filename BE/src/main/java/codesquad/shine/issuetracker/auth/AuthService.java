package codesquad.shine.issuetracker.auth;

import codesquad.shine.issuetracker.auth.dto.LoginResponse;
import codesquad.shine.issuetracker.user.domain.User;
import codesquad.shine.issuetracker.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenFactory jwtTokenFactory;
    private final InMemoryProviderRepository providerRepository;
    private final Map<String, AuthClient> authClientMap;

    public LoginResponse login(String providerName, String code) {
        OAuthProvider oAuthProvider = providerRepository.findByProviderName(providerName);
        log.info("[oAuthProvider] {}", oAuthProvider.toString());

        // OAuthToken 받아오기
        AuthClient authClient = authClientMap.get(providerName);
        OAuthToken oAuthToken = authClient.getToken(code, oAuthProvider);
        log.info("[oAuthToken] {}", oAuthToken.getAccessToken());

        // token으로 유저 정보 받아오기
        OAuthUser findUser = authClient.getUser(oAuthToken.getAccessToken(), oAuthProvider);

        // 유저 등록과 반환
        User user = registerMember(findUser);

        // email로 token 발급
        String jwtToken = jwtTokenFactory.createAccessToken(user.getEmail());
        log.info("[jwtToken]: {}", jwtToken);

        return LoginResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getUserName())
                .avatarUrl(user.getAvatarUrl())
                .accessToken(oAuthToken.getAccessToken())
                .tokenType(oAuthToken.getTokenType())
                .build();
    }

    private User registerMember(OAuthUser findUser) {
        String userEmail = findUser.getEmail();

        // email로 회원을 찾아오기
        Optional<User> optionalUser = userRepository.findUserByEmail(userEmail);

        // 등록되지 않은 회원이라면
        if (!optionalUser.isPresent()) {
            User newUser = User.of(findUser);
            return userRepository.save(newUser);
        }
        // 등록된 회원이라면
        return optionalUser.get();
    }
}
