package api.rentCar.controlles;

import api.rentCar.domains.entity.File;
import api.rentCar.domains.model.FileDto;
import api.rentCar.enums.TypeFile;
import api.rentCar.rest.controller.FileContoller;
import api.rentCar.rest.request.RequestFile;
import api.rentCar.rest.response.ResponseFile;
import api.rentCar.service.FileService;
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

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FileContoller.class)
public class FileControllerTest {

    @MockBean
    private FileService service;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    private File file;
    private FileDto fileDto;
    private ResponseFile responseFile;
    private RequestFile requestFile;

    @BeforeEach
    void setUp(){
        file = new File(1L, TypeFile.XLSX,"");
        fileDto = new FileDto(1L,TypeFile.XLSX,"");
        responseFile = new ResponseFile(fileDto);
        requestFile = new RequestFile();
    }
    @Test
    @DisplayName("Criar um arquivo xlsx")
    void criarArquivoXlsx() throws Exception {

        given(service.createFileXlsx(any(RequestFile.class))).willReturn(responseFile);

        ResultActions responses = mockMvc.perform(post("/file")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestFile)));

        responses.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.fileDto.id",is(responseFile.getFileDto().getId())));
    }
}
