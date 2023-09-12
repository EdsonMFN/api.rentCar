package api.rentCar.rest.response;


import api.rentCar.rest.dto.ModelDto;
import api.rentCar.rest.dto.VehicleDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseModel {

    private ModelDto modelDto;

    public ResponseModel(ModelDto modelDto) {
        this.modelDto = modelDto;
    }

}
