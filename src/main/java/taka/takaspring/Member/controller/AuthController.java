package taka.takaspring.Member.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taka.takaspring.Member.dto.sendVerificationCodeDto;
import taka.takaspring.Member.dto.SignupDto;
import taka.takaspring.Member.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup/send-verification-code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "코드 전송이 성공되었습니다.",
                    content = @Content(schema = @Schema(implementation = sendVerificationCodeDto.Response.class))),
            @ApiResponse(responseCode = "409", description = "이미 회원가입이 되어 있는 이메일입니다.",
                    content = @Content(schema = @Schema(implementation = sendVerificationCodeDto.ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"errorCode\": \"409\", \"errorMessage\": \"이미 회원가입이 되어 있는 이메일입니다.\"}"))),
            @ApiResponse(responseCode = "500", description = "코드 전송에 오류가 발생하였습니다. 다시 시도해주세요",
                    content = @Content(schema = @Schema(implementation = sendVerificationCodeDto.ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"errorCode\": \"500\", \"errorMessage\": \"코드 전송에 오류가 발생하였습니다. 다시 시도해주세요.\"}")))
    })
    public ResponseEntity<sendVerificationCodeDto.Response> sendVerificationCode(@RequestBody sendVerificationCodeDto.Request request) {
        authService.sendVerificationCode(request.getEmail());
        return ResponseEntity.ok(new sendVerificationCodeDto.Response(request.getEmail(), "코드 전송이 성공되었습니다."));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이메일 인증이 성공되었습니다.",
                    content = @Content(schema = @Schema(implementation = sendVerificationCodeDto.Response.class))),
            @ApiResponse(responseCode = "400", description = "인증번호가 틀렸습니다. 다시 시도해주세요.",
                    content = @Content(schema = @Schema(implementation = sendVerificationCodeDto.ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"errorCode\": \"400\", \"errorMessage\": \"인증번호가 틀렸습니다. 다시 시도해주세요.\"}"))),
            @ApiResponse(responseCode = "500", description = "인증번호 검증 시 오류가 발생하였습니다. 다시 시도해주세요",
                    content = @Content(schema = @Schema(implementation = sendVerificationCodeDto.ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"errorCode\": \"500\", \"errorMessage\": \"인증번호 검증 시 오류가 발생하였습니다. 다시 시도해주세요\"}")))
    })
    @PostMapping("/signup/verify-code")
    public ResponseEntity<Void> verifyCode(@RequestParam("email") String email, @RequestParam("verificationCode") String verificationCode) {
        authService.verifyCode(email, verificationCode);
        return ResponseEntity.ok().build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입이 성공하였습니다.",
                    content = @Content(schema = @Schema(implementation = sendVerificationCodeDto.Response.class))),
            @ApiResponse(responseCode = "400", description = "회원정보 기입 오류가 발생하였습니다. 다시 시도해주세요.",
                    content = @Content(schema = @Schema(implementation = sendVerificationCodeDto.ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"errorCode\": \"400\", \"errorMessage\": \"회원정보 기입 오류가 발생하였습니다. 다시 시도해주세요\"}"))),
            @ApiResponse(responseCode = "500", description = "회원가입에 오류가 발생하였습니다. 다시 시도해주세요.",
                    content = @Content(schema = @Schema(implementation = sendVerificationCodeDto.ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"errorCode\": \"500\", \"errorMessage\": \"회원가입에 오류가 발생하였습니다. 다시 시도해주세요.\"}")))
    })
    @PostMapping("/signup")
    public ResponseEntity<SignupDto.SignUpResponse> signup(@RequestBody SignupDto.SignupRequest request) {
        SignupDto.SignUpResponse response = authService.signUp(request);
        return ResponseEntity.ok().body(response);
    }
}
