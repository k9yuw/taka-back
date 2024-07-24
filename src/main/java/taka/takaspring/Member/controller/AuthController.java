package taka.takaspring.Member.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import taka.takaspring.Member.jwt.JwtUtil;
import taka.takaspring.Member.dto.SignupDto;
import taka.takaspring.Member.service.AuthService;
import taka.takaspring.common.Api;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/send-verification-code")
    public ResponseEntity<Void> sendVerificationCode(@RequestParam String email) {
        authService.sendVerificationCode(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify-code")
    public ResponseEntity<Void> verifyCode(@RequestParam String email, @RequestParam String verificationCode) {
        return ResponseEntity.ok().build();
    }


    @PostMapping("/signup")
    public ResponseEntity<SignupDto.SignUpResponse> signup(@RequestBody @Valid SignupDto.SignupRequest request,
                                                           BindingResult bindingResult) {
        SignupDto.SignUpResponse response = authService.signUp(request);
        return ResponseEntity.ok().body(response);
    }
}

