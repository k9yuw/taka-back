package taka.takaspring.Member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taka.takaspring.Member.dto.VerifyCodeDto;
import taka.takaspring.Member.dto.sendVerificationCodeDto;
import taka.takaspring.Member.dto.SignupDto;
import taka.takaspring.Member.exception.EmailDuplicateException;
import taka.takaspring.Member.exception.InvalidUserInfoException;
import taka.takaspring.Member.exception.InvalidVerificationCodeException;
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

    @PostMapping("/signup/send-verification-code")
    @Operation(summary = "이메일에 회원가입 인증번호 전송")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "코드 전송에 성공하였습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Api.class),
                            examples = @ExampleObject(value = "{\"status\": \"success\", \"message\": \"코드 전송에 성공하였습니다.\", \"data\": null, \"statusCode\": 200}")
                    )
            ),
            @ApiResponse(responseCode = "400", description = "이미 회원가입 되어 있는 이메일입니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Api.class),
                            examples = @ExampleObject(value = "{\"status\": \"fail\", \"message\": \"이미 회원가입 되어 있는 이메일입니다.\", \"data\": null, \"statusCode\": 400}")
                    )
            ),
            @ApiResponse(responseCode = "500", description = "코드 전송 시 오류가 발생했습니다. 다시 시도해주세요",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Api.class),
                            examples = @ExampleObject(value = "{\"status\": \"error\", \"message\": \"코드 전송 시 오류가 발생했습니다. 다시 시도해주세요.\", \"data\": null, \"statusCode\": 500}")
                    )
            )
    })
    public ResponseEntity<Api<?>> sendVerificationCode(@RequestBody sendVerificationCodeDto.Request request) {
        try {
            authService.sendVerificationCode(request.getEmail());
            return ResponseEntity.ok(Api.ok(null, "코드 전송에 성공하였습니다."));
        } catch (EmailDuplicateException e) {
            return ResponseEntity.status(400).body(Api.fail("이미 회원가입 되어 있는 이메일입니다."));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Api.error("코드 전송 시 오류가 발생했습니다. 다시 시도해주세요"));
        }
    }




    @PostMapping("/signup/verify-code")
    @Operation(summary = "알맞은 인증번호인지 검증")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "코드 검증에 성공하였습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Api.class),
                            examples = @ExampleObject(value = "{\"status\": \"success\", \"message\": \"코드 검증에 성공하였습니다.\", \"data\": null, \"statusCode\": 200}")
                    )
            ),
            @ApiResponse(responseCode = "400", description = "코드가 유효하지 않습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Api.class),
                            examples = @ExampleObject(value = "{\"status\": \"fail\", \"message\": \"코드가 유효하지 않습니다.\", \"data\": null, \"statusCode\": 400}")
                    )
            ),
            @ApiResponse(responseCode = "500", description = "코드 검증 중 오류가 발생하였습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Api.class),
                            examples = @ExampleObject(value = "{\"status\": \"error\", \"message\": \"코드 검증 중 오류가 발생하였습니다. 다시 시도해주세요.\", \"data\": null, \"statusCode\": 500}")
                    )
            )
    })
    public ResponseEntity<Api<?>> verifyCode(@RequestBody VerifyCodeDto.VerifyCodeRequest request) {
        try {
            authService.verifyCode(request.getEmail(), request.getVerificationCode());
            return ResponseEntity.ok(Api.ok(null, "코드 검증에 성공하였습니다."));
        } catch (InvalidVerificationCodeException e) {
            return ResponseEntity.status(400).body(Api.fail("코드가 유효하지 않습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Api.error("코드 검증 중 오류가 발생하였습니다. 다시 시도해주세요."));
        }
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입이 성공하였습니다.",
                    content = @Content(schema = @Schema(implementation = Api.class),
                            examples = @ExampleObject(value = "{\"status\": \"success\", \"message\": \"회원가입이 성공하였습니다.\", \"data\": {\"id\": \"123\", \"email\": \"user@example.com\"}, \"statusCode\": 200}")
                    )
            ),
            @ApiResponse(responseCode = "400", description = "회원정보 기입 시 오류가 발생하였습니다. 다시 시도해주세요.",
                    content = @Content(schema = @Schema(implementation = Api.class),
                            examples = @ExampleObject(value = "{\"status\": \"fail\", \"message\": \"회원정보 기입 오류가 발생하였습니다. 다시 시도해주세요\", \"data\": null, \"statusCode\": 400}")
                    )
            ),
            @ApiResponse(responseCode = "500", description = "회원가입에 오류가 발생하였습니다. 다시 시도해주세요.",
                    content = @Content(schema = @Schema(implementation = Api.class),
                            examples = @ExampleObject(value = "{\"status\": \"error\", \"message\": \"회원가입에 오류가 발생하였습니다. 다시 시도해주세요.\", \"data\": null, \"statusCode\": 500}")
                    )
            )
    })
    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    public ResponseEntity<Api<SignupDto.SignUpResponse>> signup(@RequestBody SignupDto.SignupRequest request) {
        try {
            SignupDto.SignUpResponse response = authService.signUp(request);
            return ResponseEntity.ok(Api.ok(response, "회원가입이 성공하였습니다."));
        } catch (InvalidUserInfoException e) {
            return ResponseEntity.status(400).body(Api.fail("회원정보 기입 시 오류가 발생하였습니다. 다시 시도해주세요."));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Api.error("회원가입에 오류가 발생하였습니다. 다시 시도해주세요."));
        }
    }
}
