package api.rentCar.service;

import api.rentCar.domains.entity.Model;
import api.rentCar.domains.model.ModelDto;
import api.rentCar.domains.repository.RepositoryModel;
import api.rentCar.exceptions.handlers.HandlerDataIntegrityViolationException;
import api.rentCar.exceptions.handlers.HandlerEntityNotFoundException;
import api.rentCar.exceptions.handlers.HandlerErrorException;
import api.rentCar.rest.request.TypesToSearch;
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

    public ResponseModel createValueVehicle(ModelDto request){
        try {
            Model model = new Model(request);
            repositoryModel.save(model);

            return new ResponseModel(new ModelDto(model));
        }catch (Exception ex){
            throw new HandlerErrorException(ex.getMessage());
        }
    }
    public List<ResponseModel> listVehicle(TypesToSearch filter){
        try {
            List <Model> models = repositoryModel.findAll();
            List<ResponseModel> responseModels = new ArrayList<>();

            models.stream().filter(model ->
                            model.getId().equals(filter.getId())||
                            model.getModel().toLowerCase().contains(filter.getModel().toLowerCase()) ||
                            model.getCategory().equals(filter.getCategory())
                    )
                    .forEach(model -> {

                ResponseModel responseModel = new ResponseModel(new ModelDto(model));
                responseModels.add(responseModel);
            });
            return responseModels;
        }catch (Exception ex){
            throw new HandlerErrorException(ex.getMessage());
        }
    }

    public ResponseModel updateModel(ModelDto request, Long idModel) {

            Model model = repositoryModel.findById(idModel)
                    .orElseThrow(() -> new HandlerEntityNotFoundException("entity with id "+ idModel+" not found"));
        try {
            model.setModel(request.getModel());
            model.setModelYear(request.getModelYear());
            model.setFabricator(request.getFabricator());
            model.setCategory(request.getCategory());
            repositoryModel.save(model);

            return new ResponseModel(new ModelDto(model));

        } catch (Exception ex){
            throw new HandlerErrorException(ex.getMessage());
        }
    }
    public void delete(Long idModel){
        Model model = repositoryModel.findById(idModel)
                .orElseThrow(() -> new HandlerEntityNotFoundException("entity with id "+ idModel+" not found"));
        try {
            repositoryModel.deleteById(model.getId());

        }catch (DataIntegrityViolationException ex){
            throw new HandlerDataIntegrityViolationException(ex.getMessage());
        }catch (Exception ex){
            throw new HandlerErrorException(ex.getMessage());
        }
    }
}
