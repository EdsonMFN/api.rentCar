package api.rentCar.rest.dto;

import api.rentCar.entity.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RentDto {

    private Long id;
    private VehicleDto vehicleDto;
    private Integer valueWeekday;
    private Integer valueWeekenday;
    private LocalDate dateWithdrawal;
    private LocalDate dateDelivery;
    private Integer rentAmount;

}
