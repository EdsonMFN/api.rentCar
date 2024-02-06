package api.rentCar.rest.response;

import api.rentCar.domains.model.RentDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseRent {

    private RentDto rentDto;
    private String msg;

    public ResponseRent(String msg) {
        this.msg = msg;
    }

    public ResponseRent(RentDto rentDto) {
        this.rentDto = rentDto;
    }

}
