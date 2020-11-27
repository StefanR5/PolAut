package com.polovni_automobili.auta.repository;

import com.polovni_automobili.auta.domain.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {

    Boolean existsByName( String name );
    List<Model> findByNameAndIdNot(String name, Long id);

}


