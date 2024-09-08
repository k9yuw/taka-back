package taka.takaspring.Organization.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import taka.takaspring.Organization.dto.OrgDto;
import taka.takaspring.Organization.service.OrgService;
import taka.takaspring.common.Api;

import java.util.List;

@RestController
@RequestMapping("/api/orgs")
public class OrgController {

    private final OrgService orgService;

    public OrgController(OrgService orgService) {
        this.orgService = orgService;
    }

    @Operation(summary = "전체 단체목록 조회", description = "전체 단체목록을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전체 단체목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrgDto.OrgResponse.class),
                            examples = @ExampleObject(value = "{"
                                    + "\"status\": \"success\", "
                                    + "\"message\": \"전체 단체목록 조회 성공\", "
                                    + "\"data\": ["
                                    + "{"
                                    + "\"orgName\": \"FLIP\", "
                                    + "\"department\": \"애기능동아리연합회\", "
                                    + "\"orgDescription\": \"세계 최고 스노우보드 동아리입니다.\""
                                    + "}, "
                                    + "{"
                                    + "\"orgName\": \"식품공학과\", "
                                    + "\"department\": \"생명과학대학\", "
                                    + "\"orgDescription\": \"생명과학대학 식품공학과입니다. 전진식공~\""
                                    + "}"
                                    + "], "
                                    + "\"statusCode\": 200"
                                    + "}"))),
            @ApiResponse(responseCode = "500", description = "전체 단체목록 조회 실패",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{"
                                    + "\"status\": \"error\", "
                                    + "\"message\": \"전체 단체목록 조회 실패\", "
                                    + "\"data\": null, "
                                    + "\"statusCode\": 500"
                                    + "}")))
    })
    @GetMapping
    public ResponseEntity<Api<List<OrgDto.OrgResponse>>> getAllOrgs() {
        try {
            List<OrgDto.OrgResponse> orgList = orgService.getAllOrgs();
            String successMessage = "전체 단체목록 조회 성공";
            return ResponseEntity.ok(Api.ok(orgList, successMessage));
        } catch (Exception e) {
            String errorMessage = "전체 단체목록 조회 실패";
            return ResponseEntity.status(500).body(Api.error(errorMessage));
        }
    }
}