package chervotkin.dev.eventnotificator.security;

import chervotkin.dev.user.AuthenticatedUser;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenManager jwtTokenManager;

    public JwtTokenFilter(JwtTokenManager jwtTokenManager) {
        this.jwtTokenManager = jwtTokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException, java.io.IOException {

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.substring(7);

        if (!jwtTokenManager.isTokenValid(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        String login = jwtTokenManager.getLoginFromToken(token);
        Long userId = jwtTokenManager.getUserIdFromToken(token);
        String role = jwtTokenManager.getRoleFromToken(token);

        AuthenticatedUser authUser = new AuthenticatedUser(userId, login, role);

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        authUser,
                        null,
                        List.of(new SimpleGrantedAuthority(role))
                );

        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }
}
