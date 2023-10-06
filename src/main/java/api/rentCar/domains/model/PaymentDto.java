package api.rentCar.domains.model;

import api.rentCar.enums.Status;
import api.rentCar.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
public class PaymentDto {

    private Long id;
    private RentDto rentDto;
    private LocalDate payday;
    private Type type;
    private Status status;
}
