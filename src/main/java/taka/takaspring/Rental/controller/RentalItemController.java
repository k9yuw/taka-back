package taka.takaspring.Rental.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import taka.takaspring.Rental.dto.RentalItemDto;
import taka.takaspring.Rental.service.RentalItemService;
import taka.takaspring.common.Api;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class RentalItemController {

    private final RentalItemService rentalItemService;

    public RentalItemController(RentalItemService rentalItemService) {
        this.rentalItemService = rentalItemService;
    }

    @Operation(summary = "단체의 대여 물품 목록 조회", description = "특정 단체의 대여 가능한 물품 목록을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "id: [orgId]의 대여 물품 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RentalItemDto.RentalItemResponse.class),
                            examples = @ExampleObject(value = "{\"status\": \"success\", \"message\": \"단체의 대여 물품 조회 성공\", \"data\": [{\"id\": 1, \"orgId\": 1, \"name\": \"계산기1\", \"isAvailable\": true, \"rentalPeriod\": 7, \"photos\": [\"photo1.jpg\", \"photo2.jpg\"], \"category\": {\"id\": 1, \"name\": \"계산기\"}, \"descriptions\": [\"공학용 계산기입니다.\"]}], \"statusCode\": 200}"))),
            @ApiResponse(responseCode = "500", description = "id: [orgId]의 대여 물품 조회 실패",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{organization_id}")
    public ResponseEntity<Api<List<RentalItemDto.RentalItemResponse>>> getRentalItem(@PathVariable("organization_id") Long orgId) {
        try {
            List<RentalItemDto.RentalItemResponse> itemList = rentalItemService.getRentalItemListByOrgId(orgId);

            StringBuilder successMessage = new StringBuilder();
            successMessage.append("id: [").append(orgId).append("]의 items 조회 성공");

            return ResponseEntity.ok(Api.ok(itemList, successMessage.toString()));
        } catch (Exception e) {
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("id: [").append(orgId).append("]의 items 조회 실패");

            return ResponseEntity.status(500).body(Api.error(errorMessage.toString()));
        }
    }
}
