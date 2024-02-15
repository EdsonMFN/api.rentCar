package api.rentCar.domains.model;

import api.rentCar.domains.entity.Vehicle;
import api.rentCar.enums.ColorVehicle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDto {

    private Long id;
    private ModelDto modelDto;
    private String plate;
    private ColorVehicle color;
    private int mileage;
    private String photo;

    public VehicleDto(Vehicle vehicle) {
        this.id = vehicle.getId();
        this.modelDto = new ModelDto();
        this.plate = vehicle.getPlate();
        this.color = vehicle.getColor();
        this.mileage = vehicle.getMileage();
    }
}
