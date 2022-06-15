package codesquad.shine.issuetracker.auth;

import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class OAuthAdapter {

    // OAuthProperties를 OAuthProvider로 변환해준다.
    public static Map<String, OAuthProvider> getOauthProviders(OAuthProperties properties) {
        Map<String, OAuthProvider> oauthProvider = new HashMap<>();

        properties.getUser()
                .forEach((key, value) ->
                        oauthProvider.put(key, new OAuthProvider(value, properties.getProvider().get(key))));
        return oauthProvider;
    }
}
