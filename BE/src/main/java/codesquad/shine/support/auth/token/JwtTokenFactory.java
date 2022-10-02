package codesquad.shine.support.auth.token;

import codesquad.shine.issuetracker.common.redis.RedisService;
import codesquad.shine.issuetracker.exception.ErrorCode;
import codesquad.shine.issuetracker.exception.unchecked.NotAvailableException;
import codesquad.shine.support.auth.authentication.AuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
public class JwtTokenFactory {

    private final String secretKey;
    private final long accessTokenValidityTime;
    private final long refreshTokenValidityTime;
    private final RedisService redisService;

    public JwtTokenFactory(String secretKey, long accessTokenValidityTime, long refreshTokenValidityTime, RedisService redisService) {
        this.secretKey = secretKey;
        this.accessTokenValidityTime = accessTokenValidityTime;
        this.refreshTokenValidityTime = refreshTokenValidityTime;
        this.redisService = redisService;
    }

    public String createAccessToken(String payload) {
        Claims claims = Jwts.claims().setSubject(payload);
        Date createdDate = new Date();
        Date expirationDate = new Date(createdDate.getTime() + accessTokenValidityTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createAccessToken(String principal, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(principal);
        Date createdDate = new Date();
        Date expirationDate = new Date(createdDate.getTime() + accessTokenValidityTime);

        return this.buildToken(roles, claims, createdDate, expirationDate);
    }

    public String createRefreshToken(String principal, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(principal);
        Date createdDate = new Date();
        Date expirationDate = new Date(createdDate.getTime() + refreshTokenValidityTime);
        String refreshToken = buildToken(roles, claims, createdDate, expirationDate);

        redisService.setValuesWithDuration(principal, refreshToken, Duration.ofMillis(refreshTokenValidityTime));

        return refreshToken;
    }

    private String buildToken(List<String> roles, Claims claims, Date createdDate, Date expirationDate) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .claim("roles", roles)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String parsePayload(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        } catch (JwtException e) {
            throw new NotAvailableException(ErrorCode.INVALID_TOKEN);
        }
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public List<String> getRoles(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("roles", List.class);
    }

    public String reissueAccessToken(String refreshToken) {
        String principal = parsePayload(refreshToken);
        List<String> roles = getRoles(refreshToken);

        String refreshTokenInRedis = redisService.getValues(principal);
        if (Objects.isNull(refreshTokenInRedis)) {
            throw new AuthenticationException("인증 정보가 만료된 회원 입니다.");
        }

        return createAccessToken(principal, roles);
    }
}
