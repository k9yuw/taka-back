package taka.takaspring.Member.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import taka.takaspring.Member.dto.ForgotPwdDto;
import taka.takaspring.Member.service.AuthService;
import taka.takaspring.Member.service.ForgotPwdService;

@RestController
@RequestMapping("/api/auth")
public class ForgotPwdController {

    private final ForgotPwdService forgotPwdService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public ForgotPwdController (ForgotPwdService forgotPwdService) {
        this.forgotPwdService = forgotPwdService;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ForgotPwdDto.ForgotPwdResponse> forgotPassword(@RequestBody @Valid ForgotPwdDto.ForgotPwdRequest requestDto) {
        String email = forgotPwdService.sendTemporaryPassword(requestDto.getEmail());
        ForgotPwdDto.ForgotPwdResponse response = ForgotPwdDto.ForgotPwdResponse.builder()
                .email(email)
                .build();
        return ResponseEntity.ok(response);
    }

}
