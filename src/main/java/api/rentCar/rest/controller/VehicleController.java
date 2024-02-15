package api.rentCar.rest.controller;

import api.rentCar.enums.Category;
import api.rentCar.rest.request.RequestVehicle;
import api.rentCar.rest.request.TypesToSearch;
import api.rentCar.rest.response.ResponseVehicle;
import api.rentCar.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/model/{idModel}")
    public ResponseEntity<ResponseVehicle> createVehicle(@RequestBody RequestVehicle requestVehicle, @PathVariable Long idModel){
        ResponseVehicle responseVehicle = vehicleService.createVehicle(requestVehicle,idModel);
        return ResponseEntity.status(HttpStatus.OK).body(responseVehicle);
    }
    @GetMapping
    public ResponseEntity<List<ResponseVehicle>> listarAcademia(@RequestBody TypesToSearch file){
        List<ResponseVehicle> responseVehicles = vehicleService.filterVehicle(file);
        return ResponseEntity.ok(responseVehicles);
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ResponseVehicle>> searchByVehicle(@PathVariable Category category){
        List<ResponseVehicle> responseVehicles = vehicleService.searchByCategory(category);
        return ResponseEntity.status(HttpStatus.OK).body(responseVehicles);
    }
    @DeleteMapping("/{idVehicle}")
    public ResponseEntity<?> deleteVehicle(@PathVariable Long idVehicle){
        vehicleService.delete(idVehicle);
        return ResponseEntity.noContent().build();
    }
}
