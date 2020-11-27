package com.polovni_automobili.auta.repository;

import com.polovni_automobili.auta.domain.Marka;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkaRepository extends JpaRepository<Marka, Long> {

    Boolean existsByName( String name );
    List< Marka > findByNameAndIdNot(String name, Long id );

}
