package api.rentCar.rest.request;

import api.rentCar.rest.dto.ModelDto;
import api.rentCar.rest.dto.VehicleDto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestRent {

    private Long id;
    private VehicleDto vehicleDto;
    private Integer valueWeekday;
    private Integer valueWeekenday;
    private LocalDate dateWithdrawal;
    private LocalDate dateDelivery;
    private Integer rentAmount;
}
