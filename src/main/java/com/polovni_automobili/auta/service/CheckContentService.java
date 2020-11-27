package com.polovni_automobili.auta.service;

import com.polovni_automobili.auta.domain.*;
import com.polovni_automobili.auta.dto.SearchDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CheckContentService {

    ResponseEntity<List<Marka>> get_all_marke();
    ResponseEntity< List<Model> > get_all_models();
    ResponseEntity< List<Karoserija> > get_all_karoserije();
    ResponseEntity<List<Gorivo>> get_all_gorivo();
    ResponseEntity< List<Menjac> > daj_sve_menjace();
    ResponseEntity< List< City > > get_cities();

    Optional<City> getCityfromId(Long id);

    ResponseEntity<List <Automobil> > get_all_cars();

    Page< Automobil > get_cars_page(final Integer page, final Integer size, String sort );




    Page< Automobil > searchCars(SearchDto search, Long markaId, Long modelId, Long gorivoId, Long menjacId,
                                 Long karoserijaId,Integer broj_sjedista,Long cityId,
                                 String godina,Integer kilometraza, Integer kubikaza, Integer snaga, String sort, Integer page,
                                 Boolean ascending);



}
