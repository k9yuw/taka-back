package taka.takaspring.Rental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import taka.takaspring.Rental.dto.RentalRecordDto;
import taka.takaspring.Rental.service.RentalRecordManageService;

import java.util.List;

@PreAuthorize("@accessControlService.isOrgAdmin(principal, #organization_id)")
@RestController
@RequestMapping("/api/admin/{organization_id}/rental_records")
public class RentalRecordManageController {

    private final RentalRecordManageService rentalRecordManageService;

    @Autowired
    public RentalRecordManageController(RentalRecordManageService rentalRecordManageService) {
        this.rentalRecordManageService = rentalRecordManageService;
    }

    // 해당 단체의 전체 대여기록 조회
    @GetMapping
    public ResponseEntity<List<RentalRecordDto.RentalRecordResponse>> getRentalRecordListByOrgId(@PathVariable("organization_id") Long orgId){
        List<RentalRecordDto.RentalRecordResponse> rentalRecordsByOrg = rentalRecordManageService.getRentalRecordsByOrgId(orgId);
        return ResponseEntity.ok().body(rentalRecordsByOrg);
    }

    // 해당 단체의 물품별 대여기록 조회
    @GetMapping("/{item_id}")
    public ResponseEntity<List<RentalRecordDto.RentalRecordResponse>>  getRentalRecordListByItemId(@PathVariable("organization_id") Long orgId, @PathVariable("item_id") Long itemId) {
        List<RentalRecordDto.RentalRecordResponse> rentalRecordsByItem = rentalRecordManageService.getRentalRecordsByItemId(itemId);
        return ResponseEntity.ok().body(rentalRecordsByItem);
    }

    // 해당 단체의 회원별 대여기록 조회
    @GetMapping("/{user_id}")
    public ResponseEntity<List<RentalRecordDto.RentalRecordResponse>>  getRentalRecordListByOrgIdAndUserId(@PathVariable("organization_id") Long orgId, @PathVariable("user_id") Long userId) {
        List<RentalRecordDto.RentalRecordResponse> rentalRecordsByUser = rentalRecordManageService.getRentalRecordsByOrgIdAndUserId(orgId, userId);
        return ResponseEntity.ok().body(rentalRecordsByUser);
    }

}
