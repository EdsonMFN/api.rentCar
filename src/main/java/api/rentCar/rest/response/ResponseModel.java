package api.rentCar.rest.response;


import api.rentCar.domains.model.ModelDto;
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
