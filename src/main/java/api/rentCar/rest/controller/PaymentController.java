package api.rentCar.rest.controller;

import api.rentCar.rest.request.RequestPayment;
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
    public ResponseEntity<ResponsePayment> createVehicle(@RequestBody RequestPayment requestPayment, @PathVariable Long idRent){
        ResponsePayment responsePayment = paymentService.createPayment(requestPayment, idRent);
        return ResponseEntity.ok(responsePayment);
    }
    @GetMapping
    public ResponseEntity<List<ResponsePayment>> listarAcademia(){
        List<ResponsePayment> responsePaymentList = paymentService.listPayment();
        return ResponseEntity.ok(responsePaymentList);
    }
    @DeleteMapping("/{idPayment}")
    public ResponseEntity<String> deleteVehicle(@PathVariable Long idPayment){
        String responsePayment = paymentService.delete(idPayment);
        return ResponseEntity.ok(responsePayment);
    }
}
