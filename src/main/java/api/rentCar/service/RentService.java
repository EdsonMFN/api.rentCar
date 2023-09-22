package api.rentCar.service;

import api.rentCar.domains.entity.Rent;
import api.rentCar.domains.entity.Vehicle;
import api.rentCar.domains.model.ModelDto;
import api.rentCar.domains.model.RentDto;
import api.rentCar.domains.model.VehicleDto;
import api.rentCar.domains.repository.RepositoryModel;
import api.rentCar.domains.repository.RepositoryRent;
import api.rentCar.domains.repository.RepositoryVehicle;
import api.rentCar.exceptions.handlers.HandlerDataIntegrityViolationException;
import api.rentCar.exceptions.handlers.HandlerEntitydadeNotFoundException;
import api.rentCar.exceptions.handlers.HandlerErrorException;
import api.rentCar.rest.request.RequestRent;
import api.rentCar.rest.response.ResponseRent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RentService {

    @Autowired
    private RepositoryVehicle repositoryVehicle;
    @Autowired
    private RepositoryModel repositoryModel;
    @Autowired
    private RepositoryRent repositoryRent;


    public ResponseRent createRent(RequestRent requestRent, String plate){


        Vehicle vehicle = Optional.ofNullable(repositoryVehicle.findByPlate(plate))
                .orElseThrow(() -> new HandlerEntitydadeNotFoundException("entity with id "+ plate +" not found"));
        try {
            Rent rent = new Rent();
            rent.setVehicle(vehicle);
            rent.setDateWithdrawal(LocalDate.now());
            rent.setDateDelivery(requestRent.getDateDelivery());
            rent.setValueWeekday(requestRent.getValueWeekday());
            rent.setValueWeekenday(requestRent.getValueWeekenday());
            rent.setRentAmount(rentValueTotal(rent.getValueWeekday(), rent.getValueWeekenday(), rent.getDateWithdrawal(), rent.getDateDelivery()));
            repositoryRent.save(rent);

            var model = vehicle.getModel();

            ModelDto modelDto = ModelDto.builder()
                    .id(model.getId())
                    .model(model.getModel())
                    .modelYear(model.getModelYear())
                    .fabricator(model.getFabricator())
                    .category(model.getCategory())
                    .build();

            VehicleDto vehicleDto = VehicleDto.builder()
                    .id(vehicle.getId())
                    .modelDto(modelDto)
                    .plate(vehicle.getPlate())
                    .build();

            return new ResponseRent(RentDto.builder()
                    .id(rent.getId())
                    .dateWithdrawal(LocalDate.now())
                    .dateDelivery(rent.getDateDelivery())
                    .valueWeekday(rent.getValueWeekday())
                    .valueWeekenday(rent.getValueWeekenday())
                    .vehicleDto(vehicleDto)
                    .rentAmount(rent.getRentAmount())
                    .build());
        }catch (Exception ex){
        throw new HandlerErrorException(ex.getMessage());
    }
    }
    public List<ResponseRent> listVehicle(){
    try {
        List <Rent> rents = repositoryRent.findAll();
        List<ResponseRent> responseRents = new ArrayList<>();

        rents.forEach( rent -> {

            ResponseRent responseRent = new ResponseRent (RentDto.builder()
                    .id(rent.getId())
                    .dateWithdrawal(rent.getDateWithdrawal())
                    .dateDelivery(rent.getDateDelivery())
                    .valueWeekday(rent.getValueWeekday())
                    .valueWeekenday(rent.getValueWeekenday())
                    .rentAmount(rent.getRentAmount())
                    .build());

            responseRents.add(responseRent);
        });
        return responseRents;
    }catch (Exception ex){
        throw new HandlerErrorException(ex.getMessage());
    }
    }
    public ResponseRent delete(Long idRent){
        Rent rent = repositoryRent.findById(idRent)
                .orElseThrow(() -> new HandlerEntitydadeNotFoundException("entity with id "+ idRent+" not found"));
       try {
           repositoryRent.deleteById(rent.getId());

           ResponseRent responseRent = new ResponseRent();

           return responseRent;

       }catch (DataIntegrityViolationException ex){
        throw new HandlerDataIntegrityViolationException(ex.getMessage());
        }catch (Exception ex){
            throw new HandlerErrorException(ex.getMessage());
        }
    }

    private static Stream<LocalDate> getQuantityDaysRent(LocalDate dateWithdrawal, LocalDate dateDelivery) {
        return dateWithdrawal.datesUntil(dateDelivery);
    }

    private Integer rentValueTotal(int valueWeekday, int valueWeekenday, LocalDate dateWithdrawal, LocalDate dateDelivery) {

        if (dateDelivery.isAfter(dateWithdrawal)){

            Integer rentTotalAmount = 0;

            List<LocalDate> quantityDaysRent = getQuantityDaysRent(dateWithdrawal, dateDelivery).collect(Collectors.toList());

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
            throw new RuntimeException("the delivery date must be greater than the withdrawal date!");
        }
    }
}
