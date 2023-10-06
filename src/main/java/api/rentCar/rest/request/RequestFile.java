package api.rentCar.rest.request;

import api.rentCar.enums.TypeFile;
import lombok.Data;

@Data
public class RequestFile {

    private TypeFile typeFile;
}
