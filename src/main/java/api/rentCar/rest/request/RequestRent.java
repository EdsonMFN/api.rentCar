package api.rentCar.rest.request;

import api.rentCar.domains.model.VehicleDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RequestRent {

    private Long id;
    private VehicleDto vehicleDto;
    private Integer valueWeekday;
    private Integer valueWeekenday;
    private LocalDate dateWithdrawal;
    private LocalDate dateDelivery;
    private Integer rentAmount;
}
