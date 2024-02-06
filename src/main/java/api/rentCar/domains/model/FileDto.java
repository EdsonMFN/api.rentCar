package api.rentCar.domains.model;

import api.rentCar.enums.TypeFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDto {

    private Long id;
    private TypeFile typeFile;
    private String Base64;
}
