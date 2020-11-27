package com.polovni_automobili.auta.controller;

import com.polovni_automobili.auta.domain.*;
import com.polovni_automobili.auta.dto.ApiResponse;
import com.polovni_automobili.auta.dto.Entities;
import com.polovni_automobili.auta.dto.RoleDto;
import com.polovni_automobili.auta.service.CrudContentService;
import com.polovni_automobili.auta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/admin")
public class AdminController {

    private CrudContentService crudContentService;
    private UserService userService;

    @Autowired
    public AdminController(CrudContentService crudContentService, UserService userService) {
        this.crudContentService = crudContentService;
        this.userService = userService;
    }



    //@PutMapping("/update/{id}")
    //public User updateRole(@PathVariable(value="id") long id, @RequestParam final String rola){
      //  return userService.updateRole(id, rola);
    //}
    @PutMapping("/update/{id}")
    public User updateRole(@PathVariable(value="id") long id, @RequestBody RoleDto roles){
        return userService.updateRole(id, roles.getName());
    }


    @PutMapping("/{id}")
    public User update(@PathVariable(value="id") long id, @RequestBody User user){
        return userService.update(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable(value = "id") int id){
        userService.delete(id);
    }


    @GetMapping("/")
    public ResponseEntity<Entities> listOFUsers(){
        Entities result = new Entities();
        ArrayList<User> us = new ArrayList<>();

        Iterable<User> list = userService.listOfUsers();
        for (User user: list) {
            us.add(user);
        }
        result.setEntities(us);
        result.setTotal(us.size());
        System.out.println(result.toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }





    //@RequestMapping(value = "/marka/name", method = RequestMethod.POST)
    @PostMapping( "/marka" )
    public ResponseEntity<ApiResponse> create_brand(@RequestBody Marka marka ) {
        return this.crudContentService.kreiraj_marku( marka.getName() );
    }
    @PutMapping( "/marka/{id}" )
    public ResponseEntity< ApiResponse > update_brand( @RequestBody Marka marka, @PathVariable(value="id") Long id ) {
        return this.crudContentService.update_marku( id, marka.getName() );
    }
    @DeleteMapping( "/marka/{id}" )
    public ResponseEntity< ApiResponse > delete_brand( @PathVariable final Long id ) {
        return this.crudContentService.delete_marka( id );
    }



    @PostMapping( "/model" )
    public ResponseEntity< ApiResponse > create_model( @RequestBody Model model) {
        return this.crudContentService.kreiraj_model( model.getName() );
    }
    @PutMapping( "/model/{id}" )
    public ResponseEntity< ApiResponse > update_model( @RequestBody Model model, @PathVariable(value="id") Long id ) {
        return this.crudContentService.update_model( id, model.getName() );
    }
    @DeleteMapping( "/model/{id}" )
    public ResponseEntity< ApiResponse > delete_model( @PathVariable final Long id) {
        return this.crudContentService.delete_model( id );

    }



    @PostMapping( "/karoserija" )
    public ResponseEntity< ApiResponse > create_car_class(@RequestBody Karoserija karoserija) {
        return this.crudContentService.kreiraj_karoseriju( karoserija.getName() );
    }
    @PutMapping( "/karoserija/{id}" )
    public ResponseEntity< ApiResponse > update_car_class( @RequestBody Karoserija karoserija, @PathVariable(value="id") Long id) {
        return this.crudContentService.update_karoserija( id, karoserija.getName() );
    }
    @DeleteMapping( "/karoserija/{id}" )
    public ResponseEntity< ApiResponse > delete_car_class( @PathVariable final Long id ) {
        return this.crudContentService.delete_karoserija( id );

    }


    @PostMapping( "/gorivo" )
    public ResponseEntity< ApiResponse > create_car_fuel(@RequestBody Gorivo gorivo) {
        return this.crudContentService.kreiraj_gorivo( gorivo.getName() );
    }
    @PutMapping( "/gorivo/{id}" )
    public ResponseEntity< ApiResponse > update_car_fuel( @RequestBody Gorivo gorivo, @PathVariable(value="id") Long id ) {
        return this.crudContentService.update_gorivo( id, gorivo.getName() );
    }
    @DeleteMapping( "/gorivo/{id}" )
    public ResponseEntity< ApiResponse > delete_car_fuel( @PathVariable final Long id ) {
        return this.crudContentService.delete_gorivo( id );

    }


    @PostMapping( "/menjac" )
    public ResponseEntity< ApiResponse > create_car_transmission(@RequestBody Menjac menjac) {
        return this.crudContentService.create_menjac( menjac.getName() );
    }
    @PutMapping( "/menjac/{id}" )
    public ResponseEntity< ApiResponse > update_car_transmission( @RequestBody Menjac menjac, @PathVariable(value="id") Long id  ) {
        return this.crudContentService.update_menjac( id, menjac.getName() );
    }
    @DeleteMapping( "/menjac/{id}" )
    public ResponseEntity< ApiResponse > delete_car_transmission( @PathVariable final Long id ) {
        return this.crudContentService.delete_menjac( id );
    }



    @PostMapping( "/city" )
    public ResponseEntity< ApiResponse > create_city( @RequestBody City city) {
        return this.crudContentService.create_city(city.getName(), city.getZip() );
    }
    @PutMapping( "/city/{id}" )
    public ResponseEntity< ApiResponse > update_city( @RequestBody City city, @PathVariable(value="id") Long id ) {
        return this.crudContentService.update_city( id, city.getName(), city.getZip());
    }
    @DeleteMapping( "/city/{id}" )
    public ResponseEntity< ApiResponse > delete_city( @RequestParam final Long id ) {
        return this.crudContentService.remove_city( id );
    }




}
