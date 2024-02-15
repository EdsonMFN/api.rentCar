package api.rentCar.rest.response;

import api.rentCar.domains.model.VehicleDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseVehicle {

    private VehicleDto vehicleDto;
    private String msg;

    public ResponseVehicle(String msg) {
        this.msg = msg;
    }
    public ResponseVehicle(VehicleDto vehicleDto) {
        this.vehicleDto = vehicleDto;
    }

}
