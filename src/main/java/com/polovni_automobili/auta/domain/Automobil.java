package com.polovni_automobili.auta.domain;


import com.querydsl.core.annotations.QueryEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@QueryEntity
public class Automobil {


    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @ManyToOne( fetch = FetchType.EAGER )
    private Marka marka;

    @ManyToOne( fetch = FetchType.EAGER )
    private Model model;

    @ManyToOne( fetch = FetchType.EAGER )
    private Gorivo gorivo;

    @ManyToOne( fetch = FetchType.EAGER )
    private Menjac menjac;

    @ManyToOne( fetch = FetchType.EAGER )
    private Karoserija karoserija;

    private Integer cijena;

    @ManyToOne( fetch = FetchType.EAGER )
    private City city;


    @OneToMany( fetch = FetchType.EAGER )
    @JoinColumn( name = "car_id" )
    private Set< SlikaAuta > images = new HashSet< SlikaAuta >();


    private String god;

    private Integer km;

    private Integer kubikaza;

    private Integer konjska_snaga;

    @Column(columnDefinition = "TEXT")
    private String opis;

    private Integer broj_sjedista;


    @Column( updatable = false, nullable = true )
    private Long owner;

}
