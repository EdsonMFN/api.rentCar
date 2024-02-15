package api.rentCar.rest.controller;


import api.rentCar.rest.request.RequestUser;
import api.rentCar.rest.request.TypesToSearch;
import api.rentCar.rest.response.ResponseUser;
import api.rentCar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ResponseUser>createUser(@RequestBody RequestUser requestUser){
        ResponseUser response = userService.createUser(requestUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping(value = "/filterUser")
    public ResponseEntity<List<ResponseUser>>findByFilter(@RequestBody TypesToSearch filter){
        List<ResponseUser> responses = userService.findByFilter(filter);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
    @GetMapping(value = "/{idUser}")
    public ResponseEntity<ResponseUser>findByUser(@PathVariable Long idUser){
        ResponseUser response = userService.findByUser(idUser);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PutMapping(value = "/{idUser}")
    public ResponseEntity<ResponseUser>updateUser(@RequestBody RequestUser requestUser, @PathVariable Long idUser){
        ResponseUser response = userService.updateUser(requestUser,idUser);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @DeleteMapping(value = "/{idUser}")
    public ResponseEntity<ResponseUser>deleteUser(@PathVariable Long idUser){
        ResponseUser response = userService.deleteUser(idUser);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @DeleteMapping(value = "/disable/{idUser}")
    public ResponseEntity<ResponseUser>disableUser(@PathVariable Long idUser){
        ResponseUser response = userService.deleteUser(idUser);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
