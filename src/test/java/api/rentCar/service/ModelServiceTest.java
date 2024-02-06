package api.rentCar.service;

import api.rentCar.domains.entity.Model;
import api.rentCar.domains.entity.Vehicle;
import api.rentCar.domains.model.ModelDto;
import api.rentCar.domains.model.VehicleDto;
import api.rentCar.domains.repository.RepositoryModel;
import api.rentCar.domains.repository.RepositoryVehicle;
import api.rentCar.enums.Category;
import api.rentCar.exceptions.handlers.HandlerEntitydadeNotFoundException;
import api.rentCar.rest.request.RequestModel;
import api.rentCar.rest.response.ResponseModel;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ModelServiceTest {

    @InjectMocks
    private ModelService modelService;

    @Mock
    private RepositoryVehicle repositoryVehicle;
    @Mock
    private RepositoryModel repositoryModel;

    private ResponseVehicle responseVehicle;
    private ResponseModel responseModel;
    private RequestModel requestModel;
    private Model model;
    private Vehicle vehicle;
    private VehicleDto vehicleDto;
    private ModelDto modelDto;
    private List<ResponseModel> responseModels;

        @BeforeEach
        public void setUp(){

            model = new Model();
            model.setId(1L);
            model.setModel("Cronos 1.2 8V Flex");
            model.setModelYear(2018);
            model.setFabricator("Fiat");
            model.setCategory(Category.SEDAN);

            vehicle = new Vehicle(1L,model,"rio2o23");

            vehicleDto = VehicleDto.builder()
                    .id(vehicle.getId())
                    .modelDto(modelDto)
                    .plate(vehicle.getPlate())
                    .build();

            modelDto = new ModelDto(1L,"Cronos 1.2 8V Flex",2018,"Fiat",Category.SEDAN);
            vehicleDto = new VehicleDto(1L,modelDto,"rio2o23");
            requestModel = new RequestModel(1L,"Cronos 1.2 8V Flex",2018,"Fiat",Category.SEDAN);

            ResponseModel responseModel = new ResponseModel(ModelDto.builder()
                    .id(model.getId())
                    .model(model.getModel())
                    .modelYear(model.getModelYear())
                    .fabricator(model.getFabricator())
                    .category(model.getCategory())
                    .build());

            responseModels = new ArrayList<>();
            responseModels.add(responseModel);
        }
        @Test
        @DisplayName("Deve verificar se o request está sendo lançado")
        void requestCorreto(){
            Mockito.when(repositoryModel.findById(model.getId())).thenReturn(Optional.ofNullable(model));
            ResponseModel responseModel1 = modelService.createValueVehicle(requestModel);

            Assertions.assertEquals(responseModel,responseModel1);
        }

        @Test
        @DisplayName("Deve verificar se está retornando uma lista com todos o models")
    void DeveVerificarRetornoListaModels(){
            Mockito.when(repositoryModel.findAll()).thenReturn(Collections.singletonList(model));

            List<ResponseModel> responseModels1 = modelService.listVehicle();

            Assertions.assertEquals(responseModels,responseModels1);

            Mockito.verify(repositoryModel).findAll();
        }
    @Test
    @DisplayName("Deve verificar se está retornando o id do model")
    void DeveVerificarRetornoModel(){
        Mockito.when(repositoryModel.findById(model.getId())).thenReturn(Optional.ofNullable(model));

        ResponseModel responseModel1 = modelService.updateModel(requestModel,model.getId());

        Mockito.verify(repositoryModel, Mockito.times(1)).findById(model.getId());
    }
    @Test
    @DisplayName("Deve executar o HandlerEntitydadeNotFoundException quando não," +
            " houver o model")
    void deveExecutarHandlerEntityNotFoundExceptionNoUpdateModel(){

        when(repositoryModel.findById(model.getId()))
                .thenThrow(new HandlerEntitydadeNotFoundException("entity with id "+ model.getId() +" not found"));
        try {
            ResponseModel responseModel1 = modelService.updateModel(requestModel,model.getId());

        }catch (HandlerEntitydadeNotFoundException ex){
            assertEquals(HandlerEntitydadeNotFoundException.class,ex.getClass());
            assertEquals("entity with id "+ model.getId() +" not found",ex.getMessage());
        }
    }
    @Test
    @DisplayName("Deve deletar o model pelo id")
    void deveDeletarModelId(){
            Mockito.when(repositoryModel.findById(model.getId())).thenReturn(Optional.of(model));
            doNothing().when(repositoryModel).deleteById(model.getId());

            Mockito.verify(repositoryModel,Mockito.times(1)).deleteById(model.getId());
    }
}
