package taka.takaspring.Rental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taka.takaspring.Organization.dto.UserOrgDto;
import taka.takaspring.Rental.db.RentalItemEntity;
import taka.takaspring.Rental.dto.RentalItemManageDto;
import taka.takaspring.Rental.service.RentalItemManageService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/{organization_id}/items")
public class RentalItemManageController {

    private final RentalItemManageService rentalItemManageService;

    @Autowired
    public RentalItemManageController(RentalItemManageService rentalItemManageService) {
        this.rentalItemManageService = rentalItemManageService;
    }

    @GetMapping
    public ResponseEntity<List<RentalItemManageDto.RentalItemManageResponse>> getRentalItem(@PathVariable("organization_id") Long orgId){
        List<RentalItemManageDto.RentalItemManageResponse> itemList = rentalItemManageService.getRentalItemsByOrgId(orgId);
        return ResponseEntity.ok(itemList);
    }

    @PostMapping
    public ResponseEntity<RentalItemManageDto.RentalItemManageResponse> addRentalItem(
            @PathVariable("organization_id") Long orgId,
            @RequestBody RentalItemManageDto.RentalItemManageRequest request) {
        RentalItemManageDto.RentalItemManageResponse response = rentalItemManageService.addRentalItem(orgId, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{item_id}")
    public ResponseEntity<RentalItemEntity> updateRentalItem(
            @PathVariable("organization_id") Long orgId,
            @PathVariable("item_id") Long itemId,
            @RequestBody RentalItemManageDto.RentalItemManageRequest request) {
        RentalItemEntity rentalItem = rentalItemManageService.updateRentalItem(orgId, itemId, request);
        return ResponseEntity.ok(rentalItem);
    }

    @DeleteMapping("/{item_id}")
    public ResponseEntity<Void> deleteRentalItem(
            @PathVariable("organization_id") Long orgId,
            @PathVariable("item_id") Long itemId) {
        rentalItemManageService.deleteRentalItem(orgId, itemId);
        return ResponseEntity.noContent().build();
    }
}
