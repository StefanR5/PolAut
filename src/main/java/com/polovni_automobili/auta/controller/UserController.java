package com.polovni_automobili.auta.controller;


import com.polovni_automobili.auta.domain.Automobil;
import com.polovni_automobili.auta.domain.User;
import com.polovni_automobili.auta.dto.ApiResponse;
import com.polovni_automobili.auta.dto.Entities;
import com.polovni_automobili.auta.dto.UserDTO;
import com.polovni_automobili.auta.exception.UserNotFoundException;
import com.polovni_automobili.auta.service.CrudContentService;
import com.polovni_automobili.auta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    private UserService userService;
    private CrudContentService crudContentService;


    @Autowired
    public UserController(UserService userService,CrudContentService crudContentService) {
        this.userService = userService;
        this.crudContentService = crudContentService;
    }


    //---------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse> register(@RequestBody User us) {
        return userService.register(us);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User u) {
        return userService.login(u);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> readUser(@PathVariable(value = "id") long id) throws UserNotFoundException {
        User us = userService.read(id);
        if(us == null){
            throw new UserNotFoundException("User with id: " + id + "not found");
        }
        UserDTO dto = new UserDTO(us);

        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(@RequestBody User user){
        System.out.println(user.toString());
        User saved = userService.create(user);
        return new ResponseEntity<>(new UserDTO(saved), HttpStatus.OK);
    }

    //---------------------------------------------------------------------------------------------------------------


    @GetMapping( "/userCars" )
    public Page<Automobil> get_users_cars(@RequestParam final Long owner , @RequestParam final Integer page, @RequestParam final Integer size) {
        return this.userService.get_users_cars( owner, page, size);
    }


    @GetMapping("/carDetails")
    public Optional<Automobil> get_DetailsOfCar(@RequestParam final Long id){
        return this.userService.idDetailsOfCar(id);
    }





    @PostMapping( "/create" )               // @RequestHeader( required = true, value = "id" ) final String user
    public ResponseEntity<ApiResponse> create_car(@RequestParam final String token, @RequestParam( "files" ) final MultipartFile[] files, @RequestParam final String car ) {
        return this.crudContentService.create_car( token, files, car );
    }
    @PutMapping( "/update" )
    public ResponseEntity< ApiResponse > update_car(@RequestParam( "files" ) final MultipartFile[] files, @RequestParam final String car ) {
        return this.crudContentService.update_car( files, car );
    }
    @DeleteMapping( "/car/{id}" )
    public ResponseEntity< ApiResponse > deleteCar(@PathVariable(value = "id") Long id) {
        return this.crudContentService.delete_car( id );
    }



    //---------------------------------------------------------------------------------------------------------------
    @ExceptionHandler(UserNotFoundException.class)
    public void handlePostNotFound(UserNotFoundException exception, HttpServletResponse response) throws IOException {
        response.sendError( HttpStatus.NOT_FOUND.value(), exception.getMessage() );
    }

}
