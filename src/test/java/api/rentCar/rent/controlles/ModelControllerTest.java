package api.rentCar.rent.controlles;

import api.rentCar.domains.entity.Model;
import api.rentCar.domains.entity.Vehicle;
import api.rentCar.domains.model.ModelDto;
import api.rentCar.domains.model.VehicleDto;
import api.rentCar.domains.repository.RepositoryModel;
import api.rentCar.domains.repository.RepositoryVehicle;
import api.rentCar.rest.controller.ModelController;
import api.rentCar.rest.request.RequestModel;
import api.rentCar.rest.response.ResponseModel;
import api.rentCar.rest.response.ResponseVehicle;
import api.rentCar.service.ModelService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
@ExtendWith(MockitoExtension.class)
public class ModelControllerTest {

    @InjectMocks
    private ModelController modelController;

    @MockBean
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
}
