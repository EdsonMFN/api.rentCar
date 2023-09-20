package api.rentCar.rest.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestVehicle {

    private Long id;
    private String model;
    private String plate;
}
