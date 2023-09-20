package api.rentCar.service;

import api.rentCar.domains.entity.Model;
import api.rentCar.domains.model.ModelDto;
import api.rentCar.domains.repository.RepositoryModel;
import api.rentCar.domains.repository.RepositoryVehicle;
import api.rentCar.exceptions.handlers.HandlerDataIntegrityViolationException;
import api.rentCar.exceptions.handlers.HandlerEntitydadeNotFoundException;
import api.rentCar.exceptions.handlers.HandlerErrorException;
import api.rentCar.rest.request.RequestModel;
import api.rentCar.rest.response.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    public ResponseModel updateModel(RequestModel requestModel, Long idModel) {

        try {
            Model model = repositoryModel.findById(idModel)
                    .orElseThrow(() -> new HandlerEntitydadeNotFoundException("entity with id "+ idModel+" not found"));

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

        } catch (HandlerEntitydadeNotFoundException ex) {
            throw new HandlerEntitydadeNotFoundException(ex.getMessage());
        }catch (Exception ex){
            throw new HandlerErrorException(ex.getMessage());
        }
    }
    public ResponseModel delete(Long idModel){
        try {
            Model model = repositoryModel.findById(idModel)
                    .orElseThrow(() -> new HandlerEntitydadeNotFoundException("entity with id "+ idModel+" not found"));

            repositoryModel.delete(model);

            ResponseModel responseModel = new ResponseModel();

            return responseModel;

        }catch (DataIntegrityViolationException ex){
            throw new HandlerDataIntegrityViolationException("Could not be deleted because there are related entities");
        }catch (HandlerEntitydadeNotFoundException ex) {
            throw new HandlerEntitydadeNotFoundException(ex.getMessage());
        }catch (Exception ex){
            throw new HandlerErrorException(ex.getMessage());
        }
    }

}
