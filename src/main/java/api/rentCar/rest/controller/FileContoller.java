package api.rentCar.rest.controller;

import api.rentCar.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/file")
@RestController
public class FileContoller {

    @Autowired
    private FileService fileService;
}
