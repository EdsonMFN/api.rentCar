package api.rentCar.rest.response;


import api.rentCar.domains.model.PaymentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePayment {

    private PaymentDto paymentDto;
    private String msg;

    public ResponsePayment(String msg) {
        this.msg = msg;
    }
    public ResponsePayment(PaymentDto paymentDto) {
        this.paymentDto = paymentDto;
    }



}
