package api.rentCar.rest.response;


import api.rentCar.domains.model.ModelDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseModel {

    private ModelDto modelDto;
    private String msg;

    public ResponseModel(String msg) {
        this.msg = msg;
    }
    public ResponseModel(ModelDto modelDto) {
        this.modelDto = modelDto;
    }

}
