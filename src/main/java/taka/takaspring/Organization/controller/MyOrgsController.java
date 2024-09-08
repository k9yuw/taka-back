package taka.takaspring.Organization.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taka.takaspring.Organization.dto.OrgDto;
import taka.takaspring.Organization.service.MyOrgsService;
import taka.takaspring.common.Api;

import java.util.List;


@RestController
@RequestMapping("/api")
public class MyOrgsController {

    private final MyOrgsService myOrgsService;

    public MyOrgsController(MyOrgsService myOrgsService) {
        this.myOrgsService = myOrgsService;
    }

    @Operation(summary = "특정 사용자가 속한 조직 조회", description = "사용자의 ID로 해당 사용자가 속한 조직들을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내가 속한 조직들 조회에 성공하였습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Api.class),
                            examples = @ExampleObject(value = "{\"status\": \"success\", \"message\": \"내가 속한 조직들 조회에 성공하였습니다.\", \"data\": [{\"orgName\": \"FLIP\", \"department\": \"애기능동아리연합회\", \"orgDescription\": \"스노우보드 동아리\"}], \"statusCode\": 200}"))),
            @ApiResponse(responseCode = "500", description = "내가 속한 조직들 조회에 실패하였습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Api.class),
                            examples = @ExampleObject(value = "{\"status\": \"error\", \"message\": \"내가 속한 조직들 조회에 실패하였습니다.\", \"data\": null, \"statusCode\": 500}")))
    })
    @GetMapping("/myOrgs/{userId}")
    public ResponseEntity<Api<?>> getMyOrganizations(@PathVariable Long userId) {
        try {
            List<OrgDto.OrgResponse> orgResponses = myOrgsService.getMyOrganizations(userId);
            return ResponseEntity.ok(Api.ok(orgResponses, "내가 속한 조직들 조회에 성공하였습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Api.error("내가 속한 조직들 조회에 실패하였습니다."));
        }
    }
}
