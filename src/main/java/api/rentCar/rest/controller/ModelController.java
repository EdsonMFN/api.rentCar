package api.rentCar.rest.controller;

import api.rentCar.domains.model.ModelDto;
import api.rentCar.rest.request.TypesToSearch;
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
    public ResponseEntity<ResponseModel> createVehicle(@RequestBody ModelDto request){
        ResponseModel responseModel = modelService.createModel(request);
        return ResponseEntity.ok(responseModel);
    }
    @GetMapping
    public ResponseEntity<List<ResponseModel>> listarAcademia(@RequestBody TypesToSearch filter){
        List<ResponseModel> responseVehicles = modelService.filterModel(filter);
        return ResponseEntity.ok(responseVehicles);
    }
    @PutMapping("/{idModel}")
    public ResponseEntity<ResponseModel> updateModel(@RequestBody ModelDto request, @PathVariable Long idModel){
        ResponseModel responseModel = modelService.updateModel(request,idModel);
        return ResponseEntity.ok(responseModel);
    }
    @DeleteMapping("/{idModel}")
    public ResponseEntity<?> deleteVehicle(@PathVariable Long idModel){

        return ResponseEntity.noContent().build();
    }
}
