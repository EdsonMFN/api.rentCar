package api.rentCar.rest.request;

import api.rentCar.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestModel {

    private Long id;
    private String model;
    private Integer modelYear;
    private String fabricator;
    private Category category;
}
