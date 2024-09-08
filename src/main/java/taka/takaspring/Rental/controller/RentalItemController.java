package taka.takaspring.Rental.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import taka.takaspring.Rental.dto.CategoryDto;
import taka.takaspring.Rental.dto.RentalItemDto;
import taka.takaspring.Rental.exception.RentalItemNotFoundException;
import taka.takaspring.Rental.service.RentalItemService;
import taka.takaspring.common.Api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/items")
public class RentalItemController {

    private final RentalItemService rentalItemService;

    public RentalItemController(RentalItemService rentalItemService) {
        this.rentalItemService = rentalItemService;
    }

    @Operation(summary = "단체의 대여 물품 목록 및 카테고리 조회", description = "특정 단체의 대여 가능한 물품 목록과 카테고리들을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "id: [orgId]의 대여 물품 및 카테고리 조회 성공",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{"
                                    + "\"status\": \"success\", "
                                    + "\"message\": \"단체의 대여 물품 및 카테고리 조회 성공\", "
                                    + "\"data\": {"
                                    + "\"categories\": ["
                                    + "{"
                                    + "\"id\": 1, "
                                    + "\"name\": \"계산기\", "
                                    + "\"description\": \"공학용 계산기\""
                                    + "},"
                                    + "{"
                                    + "\"id\": 2, "
                                    + "\"name\": \"실험복\", "
                                    + "\"description\": \"S:2개, M: 3개, L:2개\""
                                    + "},"
                                    + "{"
                                    + "\"id\": 3, "
                                    + "\"name\": \"전공책\", "
                                    + "\"description\": \"식품미생물학, 식품화학\""
                                    + "}"
                                    + "], "
                                    + "\"items\": ["
                                    + "{"
                                    + "\"id\": 1, "
                                    + "\"orgId\": 1, "
                                    + "\"categoryId\": 1, "
                                    + "\"name\": \"계산기1\", "
                                    + "\"isAvailable\": true, "
                                    + "\"rentalPeriod\": 7, "
                                    + "\"photos\": [\"calculator1.jpg\", \"calculator2.jpg\"], "
                                    + "\"description\": \"공학용 계산기입니다.\""
                                    + "}, "
                                    + "{"
                                    + "\"id\": 2, "
                                    + "\"orgId\": 1, "
                                    + "\"categoryId\": 2, "
                                    + "\"name\": \"실험복M1\", "
                                    + "\"isAvailable\": false, "
                                    + "\"rentalPeriod\": 14, "
                                    + "\"photos\": [\"labcoat1.jpg\", \"labcoat2.jpg\"], "
                                    + "\"description\": \"실험 시 필요한 실험복입니다.\""
                                    + "}"
                                    + "]"
                                    + "},"
                                    + "\"statusCode\": 200"
                                    + "}"))
            ),
            @ApiResponse(responseCode = "500", description = "id: [orgId]의 대여 물품 및 카테고리 조회 실패",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{organization_id}")
    public ResponseEntity<Api<Map<String, Object>>> getRentalItemsAndCategories(
            @PathVariable("organization_id") Long orgId) {
        try {
            // 해당 단체에 해당하는 카테고리, 아이템 목록 조회
            List<CategoryDto> categories = rentalItemService.getCategoryListByOrgId(orgId);
            List<RentalItemDto.RentalItemResponse> items = rentalItemService.getRentalItemListByOrgId(orgId);

            // 두 리스트를 Map에 담아서 반환
            Map<String, Object> response = new HashMap<>();
            response.put("categories", categories);
            response.put("items", items);

            String successMessage = String.format("orgId: [%d]의 카테고리 및 아이템 조회 성공", orgId);
            return ResponseEntity.ok(Api.ok(response, successMessage));
        } catch (Exception e) {
            String errorMessage = String.format("orgId: [%d]의 카테고리 및 아이템 조회 실패", orgId);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Api.error(errorMessage));
        }
    }
}



