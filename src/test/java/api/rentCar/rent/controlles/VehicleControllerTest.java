//package api.rentCar.rent.controlles;
//
//import api.rentCar.domains.entity.Model;
//import api.rentCar.domains.entity.Vehicle;
//import api.rentCar.domains.model.ModelDto;
//import api.rentCar.domains.model.VehicleDto;
//import api.rentCar.domains.repository.RepositoryModel;
//import api.rentCar.domains.repository.RepositoryVehicle;
//import api.rentCar.rest.controller.VehicleController;
//import api.rentCar.rest.request.RequestVehicle;
//import api.rentCar.rest.response.ResponseModel;
//import api.rentCar.rest.response.ResponseVehicle;
//import api.rentCar.service.VehicleService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.BDDMockito;
//import org.mockito.InjectMocks;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.util.List;
//@AutoConfigureMockMvc
//@ExtendWith(MockitoExtension.class)
//@SpringBootTest
//public class VehicleControllerTest {
//    @InjectMocks
//    private VehicleController vehicleController;
//
//    @MockBean
//    private VehicleService vehicleService;
//
//    @Autowired
//    private RepositoryVehicle repositoryVehicle;
//    @Autowired
//    private RepositoryModel repositoryModel;
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper mapper;
//
//    private ResponseVehicle responseVehicle;
//    private ResponseModel responseModel;
//    private RequestVehicle requestVehicle;
//    private Model model;
//    private List<ResponseVehicle> responseVehicles;
//
//    @BeforeEach
//    public void setUp() {
//
//        Model model = new Model();
//        model.setId(1L);
//        model.setModel("Cronos 1.2 8V Flex");
//        model.setModelYear(2018);
//        model.setFabricator("Fiat");
//        model.setCategory(1);
//        repositoryModel.save(model);
//
//        Vehicle vehicle = new Vehicle();
//        vehicle.setId(1L);
//        vehicle.setModel(model);
//        vehicle.setPlate("rio2020");
//        repositoryVehicle.save(vehicle);
//
//        ModelDto modelDto = new ModelDto(1L, "Cronos 1.2 8V Flex", 2018, "Fiat", 1);
//        VehicleDto vehicleDto = new VehicleDto(1L,modelDto,"rio2a12");
//        responseVehicle = new ResponseVehicle(vehicleDto);
//    }
//    @AfterEach
//    void down(){
//        repositoryVehicle.deleteAll();
//        repositoryModel.deleteAll();
//    }
//
//    @Test
//    @DisplayName("Salvar vehicle")
//    void salvarVehicle() throws Exception{
//
//        RequestVehicle request = RequestVehicle.builder()
//                .id(1L)
//                .plate("rio0e23")
//                .build();
//
//        String vehicleRequest = mapper.writeValueAsString(request);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/vehicle/{nameModel}",Mockito.anyString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("UTF-8")
//                        .content(vehicleRequest)
//                )
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print());
//
//    }
//    @Test
//    @DisplayName("Listar os Vehicles")
//    void listaVehicle() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/vehicle"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print());
//    }
//    @Test
//    @DisplayName("Listar os Vehicles por category")
//    void listaVehicleCategory() throws Exception {
//        BDDMockito.given(this.vehicleService.searchByCategory(Mockito.anyInt())).willReturn(Mockito.anyList());
//        mockMvc.perform(MockMvcRequestBuilders.get("/vehicle/{category}",Mockito.anyInt()))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print());
//    }
//}
