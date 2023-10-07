package api.rentCar.domains.model;

import api.rentCar.enums.TypeFile;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileDto {

    private Long id;
    private TypeFile typeFile;
    private String Base64;
}
