package com.polovni_automobili.auta.service;


import com.polovni_automobili.auta.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface CrudContentService {


    ResponseEntity<ApiResponse> kreiraj_marku(final String name );
    ResponseEntity< ApiResponse > update_marku( final Long id, final String name );
    ResponseEntity< ApiResponse > delete_marka( final Long id );


    ResponseEntity< ApiResponse > kreiraj_model( final String name );
    ResponseEntity< ApiResponse > update_model( final Long id, final String name );
    ResponseEntity< ApiResponse > delete_model( final Long id );


    ResponseEntity< ApiResponse > kreiraj_karoseriju( final String name );
    ResponseEntity< ApiResponse > update_karoserija( final Long id, final String name );
    ResponseEntity< ApiResponse > delete_karoserija( final Long id );



    ResponseEntity< ApiResponse > kreiraj_gorivo( final String name );
    ResponseEntity< ApiResponse > update_gorivo( final Long id, final String name );
    ResponseEntity< ApiResponse > delete_gorivo( final Long id );


    ResponseEntity< ApiResponse > create_menjac( final String name );
    ResponseEntity< ApiResponse > update_menjac( final Long id, final String name );
    ResponseEntity< ApiResponse > delete_menjac( final Long id );


    ResponseEntity< ApiResponse > create_city( final String name, final Integer zip );
    ResponseEntity< ApiResponse > update_city(Long id, final String name, final Integer zip );
    ResponseEntity< ApiResponse > remove_city( final Long id );

                                            //final Long user,
    ResponseEntity< ApiResponse > create_car(String token, final MultipartFile[] files, final String car );
    ResponseEntity< ApiResponse > update_car(  final MultipartFile[] files, String car );
    ResponseEntity< ApiResponse > delete_car(  final Long car );




}
