package com.polovni_automobili.auta.controller;


import com.polovni_automobili.auta.domain.*;
import com.polovni_automobili.auta.dto.ApiResponse;
import com.polovni_automobili.auta.dto.MessageDto;
import com.polovni_automobili.auta.dto.SearchDto;
import com.polovni_automobili.auta.service.CheckContentService;
import com.polovni_automobili.auta.service.UserService;
import com.polovni_automobili.auta.service.implementation.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/public")
public class CarController {


    private CheckContentService checkContentService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;

    @Autowired
    public CarController(CheckContentService checkContentService) {
        this.checkContentService = checkContentService;
    }


    @PostMapping("/sendMessage")
    public ResponseEntity<ApiResponse> sendMessage(@RequestBody MessageDto messageDto){
        return this.messageService.sendMessage(messageDto.getUser_id(),messageDto.getMessage());
    }



    @GetMapping( "/marke" )
    public ResponseEntity<List<Marka>> get_all_brands() {
        return this.checkContentService.get_all_marke();
    }
    @GetMapping( "/models" )
    public ResponseEntity< List<Model> > get_models() {
        return this.checkContentService.get_all_models();
    }
    @GetMapping( "/karoserije" )
    public ResponseEntity< List<Karoserija> > get_karoserije() {
        return this.checkContentService.get_all_karoserije();
    }
    @GetMapping( "/gorivo" )
    public ResponseEntity< List<Gorivo> > get_all_fuels() {
        return this.checkContentService.get_all_gorivo();
    }
    @GetMapping( "/menjac" )
    public ResponseEntity< List<Menjac> > get_all_transmissions() {
        return this.checkContentService.daj_sve_menjace();
    }
    @GetMapping( "/cities" )
    public ResponseEntity< List< City > > get_cities() {
        return this.checkContentService.get_cities();
    }


    @GetMapping("/cityId")
    public Optional<City> getCityforId(@RequestParam final Long id){
        return this.checkContentService.getCityfromId(id);
    }

    @GetMapping("/allcars")
    public ResponseEntity<List<Automobil>> get_cars() {
        return  this.checkContentService.get_all_cars();
    }

    @GetMapping( "/auta" )
    public Page< Automobil > usersCars( /*@RequestHeader( required = true, value = "id" ) final String id,*/ @RequestParam final Integer page,
                                       @RequestParam final Integer size, @RequestParam final String sort )
     {
         return this.checkContentService.get_cars_page( /*Long.parseLong( id ),*/page, size, sort );
     }




    @PostMapping( "/search" )
    public Page< Automobil > searchCars(@RequestBody( required = true ) SearchDto search, @RequestParam( required = false ) Long markaId,
                                        @RequestParam( required = false ) Long modelId, @RequestParam( required = false ) Long gorivoId, @RequestParam( required = false ) Long menjacId,
                                        @RequestParam( required = false ) Long karoserijaId, @RequestParam( required = false ) Integer broj_sjedista, @RequestParam( required = false ) Long city,
                                        @RequestParam( required = false ) String godina, @RequestParam( required = false ) Integer kilometraza,
                                        @RequestParam( required = false ) Integer kubikaza,  @RequestParam( required = false ) Integer snaga,
                                        @RequestParam( required = false ) String sort, @RequestParam( required = true ) Integer page,
                                        @RequestParam( required = false ) Boolean ascending )
    {
        return this.checkContentService.searchCars( search, markaId, modelId, gorivoId, menjacId, karoserijaId, broj_sjedista, city, godina, kilometraza, kubikaza, snaga , sort, page, ascending);

    }





}
