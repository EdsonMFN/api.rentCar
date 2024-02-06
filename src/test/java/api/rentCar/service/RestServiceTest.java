package api.rentCar.service;

import api.rentCar.domains.entity.Model;
import api.rentCar.domains.entity.Rent;
import api.rentCar.domains.entity.Vehicle;
import api.rentCar.domains.model.ModelDto;
import api.rentCar.domains.model.RentDto;
import api.rentCar.domains.model.VehicleDto;
import api.rentCar.domains.repository.RepositoryModel;
import api.rentCar.domains.repository.RepositoryRent;
import api.rentCar.domains.repository.RepositoryVehicle;
import api.rentCar.enums.Category;
import api.rentCar.rest.request.RequestRent;
import api.rentCar.rest.response.ResponseModel;
import api.rentCar.rest.response.ResponseRent;
import api.rentCar.rest.response.ResponseVehicle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RestServiceTest {

    @InjectMocks
    private RentService rentService;

    @Mock
    private RepositoryRent repositoryRent;
    @Mock
    private RepositoryVehicle repositoryVehicle;
    @Mock
    private RepositoryModel repositoryModel;

    private ResponseVehicle responseVehicle;
    private ResponseModel responseModel;
    private ResponseRent responseRent;

    private RequestRent requestRent;

    private Rent rent;
    private Vehicle vehicle;
    private VehicleDto vehicleDto;

    private List<ResponseRent> responseRents;

    @BeforeEach
    public void setUp(){

        Model model = new Model();
        model.setId(1L);
        model.setModel("Cronos 1.2 8V Flex");
        model.setModelYear(2018);
        model.setFabricator("Fiat");
        model.setCategory(Category.SEDAN);
        LocalDate dateWithdrawal = LocalDate.parse("2023-09-22");
        LocalDate datedelivery = LocalDate.parse("2023-09-24");
        vehicle = new Vehicle(1L, model,"rio2o23");

        rent = new Rent(1L,vehicle,dateWithdrawal,datedelivery,100,90,190);

        RentDto rentDto = RentDto.builder()
                .id(rent.getId())
                .vehicleDto(vehicleDto)
                .valueWeekday(rent.getValueWeekday())
                .valueWeekenday(rent.getValueWeekenday())
                .dateWithdrawal(rent.getDateWithdrawal().toString())
                .dateDelivery(rent.getDateDelivery().toString())
                .rentAmount(rent.getRentAmount())
                .build();

        ModelDto modelDto = new ModelDto(1L, "Cronos 1.2 8V Flex", 2018, "Fiat", Category.SEDAN);
        vehicleDto = new VehicleDto(1L, modelDto,"rio2o23");

        requestRent = new RequestRent(1L,vehicleDto,100,90,dateWithdrawal,datedelivery,190);

        ResponseRent responseRent = new ResponseRent(RentDto.builder()
                .id(rent.getId())
                .valueWeekday(rent.getValueWeekday())
                .valueWeekenday(rent.getValueWeekenday())
                .dateDelivery(rent.getDateDelivery().toString())
                .dateWithdrawal(rent.getDateWithdrawal().toString())
                .rentAmount(rent.getRentAmount())
                .build());

        responseRents = new ArrayList<>();
        responseRents.add(responseRent);
    }

    @Test
    @DisplayName("Deve buscar o vehicle pela placa")
        void DevebuscarVehiclePlaca(){
        Mockito.when(repositoryVehicle.findByPlate(vehicle.getPlate())).thenReturn(vehicle);

        rentService.createRent(requestRent, vehicle.getPlate());

        Assertions.assertEquals(vehicle,rent.getVehicle());

        Mockito.verify(repositoryVehicle,Mockito.times(1)).findByPlate(vehicle.getPlate());
    }
    @Test
    @DisplayName("Deve listar todos os rents")
    void DeveListarTodosRents(){
        Mockito.when(repositoryRent.findAll()).thenReturn(Collections.singletonList(rent));

        List<ResponseRent> responseRents1 = rentService.listVehicle();

        Assertions.assertEquals(responseRents,responseRents1);

        Mockito.verify(repositoryRent).findAll();
    }
}
