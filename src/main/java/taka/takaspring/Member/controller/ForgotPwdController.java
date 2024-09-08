package taka.takaspring.Member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import taka.takaspring.Member.dto.ForgotPwdDto;
import taka.takaspring.Member.dto.VerifyCodeDto;
import taka.takaspring.Member.dto.sendVerificationCodeDto;
import taka.takaspring.Member.exception.InvalidVerificationCodeException;
import taka.takaspring.Member.exception.VerificationCodeSendingFailureException;
import taka.takaspring.Member.service.AuthService;
import taka.takaspring.Member.service.ForgotPwdService;
import taka.takaspring.Membership.exception.UserEntityNotFoundException;
import taka.takaspring.common.Api;

@RestController
@RequestMapping("/api/auth/forgot-password")
public class ForgotPwdController {

    private final ForgotPwdService forgotPwdService;
    private static final Logger logger = LoggerFactory.getLogger(ForgotPwdController.class);

    public ForgotPwdController(ForgotPwdService forgotPwdService) {
        this.forgotPwdService = forgotPwdService;
    }

    @Operation(summary = "비밀번호 찾기 시 이메일로 인증번호 전송")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증 코드가 이메일로 전송되었습니다.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Api.class),
                            examples = @ExampleObject(value = "{\"status\": \"success\", \"message\": \"인증 코드가 이메일로 전송되었습니다.\", \"data\": null, \"statusCode\": 200}"))),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 회원입니다.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Api.class),
                            examples = @ExampleObject(value = "{\"status\": \"fail\", \"message\": \"존재하지 않는 회원입니다.\", \"data\": null, \"statusCode\": 400}"))),
            @ApiResponse(responseCode = "500", description = "인증 코드 전송 중 오류가 발생했습니다.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Api.class),
                            examples = @ExampleObject(value = "{\"status\": \"error\", \"message\": \"인증 코드 전송 중 오류가 발생했습니다.\", \"data\": null, \"statusCode\": 500}")))
    })
    @PostMapping("/send-verification-code")
    public ResponseEntity<Api<?>> sendVerificationCode(@RequestBody sendVerificationCodeDto.Request request) {
        try {
            forgotPwdService.sendVerificationCode(request.getEmail());
            return ResponseEntity.ok(Api.ok(null, "인증 코드가 이메일로 전송되었습니다."));
        } catch (UserEntityNotFoundException e) {
            return ResponseEntity.status(400).body(Api.fail("존재하지 않는 회원입니다."));
        } catch (VerificationCodeSendingFailureException e) {
            return ResponseEntity.status(500).body(Api.error("인증 코드 전송 중 오류가 발생했습니다."));
        }
    }

    @Operation(summary = "비밀번호 찾기 시 맞는 인증번호인지 검증")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증 코드 검증에 성공하였습니다.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Api.class),
                            examples = @ExampleObject(value = "{\"status\": \"success\", \"message\": \"인증 코드 검증에 성공하였습니다.\", \"data\": null, \"statusCode\": 200}"))),
            @ApiResponse(responseCode = "400", description = "인증 코드 검증에 실패하였습니다.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Api.class),
                            examples = @ExampleObject(value = "{\"status\": \"fail\", \"message\": \"인증 코드 검증에 실패하였습니다.\", \"data\": null, \"statusCode\": 400}"))),
            @ApiResponse(responseCode = "500", description = "인증 코드 검증 중 오류가 발생하였습니다.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Api.class),
                            examples = @ExampleObject(value = "{\"status\": \"error\", \"message\": \"인증 코드 검증 중 오류가 발생하였습니다.\", \"data\": null, \"statusCode\": 500}")))
    })
    @PostMapping("/verify-code")
    public ResponseEntity<Api<?>> verifyCode(@RequestBody VerifyCodeDto.VerifyCodeRequest request) {
        try {
            boolean verified = forgotPwdService.verifyCode(request.getEmail(), request.getVerificationCode());
            if (verified) {
                return ResponseEntity.ok(Api.ok(null, "인증 코드 검증에 성공하였습니다."));
            } else {
                return ResponseEntity.status(400).body(Api.fail("인증 코드 검증에 실패하였습니다."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Api.error("인증 코드 검증 중 오류가 발생하였습니다."));
        }
    }

    @Operation(summary = "이메일로 임시 비밀번호 전송")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "임시 비밀번호가 이메일로 전송되었습니다.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Api.class),
                            examples = @ExampleObject(value = "{\"status\": \"success\", \"message\": \"임시 비밀번호가 이메일로 전송되었습니다.\", \"data\": {\"email\": \"user@example.com\"}, \"statusCode\": 200}"))),
            @ApiResponse(responseCode = "400", description = "해당 이메일을 가진 사용자가 존재하지 않습니다.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Api.class),
                            examples = @ExampleObject(value = "{\"status\": \"fail\", \"message\": \"해당 이메일을 가진 사용자가 존재하지 않습니다.\", \"data\": null, \"statusCode\": 400}"))),
            @ApiResponse(responseCode = "500", description = "임시 비밀번호 발송 중 오류가 발생하였습니다.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Api.class),
                            examples = @ExampleObject(value = "{\"status\": \"error\", \"message\": \"임시 비밀번호 발송 중 오류가 발생하였습니다.\", \"data\": null, \"statusCode\": 500}")))
    })
    @PostMapping("/get-temporary-password")
    public ResponseEntity<Api<?>> forgotPassword(@RequestBody @Valid ForgotPwdDto.ForgotPwdRequest requestDto) {
        try {
            String email = forgotPwdService.sendTemporaryPassword(requestDto.getEmail());
            ForgotPwdDto.ForgotPwdResponse response = ForgotPwdDto.ForgotPwdResponse.builder()
                    .email(email)
                    .build();
            return ResponseEntity.ok(Api.ok(response, "임시 비밀번호가 이메일로 전송되었습니다."));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(400).body(Api.fail("해당 이메일을 가진 사용자가 존재하지 않습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Api.error("임시 비밀번호 발송 중 오류가 발생하였습니다."));
        }
    }
}
