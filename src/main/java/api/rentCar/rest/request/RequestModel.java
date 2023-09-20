package api.rentCar.rest.request;

import lombok.Data;

@Data
public class RequestModel {

    private Long id;
    private String model;
    private Integer modelYear;
    private String fabricator;
    private Integer category;
}