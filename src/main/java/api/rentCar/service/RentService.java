package api.rentCar.service;

import api.rentCar.domains.entity.Rent;
import api.rentCar.domains.entity.User;
import api.rentCar.domains.entity.Vehicle;
import api.rentCar.domains.model.ModelDto;
import api.rentCar.domains.model.RentDto;
import api.rentCar.domains.model.VehicleDto;
import api.rentCar.domains.repository.RepositoryRent;
import api.rentCar.domains.repository.RepositoryUser;
import api.rentCar.domains.repository.RepositoryVehicle;
import api.rentCar.exceptions.handlers.HandlerDataIntegrityViolationException;
import api.rentCar.exceptions.handlers.HandlerEntityNotFoundException;
import api.rentCar.exceptions.handlers.HandlerErrorException;
import api.rentCar.rest.request.TypesToSearch;
import api.rentCar.rest.response.ResponseRent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class RentService {

    @Autowired
    private RepositoryVehicle repositoryVehicle;
    @Autowired
    private RepositoryRent repositoryRent;
    @Autowired
    private RepositoryUser repositoryUser;
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    public ResponseRent createRent(RentDto request,Long idUser, String plate){

        try {
            User user = repositoryUser.findById(idUser)
                    .orElseThrow(()-> new HandlerEntityNotFoundException("Entity with id" + idUser + " not found"));
            Vehicle vehicle = repositoryVehicle.findByPlate(plate);

            Rent rent = new Rent(request);
            rent.setVehicle(vehicle);
            rent.setRentAmount(rentValueTotal(rent.getValueWeekday(), rent.getValueWeekenday(), rent.getDateWithdrawal(), rent.getDateDelivery()));
            rent.setUser(user);
            repositoryRent.save(rent);

            var model = vehicle.getModel();

            ModelDto modelDto = new ModelDto(model);

            VehicleDto vehicleDto = new VehicleDto(vehicle);
            vehicleDto.setModelDto(modelDto);

            RentDto rentDto = new RentDto(rent);
            rentDto.setVehicleDto(vehicleDto);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            var stringRent = objectMapper.writeValueAsString(rentDto);

            this.kafkaTemplate.send("test.kafka2",stringRent);
//            //Produzir conteudo pro kafka

            return new ResponseRent(rentDto);

        }catch (Exception ex){
        throw new HandlerErrorException(ex.getMessage());
    }
    }
    public List<ResponseRent> listVehicle(TypesToSearch filer){
    try {
        List <Rent> rents = repositoryRent.findAll();
        List<ResponseRent> responseRents = new ArrayList<>();

        rents.stream().filter(rent ->
                        rent.getUser().getClient().getName().equals(filer.getName()) ||
                        rent.getId().equals(filer.getId())
                )
                .forEach( rent -> {

            ResponseRent responseRent = new ResponseRent(new RentDto(rent));

            responseRents.add(responseRent);
        });
        return responseRents;
    }catch (Exception ex){
        throw new HandlerErrorException(ex.getMessage());
    }
    }
    public void delete(Long idRent){
        Rent rent = repositoryRent.findById(idRent)
                .orElseThrow(() -> new HandlerEntityNotFoundException("Entity with id "+ idRent+" not found"));
       try {
           repositoryRent.deleteById(rent.getId());
       }catch (DataIntegrityViolationException ex){
        throw new HandlerDataIntegrityViolationException(ex.getMessage());
        }catch (Exception ex){
            throw new HandlerErrorException(ex.getMessage());
        }
    }

    private static Stream<LocalDate> getQuantityDaysRent(LocalDate dateWithdrawal, LocalDate dateDelivery) {
        return dateWithdrawal.datesUntil(dateDelivery);
    }

    private int rentValueTotal(int valueWeekday, int valueWeekenday, LocalDate dateWithdrawal, LocalDate dateDelivery) {

        if (dateDelivery.isAfter(dateWithdrawal)){

            int rentTotalAmount = 0;

            List<LocalDate> quantityDaysRent = getQuantityDaysRent(dateWithdrawal, dateDelivery).toList();

            for (LocalDate dayRent: quantityDaysRent) {
                DayOfWeek dayOfWeek = dayRent.getDayOfWeek();

                if (dayOfWeek.equals(DayOfWeek.SATURDAY) || dayOfWeek.equals(DayOfWeek.SUNDAY)) {
                    rentTotalAmount += valueWeekenday;
                } else {
                    rentTotalAmount += valueWeekday;
                }
            }

            return rentTotalAmount;
        }else {
            throw new RuntimeException("The delivery date must be greater than the withdrawal date!");
        }
    }
}
