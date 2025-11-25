package chervotkin.dev.eventnotificator.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;

@Service
public class JwtTokenManager {

    private final Key signKey;

    public JwtTokenManager(@Value("${jwt.sign-key}") String key) {
        this.signKey = new SecretKeySpec(
                key.getBytes(StandardCharsets.UTF_8),
                SignatureAlgorithm.HS256.getJcaName()
        );
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(signKey)
                .build()
                .parseClaimsJws(token)
                .getPayload();
    }

    public boolean isTokenValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getLoginFromToken(String token) {
        return getClaims(token).getSubject();
    }

    public String getRoleFromToken(String token) {
        return getClaims(token).get("role", String.class);
    }

    public Long getUserIdFromToken(String jwt) {
        return Jwts.parser()
                .setSigningKey(signKey)
                .build()
                .parseClaimsJws(jwt)
                .getPayload()
                .get("userId", Long.class);
    }

}
