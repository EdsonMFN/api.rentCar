package api.rentCar.controlles;

import api.rentCar.domains.entity.Model;
import api.rentCar.domains.entity.Payment;
import api.rentCar.domains.entity.Rent;
import api.rentCar.domains.entity.Vehicle;
import api.rentCar.domains.model.ModelDto;
import api.rentCar.domains.model.PaymentDto;
import api.rentCar.domains.model.RentDto;
import api.rentCar.domains.model.VehicleDto;
import api.rentCar.enums.Category;
import api.rentCar.enums.Status;
import api.rentCar.enums.Type;
import api.rentCar.rest.controller.PaymentController;
import api.rentCar.rest.request.RequestPayment;
import api.rentCar.rest.response.ResponsePayment;
import api.rentCar.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @MockBean
    private PaymentService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    private Rent rent;
    private RentDto rentDto;
    private Model model;
    private ModelDto modelDto;
    private Vehicle vehicle;
    private VehicleDto vehicleDto;
    private Payment payment;
    private PaymentDto paymentDto;
    private ResponsePayment responsePayment;
    private RequestPayment requestPayment;

    @BeforeEach
    void setup(){
        model = new Model(1L,"Cronos 1.2 8V Flex",2018,"Fiat", Category.SEDAN);
        vehicle = new Vehicle(1L,model,"rio2020");
        rent = new Rent(1L,vehicle, LocalDate.parse("2024-02-03"), LocalDate.parse("2024-02-04"),120,150,270);
        payment = new Payment(1L,rent,LocalDate.of(2024,2,6), Type.PIX, Status.PAID_OUT);
        modelDto = new ModelDto(1L,"Cronos 1.2 8V Flex",2018,"Fiat",Category.SEDAN);
        vehicleDto = new VehicleDto(1L,modelDto,"rio2020");
        rentDto = new RentDto(1L,vehicleDto,120,150,"2024-02-03", "2024-02-04",270);
        paymentDto = new PaymentDto(1L,rentDto,LocalDate.of(2024,2,6), Type.PIX, Status.PAID_OUT);
        responsePayment = new ResponsePayment(paymentDto);
        requestPayment = new RequestPayment(1L,rent,LocalDate.of(2024,2,6),Type.PIX, Status.PAID_OUT);

    }
    @Test
    @DisplayName("Salvar Payment")
    void salvarPayment() throws Exception {
        given(service.createPayment(any(RequestPayment.class),anyLong()))
                .willReturn(responsePayment);
        ResultActions response = mockMvc.perform(post("/payment/rent/{idRent}",rent.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestPayment))
        );
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.paymentDto.rentDto.id",is(responsePayment.getPaymentDto().getRentDto().getId().intValue())))
                .andExpect(jsonPath("$.paymentDto.rentDto.rentAmount",is(responsePayment.getPaymentDto().getRentDto().getRentAmount())))
                .andExpect(jsonPath("$.paymentDto.id",is(responsePayment.getPaymentDto().getId().intValue())))
                .andExpect(jsonPath("$.paymentDto.payday",is(equalTo("2024-02-06"))))
                .andExpect(jsonPath("$.paymentDto.type",is(equalTo("PIX"))))
                .andExpect(jsonPath("$.paymentDto.status",is(equalTo("PAID_OUT"))));

    }
    @Test
    @DisplayName("Listar todos payments")
    void listarPayment() throws Exception {
        List<ResponsePayment> responses = new ArrayList<>();
        responses.add(responsePayment);
        responses.add(responsePayment);

        given(service.listPayment()).willReturn(responses);

        ResultActions response = mockMvc.perform(get("/payment"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",is(responses.size())));
    }
    @Test
    @DisplayName("Deletar payment")
    void deletarPayment() throws Exception {

        willDoNothing().given(service).delete(payment.getId());

        ResultActions response = mockMvc.perform(delete("/payment/{idPayment}",payment.getId()));

        response.andExpect(status().isNoContent())
                .andDo(print());
    }
}
