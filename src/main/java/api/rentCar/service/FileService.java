package api.rentCar.service;

import api.rentCar.File.FileXlsx;
import api.rentCar.domains.entity.File;
import api.rentCar.domains.entity.Rent;
import api.rentCar.domains.model.FileDto;
import api.rentCar.domains.repository.RepositoryFile;
import api.rentCar.domains.repository.RepositoryRent;
import api.rentCar.enums.TypeFile;
import api.rentCar.rest.request.RequestFile;
import api.rentCar.rest.response.ResponseFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FileService {
    @Autowired
    private RepositoryRent repositoryRent;
    @Autowired
    private RepositoryFile repositoryFile;

    public ResponseFile createFileXlsx(RequestFile requestFile){
        List<Rent> rents = repositoryRent.findAll();

        File fileXlsx = new File();
        fileXlsx.setTypeFile(requestFile.getTypeFile());
        fileXlsx.setBase64(base64(fileXlsx,rents));
        repositoryFile.save(fileXlsx);

        return new ResponseFile(FileDto
                .builder()
                .id(fileXlsx.getId())
                .typeFile(fileXlsx.getTypeFile())
                .base64(fileXlsx.getBase64())
                .build());
    }
    private String base64(File file, List<Rent> rents){
        String base64Xlsx= null;
        if (file.getTypeFile().equals(TypeFile.XLSX)){
            FileXlsx fileXlsx = new FileXlsx();
            fileXlsx.createFileXlsx("D:\\Edson\\projetos\\workout\\manipulacao_de_arquivos\\excel-java\\pathRent.xlsx",rents);
            base64Xlsx = fileXlsx.base64Exel("D:\\Edson\\projetos\\workout\\manipulacao_de_arquivos\\excel-java\\pathRent.xlsx");
        }
        return base64Xlsx;
    }
}
