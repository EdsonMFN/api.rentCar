package api.rentCar.rest.request;

import api.rentCar.entity.Vehicle;
import api.rentCar.rest.dto.ModelDto;
import api.rentCar.rest.dto.VehicleDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestVehicle {

    private Long id;
    private String model;
    private String plate;
}
