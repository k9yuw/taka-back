package taka.takaspring.Rental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taka.takaspring.Rental.dto.RentalDto;
import taka.takaspring.Rental.service.RentalService;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    @Autowired
    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping("/{organization_id}/{item_id}")
    public ResponseEntity<RentalDto.RentalResponse> rentItem(@RequestBody RentalDto.RentalRequest request,
                                                             @PathVariable("organization_id") Long orgId,
                                                             @PathVariable("item_id") Long itemId) {
        RentalDto.RentalResponse response = rentalService.rentItem(request);
        return ResponseEntity.ok(response);
    }


}