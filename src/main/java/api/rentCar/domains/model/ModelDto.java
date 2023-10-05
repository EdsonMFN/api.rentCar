package api.rentCar.domains.model;

import api.rentCar.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModelDto {

    private Long id;
    private String model;
    private Integer modelYear;
    private String fabricator;
    private Category category;

}
