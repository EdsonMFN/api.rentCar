package api.rentCar.service;

import api.rentCar.File.FileXlsx;
import api.rentCar.domains.entity.File;
import api.rentCar.domains.entity.Rent;
import api.rentCar.domains.repository.RepositoryRent;
import api.rentCar.enums.TypeFile;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FileService {
    @Autowired
    private RepositoryRent repositoryRent;

//    public ResponseRent createFileXlsx(RequestFile requestFile){
//
//    }
    private String base64(File file, List<Rent> rents){
        String base64Xlsx= null;
        if (file.getTypeFile().equals(TypeFile.XLSX)){
            FileXlsx fileXlsx = new FileXlsx();
            fileXlsx.createFileXlsx("D:\\Edson\\projetos\\workout\\manipulacao_de_arquivos\\excel-java",rents);
            base64Xlsx = fileXlsx.base64Exel("D:\\Edson\\projetos\\workout\\manipulacao_de_arquivos\\excel-java");
        }
        return base64Xlsx;
    }
}
