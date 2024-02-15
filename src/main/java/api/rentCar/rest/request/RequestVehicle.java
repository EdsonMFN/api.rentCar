package api.rentCar.rest.request;

import api.rentCar.enums.ColorVehicle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RequestVehicle {

    private Long id;
    private String plate;
    private ColorVehicle color;
    private int mileage;
    private String photo;
}
