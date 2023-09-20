package api.rentCar.rest.response;

import api.rentCar.domains.model.VehicleDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseVehicle {

    private VehicleDto vehicleDto;

    public ResponseVehicle(VehicleDto vehicleDto) {
        this.vehicleDto = vehicleDto;
    }

}
