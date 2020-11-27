package com.polovni_automobili.auta.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
public class CarDto {

    private Long id;
    private Long marka;
    private Long model;
    private Long gorivo;
    private Long menjac;
    private Long karoserija;
    private Long cityId;

    private Integer cijena;
    private String god;
    private Integer km;
    private Integer kubikaza;
    private Integer konjska_snaga;
    private String opis;
    private Integer broj_sjedista;
    // MultipartFile[] files;



}
