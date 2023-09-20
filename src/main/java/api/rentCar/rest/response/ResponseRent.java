package api.rentCar.rest.response;

import api.rentCar.domains.model.RentDto;
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
