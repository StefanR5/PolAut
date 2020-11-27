package com.polovni_automobili.auta.repository;

import com.polovni_automobili.auta.domain.Gorivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GorivoRepository extends JpaRepository<Gorivo, Long> {

    Boolean existsByName( String name );
    List< Gorivo > findByNameAndIdNot(String name, Long id );

}
