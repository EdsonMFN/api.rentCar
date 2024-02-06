package api.rentCar.controlles;

import api.rentCar.domains.entity.Model;
import api.rentCar.domains.model.ModelDto;
import api.rentCar.enums.Category;
import api.rentCar.rest.controller.ModelController;
import api.rentCar.rest.request.RequestModel;
import api.rentCar.rest.response.ResponseModel;
import api.rentCar.service.ModelService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ModelController.class)
public class ModelControllerTest {

    @MockBean
    private ModelService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    private ResponseModel responseModel;
    private RequestModel requestModel;
    private Model model;
    private ModelDto modelDto;

    @BeforeEach
    public void setUp() {

        model = new Model(1L,"Cronos 1.2 8V Flex",2018,"Fiat", Category.SEDAN);
        modelDto = new ModelDto(1L,"Cronos 1.2 8V Flex",2018,"Fiat",Category.SEDAN);
        responseModel = new ResponseModel(modelDto);
        requestModel = new RequestModel(1L,"Cronos 1.2 8V Flex",2018,"Fiat",Category.SEDAN);

    }
    @Test
    @DisplayName("Criar model")
    void salvarModel() throws Exception {

        given(service.createValueVehicle(any(RequestModel.class))).willReturn(responseModel);
        ResultActions response = mockMvc.perform(post("/model")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestModel))
        );
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.modelDto.id", is(responseModel.getModelDto().getId().intValue())))
                .andExpect(jsonPath("$.modelDto.model", is(responseModel.getModelDto().getModel())))
                .andExpect(jsonPath("$.modelDto.modelYear", is(responseModel.getModelDto().getModelYear())))
                .andExpect(jsonPath("$.modelDto.fabricator", is(responseModel.getModelDto().getFabricator())))
                .andExpect(jsonPath("$.modelDto.category", is(equalTo("SEDAN"))));
    }
    @Test
    @DisplayName("Listar todas os modelos")
    void listarModels() throws Exception {

        List<ResponseModel> reponses = new ArrayList<>();
        reponses.add(responseModel);
        reponses.add(responseModel);

        given(service.listVehicle()).willReturn(reponses);

        ResultActions response = mockMvc.perform(get("/model"));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",is(reponses.size())));
    }
    @Test
    @DisplayName("Alterar model")
    void alterarModel() throws Exception {

        given(service.updateModel(any(RequestModel.class),anyLong())).willReturn(responseModel);
        ResultActions response = mockMvc.perform(put("/model/{idModel}",model.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestModel))
        );
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.modelDto.id", is(responseModel.getModelDto().getId().intValue())))
                .andExpect(jsonPath("$.modelDto.model", is(responseModel.getModelDto().getModel())))
                .andExpect(jsonPath("$.modelDto.modelYear", is(responseModel.getModelDto().getModelYear())))
                .andExpect(jsonPath("$.modelDto.fabricator", is(responseModel.getModelDto().getFabricator())))
                .andExpect(jsonPath("$.modelDto.category", is(equalTo("SEDAN"))));
    }
    @Test
    @DisplayName("Deletar o model")
    void deletarModel() throws Exception {
        willDoNothing().given(service).delete(model.getId());

        ResultActions response = mockMvc.perform(delete("/model/{idModel}",model.getId()));

        response.andExpect(status().isNoContent())
                .andDo(print());
    }
}
