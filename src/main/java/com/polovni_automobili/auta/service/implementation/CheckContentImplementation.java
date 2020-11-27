package com.polovni_automobili.auta.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polovni_automobili.auta.domain.*;
import com.polovni_automobili.auta.dto.CarDto;
import com.polovni_automobili.auta.dto.SearchDto;
import com.polovni_automobili.auta.dto.SearchFormDto;
import com.polovni_automobili.auta.repository.*;
import com.polovni_automobili.auta.service.CheckContentService;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CheckContentImplementation implements CheckContentService {


    @Autowired
    private MarkaRepository markaRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private GorivoRepository gorivoRepository;

    @Autowired
    private KaroserijaRepository karoserijaRepository;

    @Autowired
    private MenjacRepository menjacRepository;

    @Autowired
    private ImageRepository imageRepository;




    @Override
    public ResponseEntity<List<Marka>> get_all_marke() {
        log.info( ">>>>>>>>>> Retrieving brands" );
        return new ResponseEntity< List< Marka > >( this.markaRepository.findAll( Sort.by( "name" ).ascending() ), HttpStatus.OK );

    }

    @Override
    public ResponseEntity<List<Model>> get_all_models() {
        log.info( ">>>>>>>>>> Retrieving models" );
        return new ResponseEntity< List< Model > >( this.modelRepository.findAll(), HttpStatus.OK );
    }

    @Override
    public ResponseEntity<List<Karoserija>> get_all_karoserije() {
        log.info( ">>>>>>>>>> Retrieving car classes" );
        return new ResponseEntity< List< Karoserija > >( this.karoserijaRepository.findAll(), HttpStatus.OK );
    }

    @Override
    public ResponseEntity<List<Gorivo>> get_all_gorivo() {
        log.info( ">>>>>>>>>> Retrieving fuel types" );
        return new ResponseEntity< List< Gorivo > >( this.gorivoRepository.findAll(), HttpStatus.OK );
    }

    @Override
    public ResponseEntity<List<Menjac>> daj_sve_menjace() {
        log.info( ">>>>>>>>>> Retrieving transmissions" );
        return new ResponseEntity< List< Menjac > >( this.menjacRepository.findAll(), HttpStatus.OK );
    }

    @Override
    public ResponseEntity<List<City>> get_cities() {
        return new ResponseEntity< List< City > >( this.cityRepository.findAll( Sort.by( "name" ).ascending() ), HttpStatus.OK );
    }

    @Override
    public Optional<City> getCityfromId(Long id) {
        return cityRepository.findById(id);
    }

    @Override
    public ResponseEntity<List<Automobil>> get_all_cars() {
        return new ResponseEntity<List<Automobil>>(this.carRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public Page<Automobil> get_cars_page(Integer page, Integer size, String sort) {

        //final BooleanBuilder booleanBuilder = new BooleanBuilder();
        //booleanBuilder.and( QAutomobil.automobil.owner.eq( id ) );

        final Page< Automobil > carsPage = this.carRepository.findAll(PageRequest.of( page - 1, size, Sort.by( sort ) ) );
        return carsPage;
    }




    @Override
    public Page<Automobil> searchCars(SearchDto search, Long markaId, Long modelId, Long gorivoId, Long menjacId,
                                      Long karoserijaId,Integer broj_sjedista,Long cityId,
                                      String godina,Integer kilometraza, Integer kubikaza, Integer snaga, String sort, Integer page, Boolean ascending) {


        final BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(markaId != null){
           booleanBuilder.and( QAutomobil.automobil.marka.id.eq( markaId ) );
        }
        if ( modelId != null ) {
            booleanBuilder.and( QAutomobil.automobil.model.id.eq( modelId ) );
        }
        if ( gorivoId != null ) {
            booleanBuilder.and( QAutomobil.automobil.gorivo.id.eq( gorivoId ) );
        }
        if ( menjacId != null ) {
            booleanBuilder.and( QAutomobil.automobil.menjac.id.eq( menjacId ) );
        }
        if(karoserijaId != null){
            booleanBuilder.and( QAutomobil.automobil.karoserija.id.eq( karoserijaId ) );
        }
        if ( cityId != null ) {
            booleanBuilder.and( QAutomobil.automobil.city.id.eq( cityId ) );
        }
        if(broj_sjedista != null){
            booleanBuilder.and(QAutomobil.automobil.broj_sjedista.goe(broj_sjedista));
        }
        if(kubikaza != null){
            booleanBuilder.and(QAutomobil.automobil.kubikaza.goe(kubikaza));
        }
        if(godina != null){
            booleanBuilder.and(QAutomobil.automobil.god.goe(godina));
        }
        if(kilometraza != null){
            booleanBuilder.and(QAutomobil.automobil.km.goe(kilometraza));
        }
        if(snaga != null){
            booleanBuilder.and(QAutomobil.automobil.konjska_snaga.goe(snaga));
        }
        if(search.getStart() != null && search.getEnd() != null){
            booleanBuilder.and(QAutomobil.automobil.cijena.between(search.getStart(),search.getEnd()));
        }


        Page<Automobil> cars;
        if ( sort != null )
        {
            final Direction direction = ascending ? Direction.ASC : Direction.DESC;
            cars = this.carRepository.findAll( booleanBuilder, PageRequest.of( page - 1, 10, Sort.by( direction, sort ) ) );

        }
        cars = this.carRepository.findAll( booleanBuilder, PageRequest.of( page - 1, 10 ) );

        return cars;

    }
}
