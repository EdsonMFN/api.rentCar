package api.rentCar.rest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModelDto {

    private Long id;
    private String model;
    private Integer modelYear;
    private String fabricator;
    private Integer category;

}
