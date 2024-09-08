package taka.takaspring.Member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import taka.takaspring.Member.dto.VerifyUserDto;
import taka.takaspring.Member.exception.ExpiredTokenException;
import taka.takaspring.Member.service.VerifyUserService;
import taka.takaspring.Membership.exception.UserEntityNotFoundException;
import taka.takaspring.common.Api;

@RestController
@RequestMapping("/api")
public class VerifyUserController {

    private final VerifyUserService verifyUserService;

    public VerifyUserController(VerifyUserService verifyUserService) {
        this.verifyUserService = verifyUserService;
    }

    @Operation(summary = "access token을 이용하여 회원 정보 확인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 정보 확인 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Api.class),
                            examples = @ExampleObject(value = "{\"status\": \"success\", \"message\": \"회원 정보 확인 성공\", \"data\": {\"id\": 1, \"email\": \"choki@example.com\", \"password\": \"myPassW0rd!\", \"name\": \"김초키\", \"major\": \"컴퓨터학과\", \"studentNum\": \"2019778899\", \"phoneNumber\": \"010-1234-5678\", \"profileImageUrl\": \"https://example.com/profile.jpg\"}, \"statusCode\": 200}"))),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 회원입니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Api.class),
                            examples = @ExampleObject(value = "{\"status\": \"fail\", \"message\": \"존재하지 않는 회원입니다.\", \"data\": null, \"statusCode\": 400}"))),
            @ApiResponse(responseCode = "401", description = "accessToken이 만료되었습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Api.class),
                            examples = @ExampleObject(value = "{\"status\": \"unauthorized\", \"message\": \"accessToken이 만료되었습니다.\", \"data\": null, \"statusCode\": 401}"))),
            @ApiResponse(responseCode = "500", description = "회원정보 가져오기에 실패하였습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Api.class),
                            examples = @ExampleObject(value = "{\"status\": \"error\", \"message\": \"회원정보 가져오기에 실패함\", \"data\": null, \"statusCode\": 500}")))
    })
    @PostMapping("/verify-user")
    public ResponseEntity<Api<?>> verifyUser(@RequestBody VerifyUserDto.Request request) {
        try {
            String accessToken = request.getAccessToken();
            VerifyUserDto.Response userInfo = verifyUserService.verifyUser(accessToken);
            return ResponseEntity.ok(Api.ok(userInfo, "회원 정보 확인 성공"));
        } catch (UserEntityNotFoundException e) {
            return ResponseEntity.status(400).body(Api.fail("존재하지 않는 회원입니다."));
        } catch (ExpiredTokenException e) {
            return ResponseEntity.status(401).body(Api.unauthorized("accessToken이 만료되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Api.error("회원정보 가져오기에 실패하였습니다."));
        }
    }
}