package taka.takaspring.Rental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taka.takaspring.Rental.dto.RentalDto;
import taka.takaspring.Rental.dto.RentalItemManageDto;
import taka.takaspring.Rental.dto.RentalRecordDto;
import taka.takaspring.Rental.dto.ReturnDto;
import taka.takaspring.Rental.service.RentalService;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    @Autowired
    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    // 대여 가능한 물품 목록 조회
    @GetMapping("/{organization_id}")
    public ResponseEntity<List<RentalItemManageDto.RentalItemManageResponse>> getRentalItem(@PathVariable("organization_id") Long orgId){
        List<RentalItemManageDto.RentalItemManageResponse> itemList = rentalService.getRentalItemListByOrgId(orgId);
        return ResponseEntity.ok().body(itemList);
    }

    // 대여
    @PostMapping("/{organization_id}/{item_id}")
    public ResponseEntity<RentalDto.RentalResponse> rentItem(@RequestBody RentalDto.RentalRequest request,
                                                             @PathVariable("organization_id") Long orgId,
                                                             @PathVariable("item_id") Long itemId) {
        RentalDto.RentalResponse response = rentalService.rentItem(request);
        return ResponseEntity.ok().body(response);
    }

    // 반납
    @PostMapping("/{rental_record_id}/return")
    public ResponseEntity<ReturnDto.ReturnResponse> returnItem(@RequestBody ReturnDto.ReturnRequest request,
                                                               @PathVariable("rental_record_id") Long rentalRecordId) {
        ReturnDto.ReturnResponse response = rentalService.returnItem(request);
        return ResponseEntity.ok().body(response);
    }

    // 대여 기록 조회
    @GetMapping("/{user_id}/{rental_record_id}")
    public ResponseEntity<RentalDto.RentalResponse> getRentalRecord(@PathVariable("user_id") Long userId,
                                                           @PathVariable("rental_record_id") Long rentalRecordId) {
        RentalDto.RentalResponse response = rentalService.getRentalRecords(rentalRecordId, userId);
        return ResponseEntity.ok().body(response);
    }

    // 현재 대여중인 물품 조회
    @GetMapping("/current/{user_id}")
    public ResponseEntity<List<RentalDto.RentalResponse>> getCurrentRental(@PathVariable("user_id") Long userId) {
        List<RentalDto.RentalResponse> currentRentalList = rentalService.getCurrentRentalsByUserId(userId);
        return ResponseEntity.ok().body(currentRentalList);
    }
}