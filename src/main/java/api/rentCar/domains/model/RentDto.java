package api.rentCar.domains.model;

import api.rentCar.domains.entity.Rent;
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
    private int valueWeekday;
    private int valueWeekenday;
    private LocalDate dateWithdrawal;
    private LocalDate dateDelivery;
    private int rentAmount;
    private UserDto user;


    public RentDto(Rent rent) {
        this.id = rent.getId();
        this.vehicleDto = new VehicleDto();
        this.valueWeekday = rent.getValueWeekday();
        this.valueWeekenday = rent.getValueWeekenday();
        this.dateWithdrawal = rent.getDateWithdrawal();
        this.dateDelivery = rent.getDateDelivery();
        this.rentAmount = rent.getRentAmount();
    }
}
