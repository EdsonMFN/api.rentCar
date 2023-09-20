package api.rentCar;

import api.rentCar.domains.entity.Model;
import api.rentCar.domains.entity.Vehicle;
import api.rentCar.domains.repository.RepositoryModel;
import api.rentCar.domains.repository.RepositoryVehicle;
import api.rentCar.domains.model.ModelDto;
import api.rentCar.domains.model.VehicleDto;
import api.rentCar.rest.request.RequestVehicle;
import api.rentCar.rest.response.ResponseModel;
import api.rentCar.rest.response.ResponseVehicle;
import api.rentCar.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VehicleTest {

    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private RepositoryVehicle repositoryVehicle;
    @Mock
    private RepositoryModel repositoryModel;

    private ResponseVehicle responseVehicle;
    private ResponseModel responseModel;
    private RequestVehicle requestVehicle;
    private Model model;
    private Vehicle vehicle;
    private VehicleDto vehicleDto;
    private ModelDto modelDto;
    private List<ResponseVehicle> responseVehicles;

    @BeforeEach
    public void setUp(){

        model = new Model();
        model.setId(1L);
        model.setModel("Cronos 1.2 8V Flex");
        model.setModelYear(2018);
        model.setFabricator("Fiat");
        model.setCategory(1);

        vehicle = new Vehicle(1L,model,"rio2o23");

        vehicleDto = VehicleDto.builder()
                .id(vehicle.getId())
                .modelDto(modelDto)
                .plate(vehicle.getPlate())
                .build();

        modelDto = new ModelDto(1L,"Cronos 1.2 8V Flex",2018,"Fiat",1);
        vehicleDto = new VehicleDto(1L,modelDto,"rio2o23");
        requestVehicle = new RequestVehicle(1L,"Cronos 1.2 8V Flex","rio2o23");

        responseVehicle = new ResponseVehicle(VehicleDto.builder()
                .id(vehicle.getId())
                .modelDto(modelDto)
                .plate(vehicle.getPlate())
                .build());

        responseVehicles = new ArrayList<>();
        responseVehicles.add(responseVehicle);
    }

    @Test
    @DisplayName("Verificar se está buscando corretamento o model, add corretamente o vehicle, add pelo model e apenas um model")
    void verificarAddCorretoDoVehicleComModel(){

        when(repositoryModel.getReferenceByModel(model.getModel())).thenReturn(model);

        ResponseVehicle responseVehicle = vehicleService.createVehicle(requestVehicle);

        assertEquals(model,vehicle.getModel());

        verify(repositoryModel).getReferenceByModel(model.getModel());
        verifyNoMoreInteractions(repositoryModel);
    }

    @Test
    @DisplayName("Verificar se está trsazendo uma lista com todos os vehicles")
    void trazerTodosVehicles(){
        when(repositoryVehicle.findAll()).thenReturn(Collections.singletonList(vehicle));

        List<ResponseVehicle> responseVehicles1 = vehicleService.listVehicle();

        assertEquals(responseVehicles,responseVehicles1);

        verify(repositoryVehicle).findAll();
    }
}
