package taka.takaspring.Rental.dto;


import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryDto {
    private Long id;
    private String name;
    private String description;
}
