package api.rentCar.rest.response;

import api.rentCar.domains.model.FileDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFile {

    private FileDto fileDto;
    private String msg;

    public ResponseFile(String msg) {
        this.msg = msg;
    }

    public ResponseFile(FileDto fileDto) {
        this.fileDto = fileDto;
    }
}
