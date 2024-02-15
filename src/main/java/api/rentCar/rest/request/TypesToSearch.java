package api.rentCar.rest.request;

import api.rentCar.enums.Category;
import api.rentCar.enums.ColorVehicle;
import api.rentCar.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypesToSearch {

    private Boolean disabled;
    private String name;
    private String cpf;
    private String plate;
    private String model;
    private ColorVehicle color;
    private Category category;
    private LocalDate date;
    private Status status;
    private Long id;

}
