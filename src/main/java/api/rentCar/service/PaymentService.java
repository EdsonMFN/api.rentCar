package api.rentCar.service;

import api.rentCar.domains.entity.Payment;
import api.rentCar.domains.entity.Rent;
import api.rentCar.domains.model.PaymentDto;
import api.rentCar.domains.model.RentDto;
import api.rentCar.domains.repository.RepositoryModel;
import api.rentCar.domains.repository.RepositoryPayment;
import api.rentCar.domains.repository.RepositoryRent;
import api.rentCar.domains.repository.RepositoryVehicle;
import api.rentCar.exceptions.handlers.HandlerDataIntegrityViolationException;
import api.rentCar.exceptions.handlers.HandlerEntitydadeNotFoundException;
import api.rentCar.exceptions.handlers.HandlerErrorException;
import api.rentCar.rest.request.RequestPayment;
import api.rentCar.rest.response.ResponsePayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private RepositoryVehicle repositoryVehicle;
    @Autowired
    private RepositoryModel repositoryModel;
    @Autowired
    private RepositoryRent repositoryRent;
    @Autowired
    private RepositoryPayment repositoryPayment;

//    @Autowired
//    private KafkaTemplate<String,String> kafkaTemplate;

    public ResponsePayment createPayment(RequestPayment requestPayment, Long idRent){

        Rent rent = repositoryRent.findById(idRent)
                .orElseThrow(() -> new HandlerEntitydadeNotFoundException("entity with id "+ idRent +" not found"));
        try {
            Payment payment = new Payment();
            payment.setType(requestPayment.getType());
            payment.setStatus(requestPayment.getStatus());
            payment.setPayday(requestPayment.getPayday());
            payment.setRent(rent);
            repositoryPayment.save(payment);

//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.registerModule(new JavaTimeModule());
//
//            var stringPayment = objectMapper.writeValueAsString(payment);
//
//            this.kafkaTemplate.send("test.kafka2",stringPayment);
            //Produzir conteudo pro kafka
            RentDto rentDto = RentDto.builder()
                    .id(rent.getId())
                    .rentAmount(rent.getRentAmount())
                    .build();

            return new ResponsePayment(PaymentDto.builder()
                    .id(payment.getId())
                    .type(payment.getType())
                    .payday(payment.getPayday())
                    .status(payment.getStatus())
                    .rentDto(rentDto)
                    .build());

        }catch (Exception ex){
        throw new HandlerErrorException(ex.getMessage());
    }
    }
    public List<ResponsePayment> listPayment(){
    try {
        List <Payment> payments = repositoryPayment.findAll();
        List<ResponsePayment> responsePayments = new ArrayList<>();


        payments.forEach( payment -> {

            var rent = payment.getRent();

            RentDto rentDto = RentDto.builder()
                    .id(rent.getId())
                    .rentAmount(rent.getRentAmount())
                    .build();

            ResponsePayment responsePayment = new ResponsePayment(PaymentDto
                    .builder()
                    .id(payment.getId())
                    .rentDto(rentDto)
                    .status(payment.getStatus())
                    .payday(payment.getPayday())
                    .type(payment.getType())
                    .build());

            responsePayments.add(responsePayment);
        });
        return responsePayments;
    }catch (Exception ex){
        throw new HandlerErrorException(ex.getMessage());
    }
    }
    public String delete(Long idPayment){
        Payment payment = repositoryPayment.findById(idPayment)
                .orElseThrow(() -> new HandlerEntitydadeNotFoundException("entity with id "+ idPayment+" not found"));
       try {
           repositoryPayment.deleteById(payment.getId());

           ResponsePayment responsePayment = new ResponsePayment();

           return responsePayment.msgDelet();

       }catch (DataIntegrityViolationException ex){
        throw new HandlerDataIntegrityViolationException(ex.getMessage());
        }catch (Exception ex){
            throw new HandlerErrorException(ex.getMessage());
        }
    }
}
