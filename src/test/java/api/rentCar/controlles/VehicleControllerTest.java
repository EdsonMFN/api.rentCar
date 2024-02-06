package api.rentCar.controlles;


import api.rentCar.domains.entity.Model;
import api.rentCar.domains.entity.Vehicle;
import api.rentCar.domains.model.ModelDto;
import api.rentCar.domains.model.VehicleDto;
import api.rentCar.enums.Category;
import api.rentCar.exceptions.handlers.HandlerEntitydadeNotFoundException;
import api.rentCar.rest.controller.VehicleController;
import api.rentCar.rest.request.RequestVehicle;
import api.rentCar.rest.response.ResponseVehicle;
import api.rentCar.service.VehicleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VehicleController.class)
public class VehicleControllerTest {

    @MockBean
    private VehicleService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    private Model model;
    private Vehicle vehicle;
    private RequestVehicle request;
    private ResponseVehicle responseVehicle;
    private VehicleDto vehicleDto;
    private ModelDto modelDto;

    @BeforeEach
    public void setUp() {

        model = new Model(1L,"Cronos 1.2 8V Flex",2018,"Fiat",Category.SEDAN);
        modelDto = new ModelDto(1L,"Cronos 1.2 8V Flex",2018,"Fiat",Category.SEDAN);
        vehicle = new Vehicle(1L,model,"rio2020");
        vehicleDto = new VehicleDto(1L,modelDto,"rio2020");
        request = new RequestVehicle(1L,"rio2020");
        responseVehicle = new ResponseVehicle(vehicleDto);
    }

    @Test
    @DisplayName("Salvar vehicle")
    void salvarVehicle()  throws Exception{

        given(service.createVehicle(any(RequestVehicle.class),anyLong()))
                .willReturn(responseVehicle);

        ResultActions response = mockMvc.perform(post("/vehicle/{idModel}", model.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
        );
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.vehicleDto.id", is(responseVehicle.getVehicleDto().getId().intValue())))
                .andExpect(jsonPath("$.vehicleDto.modelDto.id", is(responseVehicle.getVehicleDto().getModelDto().getId().intValue())))
                .andExpect(jsonPath("$.vehicleDto.modelDto.model", is(responseVehicle.getVehicleDto().getModelDto().getModel())))
                .andExpect(jsonPath("$.vehicleDto.modelDto.modelYear", is(responseVehicle.getVehicleDto().getModelDto().getModelYear())))
                .andExpect(jsonPath("$.vehicleDto.modelDto.fabricator", is(responseVehicle.getVehicleDto().getModelDto().getFabricator())))
                .andExpect(jsonPath("$.vehicleDto.modelDto.category", is(equalTo("SEDAN"))))
                .andExpect(jsonPath("$.vehicleDto.plate", is(vehicleDto.getPlate())));
    }
    @Test
    @DisplayName("buscar todos vehicle")
    void listarVehicle()  throws Exception {

        List<ResponseVehicle> responses = new ArrayList<>();
        responses.add(new ResponseVehicle(vehicleDto));

        given(service.listVehicle()).willReturn(responses);

        ResultActions response = mockMvc.perform(get("/vehicle"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(responses.size())));
    }
    @Test
    @DisplayName("buscar todos por category")
    void listarByCategoryVehicle()  throws Exception {
        List<ResponseVehicle> responses = new ArrayList<>();
        responses.add(new ResponseVehicle(vehicleDto));

        given(service.searchByCategory(model.getCategory().getId())).willReturn(responses);

        ResultActions response = mockMvc.perform(get("/vehicle/category/{category}", model.getCategory().getId()));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(responses.size())));
    }
    @Test
    @DisplayName("buscar todos por category")
    void listarByCategoryNotFoundVehicle()  throws Exception {

        given(service.searchByCategory(model.getCategory().getId())).willThrow(HandlerEntitydadeNotFoundException.class);

        ResultActions response = mockMvc.perform(get("/vehicle/category/{category}", model.getCategory().getId()));

        response.andExpect(status().isNotFound())
                .andDo(print());
    }
    @Test
    @DisplayName("Excluir veículo por ID")
    void deleteVehicle()  throws  Exception {

        willDoNothing().given(service).delete(vehicle.getId());

        ResultActions response = mockMvc.perform(delete("/vehicle/{idVehicle}", vehicle.getId()));

        response.andExpect(status().isNoContent())
                .andDo(print());
    }
}
