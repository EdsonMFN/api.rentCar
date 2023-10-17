package api.rentCar.rest.response;


import api.rentCar.domains.model.PaymentDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponsePayment {

    private PaymentDto paymentDto;

    public ResponsePayment(PaymentDto paymentDto) {
        this.paymentDto = paymentDto;
    }
    public String msgDelet() {
        return "payment deleted successfully";
    }
    public String msgFailDelet() {
        return "Could not delete";
    }

}
