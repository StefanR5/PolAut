package com.polovni_automobili.auta.repository;

import com.polovni_automobili.auta.domain.Karoserija;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KaroserijaRepository extends JpaRepository<Karoserija, Long> {

    Boolean existsByName( String name );
    List< Karoserija > findByNameAndIdNot(String name, Long id );

}
