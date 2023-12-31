package api.rentCar.domains.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RentDto {

    private Long id;
    private VehicleDto vehicleDto;
    private Integer valueWeekday;
    private Integer valueWeekenday;
    private String dateWithdrawal;
    private String dateDelivery;
    private Integer rentAmount;

}
