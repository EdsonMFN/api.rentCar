package api.rentCar.rest.controller;

import api.rentCar.domains.model.PaymentDto;
import api.rentCar.rest.request.TypesToSearch;
import api.rentCar.rest.response.ResponsePayment;
import api.rentCar.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/rent/{idRent}")
    public ResponseEntity<ResponsePayment> createVehicle(@RequestBody PaymentDto request, @PathVariable Long idRent){
        ResponsePayment responsePayment = paymentService.createPayment(request, idRent);
        return ResponseEntity.ok(responsePayment);
    }
    @GetMapping
    public ResponseEntity<List<ResponsePayment>> listarAcademia(@RequestBody TypesToSearch filter){
        List<ResponsePayment> responsePaymentList = paymentService.filterPayment(filter);
        return ResponseEntity.ok(responsePaymentList);
    }
    @DeleteMapping("/{idPayment}")
    public ResponseEntity<?> deleteVehicle(@PathVariable Long idPayment){
        paymentService.delete(idPayment);
        return ResponseEntity.noContent().build();
    }
}
