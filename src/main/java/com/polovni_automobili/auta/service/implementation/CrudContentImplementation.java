package com.polovni_automobili.auta.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polovni_automobili.auta.config.JwtTokenProvider;
import com.polovni_automobili.auta.domain.*;
import com.polovni_automobili.auta.dto.ApiResponse;
import com.polovni_automobili.auta.dto.CarDto;
import com.polovni_automobili.auta.repository.*;
import com.polovni_automobili.auta.service.CrudContentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.error.Mark;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class CrudContentImplementation implements CrudContentService {

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

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Override
    public ResponseEntity<ApiResponse> kreiraj_marku(String name) {
        if ( this.markaRepository.existsByName( name ) )
        {
            log.error( ">>>>>>>>>> Brand name is already in use" );

            return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Name in use" ), HttpStatus.BAD_REQUEST );
        }
        final Marka newBrand = new Marka( name );
        final Marka save = this.markaRepository.save( newBrand );
        return new ResponseEntity< ApiResponse >( new ApiResponse( true, "" + save.getId() ), HttpStatus.CREATED );

    }

    @Override
    public ResponseEntity<ApiResponse> update_marku(Long id, String name) {

        final Optional< Marka > optional = this.markaRepository.findById( id );
        if ( !optional.isPresent() )
        {
            log.error( ">>>>>>>>>>Nemoguce pronaci marku sa id-jem {}", id );
            return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Nemoguce pronaci marku " ), HttpStatus.BAD_REQUEST );
        }
        // ? if there are brands with the same name, they will be here
        final List< Marka > findByNameAndIdNot = this.markaRepository.findByNameAndIdNot( name, id );
        if ( findByNameAndIdNot.size() != 0 )
        {
            return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Ime Marke je u upotrebi" ), HttpStatus.BAD_REQUEST );
        }

        final Marka marka = optional.get();

        marka.setName( name );
        this.markaRepository.save( marka );
        return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Updated" ), HttpStatus.OK );

    }

    @Override
    public ResponseEntity<ApiResponse> delete_marka(Long id) {
        final List< Automobil > cars = this.carRepository.findByMarkaId(id);
        if ( cars.size() != 0 )
        {
            log.error( ">>>>>>>>>> Nemoguce izbrisati auto sa id-jem {}, jer postoje jos auta sa istom markom", id );
            return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Nemoguce izbrisati auto sa id-jem " + id + ",  jer postoje jos auta sa istom markom" ),
                    HttpStatus.BAD_REQUEST );
        }

        markaRepository.deleteById( id );
        return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Izbrisano!" ), HttpStatus.OK );

    }




    @Override
    public ResponseEntity<ApiResponse> kreiraj_model(String name) {
        log.info( ">>>>>>>>>> Checking if there are model with the same name" );
        if ( this.modelRepository.existsByName( name ) )
        {
            log.error( ">>>>>>>> Name is in use" );
            return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Name is in use" ), HttpStatus.BAD_REQUEST );
        }
        final Model newModel = new Model( name );
        final Model save = this.modelRepository.save( newModel );

        return new ResponseEntity< ApiResponse >( new ApiResponse( true, "" + save.getId() ), HttpStatus.CREATED );
    }

    @Override
    public ResponseEntity<ApiResponse> update_model(Long id, String name) {
        final Optional< Model > optional = this.modelRepository.findById( id );
        if ( !optional.isPresent() )
        {
            log.error( ">>>>>>>>>> Cant find model with id {}", id );
            return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find model with id " + id ), HttpStatus.BAD_REQUEST );
        }
        final List< Model > checkList = this.modelRepository.findByNameAndIdNot( name, id );
        if ( checkList.size() != 0 )
        {
            log.error( ">>>>>>>>>> The name {} is already in use", name );
            return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Name is in use" ), HttpStatus.BAD_REQUEST );
        }

        final Model model = optional.get();
        model.setName( name );
        this.modelRepository.save( model );
        return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Updated" ), HttpStatus.OK );
    }

    @Override
    public ResponseEntity<ApiResponse> delete_model(Long id) {
        final Optional< Model > carOptional = this.modelRepository.findById( id );
        if ( !carOptional.isPresent() )
        {
            log.error( ">>>>>>>>>> Cant find model with id {}", id );
            return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find model" ), HttpStatus.BAD_REQUEST );
        }

        if ( this.carRepository.existsByModelId( id ) )
        {
            log.error( ">>>>>>>>>> There are cars with this model" );
            return new ResponseEntity< ApiResponse >( new ApiResponse( false, "There are cars with this model" ), HttpStatus.BAD_REQUEST );
        }
        this.modelRepository.deleteById( id );
        return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Deleted" ), HttpStatus.OK );

    }




    @Override
    public ResponseEntity<ApiResponse> kreiraj_karoseriju(String name) {
        if ( this.karoserijaRepository.existsByName( name ) )
        {
            log.error( ">>>>>>>>>> Car class with name {} already exists", name );
            return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Name in user" ), HttpStatus.BAD_REQUEST );

        }

        final Karoserija newClass = new Karoserija( name );

        final Karoserija save = this.karoserijaRepository.save( newClass );

        return new ResponseEntity< ApiResponse >( new ApiResponse( true, "" + save.getId() ), HttpStatus.CREATED );

    }

    @Override
    public ResponseEntity<ApiResponse> update_karoserija(Long id, String name) {
        if ( !this.karoserijaRepository.existsById( id ) )
        {
            log.error( ">>>>>>>>>> Cant find car class with id {}", id );
            return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find car class" ), HttpStatus.BAD_REQUEST );
        }

        if ( this.karoserijaRepository.findByNameAndIdNot( name, id ).size() != 0 )
        {
            log.error( ">>>>>>>>>> Name in use" );
            return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Name in use" ), HttpStatus.BAD_REQUEST );
        }

        final Karoserija carClass = this.karoserijaRepository.findById( id ).get();

        carClass.setName( name );

        this.karoserijaRepository.save( carClass );
        return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Updated" ), HttpStatus.OK );

    }

    @Override
    public ResponseEntity<ApiResponse> delete_karoserija(Long id) {
        if ( !this.karoserijaRepository.existsById( id ) )
        {
            log.error( ">>>>>>>>>> Cant find car class" );
            return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find" ), HttpStatus.BAD_REQUEST );

        }

        if ( this.carRepository.existsByKaroserijaId(id))
        {
            log.error( ">>>>>>>>> Cars with that class exist!" );
            return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cars with that car class still exist" ), HttpStatus.BAD_REQUEST );

        }
        this.karoserijaRepository.deleteById( id );
        return new ResponseEntity< ApiResponse >(new ApiResponse( true, "Deleted"), HttpStatus.OK );

    }





    @Override
    public ResponseEntity<ApiResponse> kreiraj_gorivo(String name) {
        if ( this.gorivoRepository.existsByName( name ) )
        {
            log.error( ">>>>>>>>>> NAme in use" );
            return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Name in use" ), HttpStatus.BAD_REQUEST );
        }

        final Gorivo fuel = new Gorivo( name );

        final Gorivo save = this.gorivoRepository.save( fuel );
        return new ResponseEntity< ApiResponse >( new ApiResponse( true, "" + save.getId() ), HttpStatus.CREATED );

    }

    @Override
    public ResponseEntity<ApiResponse> update_gorivo(Long id, String name) {
        if ( !this.gorivoRepository.existsById( id ) )
        {
            log.error( ">>>>>>>>> Cant find fuel with id {}", id );
            return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find fuel" ), HttpStatus.BAD_REQUEST );
        }
        if ( this.gorivoRepository.findByNameAndIdNot( name, id ).size() != 0 )
        {
            log.error( ">>>>>>>>>> Name is in use" );
            return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Name in use" ), HttpStatus.BAD_REQUEST );
        }
        final Gorivo fuelType = this.gorivoRepository.findById( id ).get();
        fuelType.setName( name );
        this.gorivoRepository.save( fuelType );
        return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Updated" ), HttpStatus.OK );

    }

    @Override
    public ResponseEntity<ApiResponse> delete_gorivo(Long id) {
        if ( !this.gorivoRepository.existsById( id ) )
        {
            log.error( ">>>>>>>>> Cant find fuel with id {}", id );
            return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find fuel" ), HttpStatus.BAD_REQUEST );
        }

        if ( this.carRepository.existsByGorivoId( id ) )
        {
            log.error( ">>>>>>>>> Cars with that fuel exist!" );
            return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cars with that fuel still exist" ), HttpStatus.BAD_REQUEST );
        }
        this.gorivoRepository.deleteById( id );
        return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Removed" ), HttpStatus.OK );
    }




    @Override
    public ResponseEntity<ApiResponse> create_menjac(String name) {
        if ( this.menjacRepository.existsByName( name ) )
        {
            log.error( ">>>>>>>>>> NAme in use" );
            return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Name in use" ), HttpStatus.BAD_REQUEST );
        }

        final Menjac transmission = new Menjac( name );

        final Menjac save = this.menjacRepository.save( transmission );
        return new ResponseEntity< ApiResponse >( new ApiResponse( true, "" + save.getId() ), HttpStatus.CREATED );

    }

    @Override
    public ResponseEntity<ApiResponse> update_menjac(Long id, String name) {
        if ( !this.menjacRepository.existsById( id ) )
        {
            log.error( ">>>>>>>>>> Cant find transmission" );
            return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find transmission" ), HttpStatus.BAD_REQUEST );
        }

        if ( this.menjacRepository.findByNameAndIdNot( name, id ).size() != 0 )
        {
            log.error( ">>>>>>>>>> Name in use" );
            return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Name in use" ), HttpStatus.BAD_REQUEST );

        }
        final Menjac transmission = this.menjacRepository.findById( id ).get();
        transmission.setName( name );
        this.menjacRepository.save( transmission );
        return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Updated" ), HttpStatus.OK );
    }

    @Override
    public ResponseEntity<ApiResponse> delete_menjac(Long id) {
        if ( !this.menjacRepository.existsById( id ) )
        {
            log.error( ">>>>>>>>>> Cant find transmission" );
            return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find transmission" ), HttpStatus.BAD_REQUEST );

        }
        if ( this.carRepository.existsByMenjacId( id ))
        {
            log.error( ">>>>>>>>>> There are cars with that transmission" );
            return new ResponseEntity< ApiResponse >( new ApiResponse( false, " There are cars with that transmission" ), HttpStatus.BAD_REQUEST );
        }
        this.menjacRepository.deleteById( id );
        return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Removed" ), HttpStatus.OK );

    }






    @Override
    public ResponseEntity<ApiResponse> create_city(String name, Integer zip) {
        if ( this.cityRepository.existsByName( name ) )
        {
            log.error( ">>>>>>>>>> Name is in use" );
            return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Name in use" ), HttpStatus.BAD_REQUEST );
        }


        if (zip != null && this.cityRepository.existsByZip( zip ) )
        {
            log.error( ">>>>>>>>>> ZIp is in use" );
            return new ResponseEntity< ApiResponse >( new ApiResponse( false, "ZIP is in use" ), HttpStatus.BAD_REQUEST );
        }
        final City city = new City();

        city.setName( name );
        city.setZip( zip );

        this.cityRepository.save( city );

        return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Created" ), HttpStatus.CREATED );

    }

    @Override
    public ResponseEntity<ApiResponse> update_city(Long id, String name, Integer zip) {
        if ( !this.cityRepository.existsById( id ) )
        {
            log.error( ">>>>>>>>>> CAnt find city" );
            return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find city" ), HttpStatus.BAD_REQUEST );
        }

        if ( this.cityRepository.existsByNameAndIdNot( name, id ) )
        {
            log.error( ">>>>>>>>>> Name is in use" );
            return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Name in use" ), HttpStatus.BAD_REQUEST );
        }

        if ( this.cityRepository.existsByZipAndIdNot( zip, id ) )
        {
            log.error( ">>>>>>>>>> ZIp is in use" );
            return new ResponseEntity< ApiResponse >( new ApiResponse( false, "ZIP is in use" ), HttpStatus.BAD_REQUEST );
        }

        final City city = this.cityRepository.findById( id ).get();

        city.setName( name );
        city.setZip( zip );

        this.cityRepository.save( city );

        return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Updated" ), HttpStatus.OK );

    }

    @Override
    public ResponseEntity<ApiResponse> remove_city(Long id) {
        if ( !this.cityRepository.existsById( id ) )
        {
            log.error( ">>>>>>>>>> Cant find city" );
            return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find city" ), HttpStatus.BAD_REQUEST );

        }

        this.cityRepository.deleteById( id );
        return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Removed" ), HttpStatus.OK );

    }





    @Override                               //Long user,
    public ResponseEntity<ApiResponse> create_car(String token, MultipartFile[] files, String carstr) {
        try
        {
            final ObjectMapper objectMapper = new ObjectMapper();
            final CarDto car = objectMapper.readValue( carstr, CarDto.class );


            // ! check if model is present and other stuff
            if ( !this.markaRepository.existsById(car.getMarka()) )
            {
                log.error( "Cant find brand" );
                return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find brand" ), HttpStatus.BAD_REQUEST );
            }

            if ( !this.modelRepository.existsById( car.getModel() ) )
            {
                log.error( "Cant find model" );
                return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find model" ), HttpStatus.BAD_REQUEST );
            }

            if ( !this.karoserijaRepository.existsById( car.getKaroserija() ) )
            {
                log.error( "Cant find car class" );
                return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find car class" ), HttpStatus.BAD_REQUEST );
            }

            if ( !this.cityRepository.existsById( car.getCityId() ) )
            {
                log.error( "Cant find city" );
                return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find city" ), HttpStatus.BAD_REQUEST );
            }

            if ( !this.gorivoRepository.existsById( car.getGorivo() ) )
            {
                log.error( "Cant find fuel type" );
                return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find fuel type" ), HttpStatus.BAD_REQUEST );
            }

            if ( !this.menjacRepository.existsById( car.getMenjac() ) )
            {
                log.error( "Cant find transmission" );
                return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find transmission" ), HttpStatus.BAD_REQUEST );
            }

            Long id = tokenProvider.getUserIdFromJWT(token);

            String text = "";

            final Automobil newCar = new Automobil();
            try
            {
                for ( final MultipartFile file : files )
                {
                    final SlikaAuta carPicture = new SlikaAuta();
                    carPicture.setData( file.getBytes() );
                    newCar.getImages().add( carPicture );
                    final SlikaAuta save = this.imageRepository.save( carPicture );
                    text = text.concat( "" + save.getId() ) + ";";
                }
            }
            catch ( final Exception e )
            {

                return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Error with images jebene slike" ), HttpStatus.INTERNAL_SERVER_ERROR );
            }

            newCar.setMarka( this.markaRepository.findById( car.getMarka() ).get() );
            newCar.setKaroserija( this.karoserijaRepository.findById( car.getKaroserija() ).get() );
            newCar.setBroj_sjedista( car.getBroj_sjedista() );
            newCar.setCity( this.cityRepository.findById( car.getCityId() ).get() );
            newCar.setGorivo( this.gorivoRepository.findById( car.getGorivo() ).get() );
            newCar.setModel( this.modelRepository.findById( car.getModel() ).get() );
            newCar.setCijena( car.getCijena() );
            newCar.setOpis(car.getOpis());
            newCar.setKonjska_snaga(car.getKonjska_snaga());
            newCar.setKm(car.getKm());
            newCar.setGod(car.getGod());
            newCar.setKubikaza(car.getKubikaza());
            newCar.setOwner(id);
            newCar.setMenjac( this.menjacRepository.findById( car.getMenjac() ).get() );

            final Automobil save = this.carRepository.save( newCar );

            return new ResponseEntity< ApiResponse >( new ApiResponse( true, "" + save.getId() + "_" + text ), HttpStatus.CREATED );
        }
        catch ( final Exception e )
        {
            return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Error with json object" ), HttpStatus.INTERNAL_SERVER_ERROR );

        }

    }

    @Override
    public ResponseEntity<ApiResponse> update_car( MultipartFile[] files, String carParam) {
        try
        {
            final ObjectMapper objectMapper = new ObjectMapper();
            final CarDto car = objectMapper.readValue( carParam, CarDto.class );
            Optional< Automobil > optional = this.carRepository.findById( car.getId() );

            if ( !optional.isPresent() )
            {
                return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find car" ), HttpStatus.BAD_REQUEST );
            }

            // ! check if model is present and other stuff
            if ( !this.markaRepository.existsById( car.getMarka() ) ) {
                log.error( "Cant find brand" );
                return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find brand" ), HttpStatus.BAD_REQUEST );
            }
            if ( !this.modelRepository.existsById( car.getModel() ) ) {
                log.error( "Cant find model" );
                return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find model" ), HttpStatus.BAD_REQUEST );
            }
            if ( !this.karoserijaRepository.existsById( car.getKaroserija() ) )
            {
                log.error( "Cant find car class" );
                return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find car class" ), HttpStatus.BAD_REQUEST );
            }

            if ( !this.cityRepository.existsById( car.getCityId() ) )
            {
                log.error( "Cant find city" );
                return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find city" ), HttpStatus.BAD_REQUEST );
            }

            if ( !this.gorivoRepository.existsById( car.getGorivo() ) )
            {
                log.error( "Cant find fuel type" );
                return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find fuel type" ), HttpStatus.BAD_REQUEST );
            }

            if ( !this.menjacRepository.existsById( car.getMenjac() ) )
            {
                log.error( "Cant find transmission" );
                return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find transmission" ), HttpStatus.BAD_REQUEST );
            }

            String text = "";

            final Automobil newCar = optional.get();

            if ( files.length > 0 )
            {
                try
                {
                    Set< SlikaAuta > images = newCar.getImages();

                    this.imageRepository.deleteAll( images );
                    newCar.getImages().clear();
                    for ( final MultipartFile file : files )
                    {
                        final SlikaAuta carPicture = new SlikaAuta();

                        carPicture.setData( file.getBytes() );
                        newCar.getImages().add( carPicture );
                        final SlikaAuta save = this.imageRepository.save( carPicture );
                        text = text.concat( "" + save.getId() ) + ";";
                    }
                }
                catch ( final Exception e )
                {

                    return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Error with images" ), HttpStatus.INTERNAL_SERVER_ERROR );
                }
            }

            newCar.setMarka( this.markaRepository.findById( car.getMarka() ).get());
            newCar.setKaroserija( this.karoserijaRepository.findById( car.getKaroserija() ).get() );
            newCar.setBroj_sjedista( car.getBroj_sjedista() );
            newCar.setCity( this.cityRepository.findById( car.getCityId() ).get() );
            newCar.setGorivo( this.gorivoRepository.findById( car.getGorivo() ).get() );
            newCar.setModel( this.modelRepository.findById( car.getModel() ).get() );
            newCar.setCijena( car.getCijena() );
            newCar.setOpis(car.getOpis());
            newCar.setKonjska_snaga(car.getKonjska_snaga());
            newCar.setKm(car.getKm());
            newCar.setGod(car.getGod());
            newCar.setKubikaza(car.getKubikaza());
            newCar.setMenjac( this.menjacRepository.findById( car.getMenjac() ).get() );

            final Automobil save = this.carRepository.save( newCar );
            return new ResponseEntity< ApiResponse >( new ApiResponse( true, "" + save.getId() + "_" + text ), HttpStatus.CREATED );
        }
        catch (
                final Exception e )
        {
            return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Error with json object" ), HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    @Override
    public ResponseEntity<ApiResponse> delete_car(Long car) {

            Automobil car2remove = this.carRepository.findById( car ).get();
            if(car2remove != null){
                this.carRepository.deleteById(car);
                return new ResponseEntity< ApiResponse >( new ApiResponse( true, "Removed" ), HttpStatus.OK );
            }

            return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Cant find car!" ), HttpStatus.BAD_REQUEST );

    }
}
