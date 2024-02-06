package api.rentCar.rest.controller;

import api.rentCar.rest.request.RequestRent;
import api.rentCar.rest.response.ResponseRent;
import api.rentCar.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        return ResponseEntity.status(HttpStatus.OK).body(responseRent);
    }
    @GetMapping
    public ResponseEntity<List<ResponseRent>> listarAcademia(){
        List<ResponseRent> responseRents = rentService.listVehicle();
        return ResponseEntity.ok(responseRents);
    }
    @DeleteMapping("/{idRent}")
    public ResponseEntity<?> deleteVehicle(@PathVariable Long idRent){
        rentService.delete(idRent);
        return ResponseEntity.noContent().build();
    }
}
