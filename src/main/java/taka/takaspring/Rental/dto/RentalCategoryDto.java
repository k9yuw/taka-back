package taka.takaspring.Rental.dto;

import lombok.*;

public class RentalCategoryDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CategoryRequest {

        private String categoryName;
        private String description;

    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class CategoryResponse {

        private Long id;
        private String categoryName;
        private String description;
        private String orgName;

        public CategoryResponse(Long id, String categoryName, String description, String orgName) {
            this.id = id;
            this.categoryName = categoryName;
            this.description = description;
            this.orgName = orgName;
        }
    }
}
