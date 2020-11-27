package com.polovni_automobili.auta.repository;

import com.polovni_automobili.auta.domain.Menjac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenjacRepository extends JpaRepository<Menjac, Long> {

    Boolean existsByName( String name );
    List< Menjac > findByNameAndIdNot(String name, Long id );
}
