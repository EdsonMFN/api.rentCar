package api.rentCar.rest.controller;

import api.rentCar.rest.request.RequestRent;
import api.rentCar.rest.request.RequestVehicle;
import api.rentCar.rest.response.ResponseRent;
import api.rentCar.rest.response.ResponseVehicle;
import api.rentCar.service.RentService;
import api.rentCar.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rent")
public class RentController {

    @Autowired
    private RentService rentService;

    @PostMapping("/plate/{plate}")
    public ResponseEntity<ResponseRent> createVehicle(@RequestBody RequestRent requestRent, @PathVariable String plate){
        ResponseRent responseRent = rentService.createRent(requestRent, plate);
        return ResponseEntity.ok(responseRent);
    }
    @GetMapping
    public ResponseEntity<List<ResponseRent>> listarAcademia(){
        List<ResponseRent> responseRents = rentService.listVehicle();
        return ResponseEntity.ok(responseRents);
    }
    @DeleteMapping("/{idRent}")
    public ResponseEntity<ResponseRent> deleteVehicle(@PathVariable Long idRent){
        ResponseRent responseRent = rentService.delete(idRent);
        return ResponseEntity.noContent().build();
    }
}
