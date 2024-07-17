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
        boolean isValid = authService.verifyCode(email, verificationCode);
        if (isValid) {
            return ResponseEntity.ok(Api.<String>builder()
                    .status(Api.SUCCESS_STATUS)
                    .message("회원가입 이메일 인증이 성공적으로 진행되었습니다.")
                    .data("Email: " + email)
                    .build());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Api.<String>builder()
                    .status(Api.FAIL_STATUS)
                    .message("회원가입 인증 코드가 틀렸습니다.")
                    .data("Email: " + email)
                    .build());
        }
    }


    @PostMapping("/signup")
    public ResponseEntity<Api<String>> signup(@RequestBody @Valid SignupDto.SignupRequest request,
                                              BindingResult bindingResult) {
        Api<String> response;

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }

            response = Api.<String>builder()
                    .status(Api.FAIL_STATUS)
                    .message("유효성 검사 실패")
                    .data(errorMap.toString())
                    .build();

            logger.warn("유효성 검사 실패: {}", errorMap.toString());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            authService.signUp(request);

            response = Api.<String>builder()
                    .status(Api.SUCCESS_STATUS)
                    .message("회원가입 성공")
                    .data("Email: " + request.getEmail())
                    .build();

            logger.info("회원가입 성공 - Email: {}", request.getEmail());

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response = Api.<String>builder()
                    .status(Api.FAIL_STATUS)
                    .message(e.getMessage())
                    .data("Email: " + request.getEmail())
                    .build();

            logger.warn("회원가입 실패 - 이유: {}", e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response = Api.<String>builder()
                    .status(Api.FAIL_STATUS)
                    .message("서버 오류")
                    .data("Email: " + request.getEmail())
                    .build();

            logger.error("회원가입 중 서버 오류 발생: ", e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


}

