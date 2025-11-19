package chervotkin.dev.eventnotificator.security;

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

    public JwtTokenManager(
            @Value("${jwt.sign-key}") String signKey
    ) {
        this.signKey = new SecretKeySpec(
                signKey.getBytes(StandardCharsets.UTF_8),
                SignatureAlgorithm.HS256.getJcaName()
        );
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(signKey)
                    .build()
                    .parse(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getLoginFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(signKey)
                .build()
                .parseClaimsJws(token)
                .getPayload()
                .getSubject();
    }

    public String getRoleFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(signKey)
                .build()
                .parseClaimsJws(token)
                .getPayload()
                .get("role", String.class);
    }
}
