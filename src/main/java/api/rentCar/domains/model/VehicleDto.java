package api.rentCar.domains.model;

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

}
