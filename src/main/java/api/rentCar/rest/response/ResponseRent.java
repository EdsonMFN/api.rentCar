package api.rentCar.rest.response;

import api.rentCar.rest.dto.RentDto;
import api.rentCar.rest.dto.VehicleDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseRent {

    private RentDto rentDto;

    public ResponseRent(RentDto rentDto) {
        this.rentDto = rentDto;
    }

}
