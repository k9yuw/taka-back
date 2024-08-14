package taka.takaspring.Rental.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taka.takaspring.Rental.dto.RentalCategoryDto;
import taka.takaspring.Rental.service.RentalCategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/{organization_id}/category")
public class RentalCategoryController {

    private final RentalCategoryService rentalCategoryService;

    public RentalCategoryController(RentalCategoryService rentalCategoryService) {
        this.rentalCategoryService = rentalCategoryService;
    }

    @PostMapping
    public ResponseEntity<RentalCategoryDto.CategoryResponse> createCategory(
            @PathVariable("organization_id") Long orgId,
            @RequestBody RentalCategoryDto.CategoryRequest request) {

        RentalCategoryDto.CategoryResponse response = rentalCategoryService.createCategory(orgId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<RentalCategoryDto.CategoryResponse>> getCategoriesByOrgId(
            @PathVariable("organization_id") Long orgId) {

        List<RentalCategoryDto.CategoryResponse> response = rentalCategoryService.getCategoriesByOrgId(orgId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{category_id}")
    public ResponseEntity<RentalCategoryDto.CategoryResponse> updateCategory(
            @PathVariable("organization_id") Long orgId,
            @PathVariable("category_id") Long categoryId,
            @RequestBody RentalCategoryDto.CategoryRequest request) {

        RentalCategoryDto.CategoryResponse response = rentalCategoryService.updateCategory(orgId, categoryId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{category_id}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable("organization_id") Long orgId,
            @PathVariable("category_id") Long categoryId) {

        rentalCategoryService.deleteCategory(orgId, categoryId);
        return ResponseEntity.noContent().build();
    }
}

