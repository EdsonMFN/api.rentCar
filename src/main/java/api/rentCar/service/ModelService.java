package api.rentCar.service;

import api.rentCar.entity.Vehicle;
import api.rentCar.entity.Model;
import api.rentCar.repository.RepositoryVehicle;
import api.rentCar.repository.RepositoryModel;
import api.rentCar.rest.dto.VehicleDto;
import api.rentCar.rest.dto.ModelDto;
import api.rentCar.rest.request.RequestModel;
import api.rentCar.rest.response.ResponseModel;
import api.rentCar.rest.response.ResponseVehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModelService {
    @Autowired
    private RepositoryModel repositoryModel;
    @Autowired
    private RepositoryVehicle repositoryVehicle;

    public ResponseModel createValueVehicle(RequestModel requestModel){

        Model model = new Model();
        model.setModel(requestModel.getModel());
        model.setModelYear(requestModel.getModelYear());
        model.setFabricator(requestModel.getFabricator());
        model.setCategory(requestModel.getCategory());
        repositoryModel.save(model);

        return new ResponseModel(ModelDto.builder()
                .id(model.getId())
                .model(model.getModel())
                .modelYear(model.getModelYear())
                .fabricator(model.getFabricator())
                .category(model.getCategory())
                .build());

    }
    public List<ResponseModel> listVehicle(){
        List <Model> models = repositoryModel.findAll();
        List<ResponseModel> responseModels = new ArrayList<>();


        models.forEach(model -> {

            ResponseModel responseModel =new ResponseModel(ModelDto.builder()
                    .id(model.getId())
                    .model(model.getModel())
                    .modelYear(model.getModelYear())
                    .fabricator(model.getFabricator())
                    .category(model.getCategory())
                    .build());

            responseModels.add(responseModel);
        });
        return responseModels;
    }

    public ResponseModel updateModel(RequestModel requestModel){

        Model model = repositoryModel.getReferenceById(requestModel.getId());

        model.setModel(requestModel.getModel());
        model.setModelYear(requestModel.getModelYear());
        model.setFabricator(requestModel.getFabricator());
        model.setCategory(requestModel.getCategory());
        repositoryModel.save(model);

        return new ResponseModel(ModelDto.builder()
                .id(model.getId())
                .model(model.getModel())
                .modelYear(model.getModelYear())
                .fabricator(model.getFabricator())
                .category(model.getCategory())
                .build());
    }
}
