package api.rentCar.rest.request;

import api.rentCar.domains.entity.Rent;
import api.rentCar.enums.Status;
import api.rentCar.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
public class RequestPayment {

    private Long id;
    private Rent rent;
    private LocalDate payday;
    private Type type;
    private Status status;
}
