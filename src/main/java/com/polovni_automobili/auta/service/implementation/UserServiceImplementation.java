package com.polovni_automobili.auta.service.implementation;

import com.polovni_automobili.auta.config.JwtTokenProvider;
import com.polovni_automobili.auta.config.PropertiesConfig;
import com.polovni_automobili.auta.domain.*;
import com.polovni_automobili.auta.dto.ApiResponse;
import com.polovni_automobili.auta.exception.AppException;
import com.polovni_automobili.auta.repository.CarRepository;
import com.polovni_automobili.auta.repository.RoleRepository;
import com.polovni_automobili.auta.repository.UserRepository;
import com.polovni_automobili.auta.service.UserService;
import com.querydsl.core.BooleanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserServiceImplementation implements UserService{


    private static final Logger log = LoggerFactory.getLogger(UserServiceImplementation.class);

    @Autowired
    private CarRepository carRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertiesConfig configuration;


    /////////////////////////////////////REMOVE TOKEN TIMER TASK //////////////////////////////////
    private class RemoveToken extends TimerTask {
        private final String token;
        public RemoveToken( final String token ) {
            this.token = token;
        }

        @Override
        public void run() {
            log.info( ">>>> Suspending token" );
            tokenProvider.suspendToken( "Bearer " + this.token );
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public ResponseEntity< ? > login(User user) {

        final Timer timer = new Timer( true );
        final Authentication authentication =
                authenticationManager.authenticate( new UsernamePasswordAuthenticationToken( user.getEmail(), user.getPassword() ) );
        final User us = userRepository.findByEmail(user.getEmail());
        //final Role userRole = roleRepository.findByName( role ).orElseThrow( () -> new AppException( "User Role not set." ) );

        if ( us.getRoles() != null) {
            SecurityContextHolder.getContext().setAuthentication( authentication );
            final String jwt = tokenProvider.generateToken( authentication );
            //timer.schedule( new RemoveToken( jwt ), configuration.getJwtExpirationInMs() );

            return ResponseEntity.ok( new JwtToken(jwt,us.getId()) );
       }
        throw new BadCredentialsException( "Bad Credentials" );

    }

    @Override
    public ResponseEntity<ApiResponse> register(User user) {
        if(userRepository.existsByEmail(user.getEmail())){
            return new ResponseEntity< ApiResponse >( new ApiResponse( false, "Email Address already in use!" ), HttpStatus.BAD_REQUEST );
        }
        final User us = new User(user.getEmail(), user.getFull_name());
        us.setPassword(passwordEncoder.encode(user.getPassword()));
        final Role userRole = roleRepository.findByName( RoleTypes.ROLE_USER ).orElseThrow( () -> new AppException( "User Role not set." ) );
        us.getRoles().add(userRole);

        final User result = userRepository.save(us);

        return new ResponseEntity< ApiResponse >( new ApiResponse( true, "User registrated successfully!" ), HttpStatus.OK );

    }



    @Override
    public Iterable<User> listOfUsers() {
        return userRepository.findAll();
    }


    /////////////////////////CRUD////////////////////////////////
    @Override
    public User read(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User create(User user) {
       // for (Role role: user.getRoles()) {
          //  roleRepository.save(role);
       // }
        return userRepository.save(user);
    }

    @Override
    public User update(long id, User to_update) {
        User user1 = userRepository.findById(id).orElse(null);
        System.out.println("Koji se mjennja: " +user1.toString());
        System.out.println("Onaj koji mjenja: "+to_update.toString());

        if(to_update.getEmail() != null){
            user1.setEmail(to_update.getEmail());
        }else if (to_update.getPassword() != null){
            user1.setPassword(to_update.getPassword());
        }
        else if(to_update.getFull_name()!= null){
            user1.setFull_name(to_update.getFull_name());
        }else if(to_update.getRoles() != null){
            user1.setRoles(to_update.getRoles());
        }
        return userRepository.save(user1);
    }

    @Override
    public User updateRole(long id, String rola) {
        User user1 = userRepository.findById(id).orElse(null);
        Set<Role> roles = new HashSet<>();

        if(rola.equals("ROLE_ADMIN")){
            Role r = new Role();
            r.setName(RoleTypes.ROLE_ADMIN);
            r.setId((long) 1);
            roles.add(r);
        }else if(rola.equals("ROLE_USER")){
            Role r = new Role();
            r.setName(RoleTypes.ROLE_USER);
            r.setId((long) 2);
            roles.add(r);
        }
        user1.setRoles(roles);

        return  userRepository.save(user1);
    }


    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }




    @Override
    public Page<Automobil> get_users_cars(Long owner, Integer page, Integer size) {
        final BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and( QAutomobil.automobil.owner.eq( owner ) );
        final Page< Automobil > carsPage = this.carRepository.findAll(booleanBuilder,PageRequest.of( page - 1, size) );
        int num = carsPage.getSize();
        return carsPage;
    }

    @Override
    public Optional<Automobil> idDetailsOfCar(Long id) {

        final Optional<Automobil> auto = this.carRepository.findById(id);
        return auto;
    }



    //Kako ce ga pronaci
    @Override
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

}
