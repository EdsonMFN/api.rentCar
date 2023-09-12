package api.rentCar.service;

import api.rentCar.entity.Rent;
import api.rentCar.entity.Vehicle;
import api.rentCar.repository.RepositoryModel;
import api.rentCar.repository.RepositoryRent;
import api.rentCar.repository.RepositoryVehicle;
import api.rentCar.rest.dto.ModelDto;
import api.rentCar.rest.dto.RentDto;
import api.rentCar.rest.dto.VehicleDto;
import api.rentCar.rest.request.RequestRent;
import api.rentCar.rest.response.ResponseRent;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

        Vehicle vehicle = repositoryVehicle.findByPlate(plate);

        Rent rent = new Rent();
        rent.setVehicle(vehicle);
        rent.setDateWithdrawal(LocalDate.now());
        rent.setDateDelivery(requestRent.getDateDelivery());
        rent.setValueWeekday(requestRent.getValueWeekday());
        rent.setValueWeekenday(requestRent.getValueWeekenday());
        rent.setRentAmount(rentValueTotal( rent.getValueWeekday(), rent.getValueWeekenday(),rent.getDateWithdrawal(),rent.getDateDelivery()));
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
    }
    public List<ResponseRent> listVehicle(){

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
    }
    public ResponseRent delete(Long idRent){

        repositoryRent.deleteById(idRent);

        ResponseRent responseRent = new ResponseRent();

        return responseRent;
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
