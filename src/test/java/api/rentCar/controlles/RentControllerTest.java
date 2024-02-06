package api.rentCar.controlles;

import api.rentCar.domains.entity.Model;
import api.rentCar.domains.entity.Rent;
import api.rentCar.domains.entity.Vehicle;
import api.rentCar.domains.model.ModelDto;
import api.rentCar.domains.model.RentDto;
import api.rentCar.domains.model.VehicleDto;
import api.rentCar.enums.Category;
import api.rentCar.rest.controller.RentController;
import api.rentCar.rest.request.RequestRent;
import api.rentCar.rest.response.ResponseRent;
import api.rentCar.service.RentService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RentController.class)
public class RentControllerTest {

    @MockBean
    private RentService rentService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    private Rent rent;
    private RentDto rentDto;
    private ResponseRent responseRent;
    private RequestRent requestRent;
    private Model model;
    private ModelDto modelDto;
    private Vehicle vehicle;
    private VehicleDto vehicleDto;


    @BeforeEach
    void setup(){
        model = new Model(1L,"Cronos 1.2 8V Flex",2018,"Fiat", Category.SEDAN);
        modelDto = new ModelDto(1L,"Cronos 1.2 8V Flex",2018,"Fiat",Category.SEDAN);
        vehicle = new Vehicle(1L,model,"rio2020");
        vehicleDto = new VehicleDto(1L,modelDto,"rio2020");
        rent = new Rent(1L,vehicle, LocalDate.parse("2024-02-03"), LocalDate.parse("2024-02-04"),120,150,270);
        rentDto = new RentDto(1L,vehicleDto,120,150,"2024-02-03", "2024-02-04",270);
        responseRent = new ResponseRent(rentDto);
        requestRent = new RequestRent(1L,vehicleDto,120,150,LocalDate.parse("2024-02-03"), LocalDate.parse("2024-02-04"),270);
    }

    @Test
    @DisplayName("Salvar os rent")
    void SalvarRent() throws Exception {

        given(rentService.createRent(any(RequestRent.class),anyString()))
                .willReturn(responseRent);

        ResultActions response = mockMvc.perform(post("/rent/plate/{plate}",vehicle.getPlate())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestRent))
        );
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.rentDto.id",is(responseRent.getRentDto().getId().intValue())))
                .andExpect(jsonPath("$.rentDto.valueWeekday",is(responseRent.getRentDto().getValueWeekday())))
                .andExpect(jsonPath("$.rentDto.valueWeekenday",is(responseRent.getRentDto().getValueWeekenday())))
                .andExpect(jsonPath("$.rentDto.dateWithdrawal",is(responseRent.getRentDto().getDateWithdrawal())))
                .andExpect(jsonPath("$.rentDto.dateDelivery",is(responseRent.getRentDto().getDateDelivery())))
                .andExpect(jsonPath("$.rentDto.rentAmount",is(responseRent.getRentDto().getRentAmount())))
                .andExpect(jsonPath("$.rentDto.vehicleDto.id",is(responseRent.getRentDto().getVehicleDto().getId().intValue())))
                .andExpect(jsonPath("$.rentDto.vehicleDto.modelDto.id", is(responseRent.getRentDto().getVehicleDto().getModelDto().getId().intValue())))
                .andExpect(jsonPath("$.rentDto.vehicleDto.modelDto.model", is(responseRent.getRentDto().getVehicleDto().getModelDto().getModel())))
                .andExpect(jsonPath("$.rentDto.vehicleDto.modelDto.modelYear", is(responseRent.getRentDto().getVehicleDto().getModelDto().getModelYear())))
                .andExpect(jsonPath("$.rentDto.vehicleDto.modelDto.fabricator", is(responseRent.getRentDto().getVehicleDto().getModelDto().getFabricator())))
                .andExpect(jsonPath("$.rentDto.vehicleDto.modelDto.category", is(equalTo("SEDAN"))))
                .andExpect(jsonPath("$.rentDto.vehicleDto.plate", is(responseRent.getRentDto().getVehicleDto().getPlate())));

    }
    @Test
    @DisplayName("Liatar todos os rent")
    void ListarRest() throws Exception {

        List<ResponseRent> responses = new ArrayList<>();
        responses.add(responseRent);
        responses.add(responseRent);

        given(rentService.listVehicle()).willReturn(responses);

        ResultActions response = mockMvc.perform(get("/rent"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",is(responses.size())));
    }
    @Test
    @DisplayName("Deletar rent ")
    void deletarRent() throws Exception {

        willDoNothing().given(rentService).delete(rent.getId());

        ResultActions response = mockMvc.perform(delete("/rent/{idRent}",rent.getId()));

        response.andExpect(status().isNoContent())
                .andDo(print());
    }
}
