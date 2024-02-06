package api.rentCar.rest.controller;

import api.rentCar.rest.request.RequestModel;
import api.rentCar.rest.response.ResponseModel;
import api.rentCar.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/model")
public class ModelController {
    @Autowired
    private ModelService modelService;

    @PostMapping
    public ResponseEntity<ResponseModel> createVehicle(@RequestBody RequestModel requestModel){
        ResponseModel responseModel = modelService.createValueVehicle(requestModel);
        return ResponseEntity.ok(responseModel);
    }
    @GetMapping
    public ResponseEntity<List<ResponseModel>> listarAcademia(){
        List<ResponseModel> responseVehicles = modelService.listVehicle();
        return ResponseEntity.ok(responseVehicles);
    }
    @PutMapping("/{idModel}")
    public ResponseEntity<ResponseModel> updateModel(@RequestBody RequestModel requestModel, @PathVariable Long idModel){
        ResponseModel responseModel = modelService.updateModel(requestModel,idModel);
        return ResponseEntity.ok(responseModel);
    }
    @DeleteMapping("/{idModel}")
    public ResponseEntity<?> deleteVehicle(@PathVariable Long idModel){

        return ResponseEntity.noContent().build();
    }
}
