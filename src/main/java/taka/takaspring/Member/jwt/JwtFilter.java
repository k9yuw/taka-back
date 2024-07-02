package taka.takaspring.Member.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Member.db.enums.RoleType;

import java.io.IOException;
import java.io.PrintWriter;

import static taka.takaspring.Member.db.enums.RoleType.USER;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 헤더에서 access키에 담긴 토큰을 꺼냄
        String accessToken = request.getHeader("access");

        // 토큰이 없다면 다음 필터로 넘김
        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {

            //response body
            PrintWriter writer = response.getWriter();
            writer.print("access token expired");

            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 토큰이 access인지 확인 (발급시 payload에 명시)
        String category = jwtUtil.getCategory(accessToken);

        if (!category.equals("access")) {

            //response body
            PrintWriter writer = response.getWriter();
            writer.print("invalid access token");

            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // username, role 값을 획득하여 principalDetails로 넘겨줌
        String email = jwtUtil.getEmail(accessToken);
        String roleStr = jwtUtil.getRole(accessToken);
        RoleType role = RoleType.valueOf(roleStr.toUpperCase());

        UserEntity user = UserEntity.builder().
                  email(email).
                  role(role).
                  build();

        PrincipalDetails principalDetails = new PrincipalDetails(user);

        // accessToken에서 뽑아낸 principalDetails을 토큰화(authToken)하여 세션(SecurityContextHolder)에 등록해줌.
        Authentication authToken = new UsernamePasswordAuthenticationToken(principalDetails, null,
                principalDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}