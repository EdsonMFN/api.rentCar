package api.rentCar.rest.controller;

import api.rentCar.rest.request.RequestFile;
import api.rentCar.rest.response.ResponseFile;
import api.rentCar.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/file")
@RestController
public class FileContoller {

    @Autowired(required=true)
    private FileService fileService;

    @PostMapping
    public ResponseEntity<ResponseFile> createFileXlsx(@RequestBody RequestFile requestFile){
        ResponseFile responseFile = fileService.createFileXlsx(requestFile);
        return ResponseEntity.ok(responseFile);
    }
}
