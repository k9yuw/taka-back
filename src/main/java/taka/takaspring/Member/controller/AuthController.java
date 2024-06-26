package taka.takaspring.Member.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Member.service.AuthService;
import taka.takaspring.common.Api;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Api<String>> signup(@RequestBody @Valid UserDto.SignupRequest request,
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
        } else {

            UserEntity signUpEntity = authService.signUp(request.toEntity());

            response = Api.<String>builder()
                    .status(Api.SUCCESS_STATUS)
                    .message("회원가입 성공")
                    .data("ID: " + request.getLoginId())
                    .data("Email: " + request.getEmail())
                    .build();

            logger.info("회원가입 성공 - ID: {}, Email: {}", request.getLoginId(), request.getEmail());

            return ResponseEntity.ok(response);
        }
    }

}