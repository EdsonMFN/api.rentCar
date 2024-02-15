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
import api.rentCar.exceptions.handlers.HandlerEntityNotFoundException;
import api.rentCar.exceptions.handlers.HandlerErrorException;
import api.rentCar.rest.request.TypesToSearch;
import api.rentCar.rest.response.ResponsePayment;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    public ResponsePayment createPayment(PaymentDto request, Long idRent){
        Rent rent = repositoryRent.findById(idRent)
                .orElseThrow(() -> new HandlerEntityNotFoundException("entity with id "+ idRent +" not found"));
        try {
            Payment payment = new Payment(request);
            payment.setRent(rent);
            repositoryPayment.save(payment);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            var stringPayment = objectMapper.writeValueAsString(payment);

            this.kafkaTemplate.send("test.kafka2",stringPayment);
//            Produzir conteudo pro kafka
            RentDto rentDto = new RentDto(rent);

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
    public List<ResponsePayment> listPayment(TypesToSearch filter){
        try {
            List <Payment> payments = repositoryPayment.findAll();
            List<ResponsePayment> responsePayments = new ArrayList<>();

            payments.stream().filter(payment ->
                            payment.getId().equals(filter.getId()) ||
                            payment.getPayday().equals(filter.getDate()) ||
                            payment.getStatus().equals(filter.getStatus())
                    )
                    .forEach( payment -> {

                var rent = payment.getRent();

                RentDto rentDto = new RentDto(rent);

                ResponsePayment responsePayment = new ResponsePayment(PaymentDto.builder()
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
    public void delete(Long idPayment){
        Payment payment = repositoryPayment.findById(idPayment)
                .orElseThrow(() -> new HandlerEntityNotFoundException("entity with id "+ idPayment+" not found"));
       try {
           repositoryPayment.deleteById(payment.getId());

       }catch (DataIntegrityViolationException ex){
        throw new HandlerDataIntegrityViolationException(ex.getMessage());
        }catch (Exception ex){
            throw new HandlerErrorException(ex.getMessage());
        }
    }
}
