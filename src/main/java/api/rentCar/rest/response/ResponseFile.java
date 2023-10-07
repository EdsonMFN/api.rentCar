package api.rentCar.rest.response;

import api.rentCar.domains.model.FileDto;
import lombok.Data;

@Data
public class ResponseFile {

    private FileDto fileDto;

    public ResponseFile(FileDto fileDto) {
        this.fileDto = fileDto;
    }
}
