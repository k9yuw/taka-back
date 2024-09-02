package taka.takaspring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import taka.takaspring.Member.jwt.*;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig {

    //AuthenticationManager가 인자로 받을 AuthenticationConfiguraion을 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration,
                          JwtUtil jwtUtil, RefreshRepository refreshRepository) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
    }

    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // csrf disable
        // session 방식에서는 session이 고정되므로 csrf 공격이 항상 방어되어야 함.
        // 반면 jwt 방식은 세션을 stateless 상태로 관리하므로 csrf 에 대한 공격을 방어하지 않아도 되어서 csrf를 disable
        http.csrf((auth) -> auth.disable());

        // From 로그인 방식 disable
        http.formLogin((auth) -> auth.disable());

        // http basic 인증 방식 disable
        http.httpBasic((auth) -> auth.disable());

        // 경로별 인가 작업
        http.authorizeHttpRequests(
                (auth) -> auth.requestMatchers("/**", "/api/auth/**", "/login").permitAll()
                        .anyRequest().authenticated());

        // LoginFilter 앞에 JWTFilter 추가
        http
                .addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class);

        //LoginFilter 추가 (AuthenticationManager 메소드에 authenticationConfiguration, jwtUtil 객체를 넣어야 함)
        http.addFilterAt(
                new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil,
                        refreshRepository),
                UsernamePasswordAuthenticationFilter.class);

        // 기존에 존재하던 LogoutFilter 앞에 CustomLogoutFilter 추가
        http.addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository),
                LogoutFilter.class);

        // 세션 stateless 상태로 설정
        http.sessionManagement(
                (session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
