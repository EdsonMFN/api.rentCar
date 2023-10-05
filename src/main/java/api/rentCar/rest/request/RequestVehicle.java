package api.rentCar.rest.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RequestVehicle {

    private Long id;
    private String plate;
}
