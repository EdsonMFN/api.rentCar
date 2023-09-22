package api.rentCar.rest.controller;

import api.rentCar.rest.request.RequestVehicle;
import api.rentCar.rest.response.ResponseVehicle;
import api.rentCar.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/{nameModel}")
    public ResponseEntity<ResponseVehicle> createVehicle(@RequestBody RequestVehicle requestVehicle, @PathVariable String nameModel){
        ResponseVehicle responseVehicle = vehicleService.createVehicle(requestVehicle,nameModel);
        return ResponseEntity.ok(responseVehicle);
    }
    @GetMapping
    public ResponseEntity<List<ResponseVehicle>> listarAcademia(){
        List<ResponseVehicle> responseVehicles = vehicleService.listVehicle();
        return ResponseEntity.ok(responseVehicles);
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ResponseVehicle>> searchByVehicle(@PathVariable Integer category){
        List<ResponseVehicle> responseVehicles = vehicleService.searchByCategory(category);
        return ResponseEntity.ok(responseVehicles);
    }
    @DeleteMapping("/{idVehicle}")
    public ResponseEntity<ResponseVehicle> deleteVehicle(@PathVariable Long idVehicle){
        ResponseVehicle responseVehicle = vehicleService.delete(idVehicle);
        return ResponseEntity.noContent().build();
    }
}
