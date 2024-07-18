package taka.takaspring.Member.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import taka.takaspring.Member.jwt.JwtUtil;
import taka.takaspring.Member.service.dto.SignupDto;
import taka.takaspring.Member.service.AuthService;
import taka.takaspring.common.Api;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/send-verification-code")
    public ResponseEntity<Api<String>> sendVerificationCode(@RequestParam String email) {
        authService.sendVerificationCode(email);
        return ResponseEntity.ok(Api.<String>builder()
                .status(Api.SUCCESS_STATUS)
                .message("회원가입 인증 코드가 이메일로 전송되었습니다.")
                .data("Email: " + email)
                .build());
    }

    @PostMapping("/verify-code")
    public ResponseEntity<Api<String>> verifyCode(@RequestParam String email, @RequestParam String verificationCode) {
        return ResponseEntity.ok(Api.<String>builder()
                .status(Api.SUCCESS_STATUS)
                .message("회원가입 이메일 인증이 성공적으로 진행되었습니다.")
                .data("Email: " + email)
                .build());
    }


    @PostMapping("/signup")
    public ResponseEntity<Api<String>> signup(@RequestBody @Valid SignupDto.SignupRequest request,
                                              BindingResult bindingResult) {
        Api<String> response;
        authService.signUp(request);

        response = Api.<String>builder()
                .status(Api.SUCCESS_STATUS)
                .message("회원가입 성공")
                .data("Email: " + request.getEmail())
                .build();

        logger.info("회원가입 성공 - Email: {}", request.getEmail());
        return ResponseEntity.ok(response);

    }
}

