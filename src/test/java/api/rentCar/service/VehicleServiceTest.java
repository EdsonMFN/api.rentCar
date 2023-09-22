package api.rentCar.service;

import api.rentCar.domains.entity.Model;
import api.rentCar.domains.entity.Vehicle;
import api.rentCar.domains.model.ModelDto;
import api.rentCar.domains.model.VehicleDto;
import api.rentCar.domains.repository.RepositoryModel;
import api.rentCar.domains.repository.RepositoryVehicle;
import api.rentCar.exceptions.handlers.HandlerEntitydadeNotFoundException;
import api.rentCar.rest.request.RequestVehicle;
import api.rentCar.rest.response.ResponseModel;
import api.rentCar.rest.response.ResponseVehicle;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

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
    private List<Vehicle> vehicles;

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

        ResponseVehicle responseVehicle = new ResponseVehicle(VehicleDto.builder()
                .id(vehicle.getId())
                .modelDto(modelDto)
                .plate(vehicle.getPlate())
                .build());

        responseVehicles = new ArrayList<>();
        responseVehicles.add(responseVehicle);
    }

    @Test
    @DisplayName("Verificar se está buscando corretamento o model," +
            " verificar se o vehicle está se relacionando corretamente com o model correspondente")
    void verificarAddCorretoDoVehicleComModel(){

        when(repositoryModel.findByModel(model.getModel())).thenReturn(model);

        ResponseVehicle responseVehicle = vehicleService.createVehicle(requestVehicle,model.getModel());

        assertEquals(model,vehicle.getModel());

        verify(repositoryModel).findByModel(model.getModel());
        verifyNoMoreInteractions(repositoryModel);
    }

    @Test
    @DisplayName("Deve executar o HandlerEntitydadeNotFoundException quando não," +
            " houver o model")
    void deveExecutarHandlerEntityNotFoundExceptionNoCreateVehicle(){

        when(repositoryModel.findByModel(model.getModel())).thenThrow(new HandlerEntitydadeNotFoundException("entity with name "+ model.getModel() +" not found"));
    try {
        ResponseVehicle responseVehicle = vehicleService.createVehicle(requestVehicle,model.getModel());

    }catch (HandlerEntitydadeNotFoundException ex){
        assertEquals(HandlerEntitydadeNotFoundException.class,ex.getClass());
        assertEquals("entity with name "+ model.getModel() +" not found",ex.getMessage());
    }
    }

    @Test
    @DisplayName("Verificar se está trsazendo uma lista com todos os vehicles")
    void trazerTodosVehicles(){
        when(repositoryVehicle.findAll()).thenReturn(Collections.singletonList(vehicle));

        List<ResponseVehicle> responseVehicles1 = vehicleService.listVehicle();

        assertEquals(responseVehicles,responseVehicles1);

        verify(repositoryVehicle).findAll();
    }

    @Test
    @DisplayName("Verificar se está buscando corretamente o model por category," +
            "listar todos os vehicles por model relacionado com a category")
    void verificarListaCategory(){

        when(repositoryModel.findAllByCategory(model.getCategory())).thenReturn(Collections.singletonList(model));
        when(repositoryVehicle.findByModel(model)).thenReturn(Collections.singletonList(vehicle));

        List<ResponseVehicle> responseVehicles1 = vehicleService.searchByCategory(model.getCategory());

        assertEquals(responseVehicles,responseVehicles1);

        verify(repositoryVehicle).findByModel(model);
        verify(repositoryModel).findAllByCategory(model.getCategory());
    }
    @Test
    @DisplayName("Deve executar o HandlerEntitydadeNotFoundException quando não," +
            " houver a category do model")
    void deveExecutarHandlerEntityNotFoundExceptionNaBuscaCategory(){

        when(repositoryModel.findAllByCategory(model.getCategory()))
                .thenThrow(new HandlerEntitydadeNotFoundException("entity with category "+ model.getCategory() +" not found"));
        try {
            List<ResponseVehicle> responseVehicles1 = vehicleService.searchByCategory(model.getCategory());
        }catch (HandlerEntitydadeNotFoundException ex){
            assertEquals(HandlerEntitydadeNotFoundException.class,ex.getClass());
            assertEquals("entity with category " + model.getCategory() +" not found",ex.getMessage());
        }
    }
    @Test
    @DisplayName("Deve deletar o vehicle pelo id")
    void deveDeletarVehicle(){
        when(repositoryVehicle.findById(anyLong())).thenReturn(Optional.ofNullable(vehicle));
        doNothing().when(repositoryVehicle).deleteById(vehicle.getId());

        ResponseVehicle responseVehicle1 = vehicleService.delete(vehicle.getId());

        ResponseVehicle responseVehicleNull = new ResponseVehicle();

        assertEquals(responseVehicleNull,responseVehicle1);

        verify(repositoryVehicle,times(1)).deleteById(vehicle.getId());
    }
    @Test
    @DisplayName("Deve executar o HandlerEntitydadeNotFoundException quando não," +
            " houver id do vehicle")
    void deveExecutarHandlerEntityNotFoundExceptionNaBuscaVehicle(){

        when(repositoryVehicle.findById(vehicle.getId()))
                .thenThrow(new HandlerEntitydadeNotFoundException("entity with id "+ vehicle.getId() +" not found"));
        try {
            ResponseVehicle responseVehicles1 = vehicleService.delete(vehicle.getId());
        }catch (HandlerEntitydadeNotFoundException ex){
            assertEquals(HandlerEntitydadeNotFoundException.class,ex.getClass());
            assertEquals("entity with id "+ vehicle.getId() +" not found",ex.getMessage());
        }
    }
//    @Test
//    @DisplayName("Deve executar o HandlerDataIntegrityViolationException quando não," +
//            " houver conflito entre entidades")
//    void deveExecutarHandlerDataIntegrityViolationExceptionQuandoHouverConflitoEntreEntidades(){
//        when(repositoryVehicle.findById(vehicle.getId())).thenReturn(Optional.of(vehicle));
//        try {
//            vehicleService.delete(vehicle.getId());
//        }catch (HandlerDataIntegrityViolationException ex){
//            assertEquals(HandlerDataIntegrityViolationException.class,ex.getClass());
//            assertEquals("Could not be deleted because there are related entities",ex.getMessage());
//        }
//    }
}
