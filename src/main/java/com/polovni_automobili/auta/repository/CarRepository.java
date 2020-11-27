package com.polovni_automobili.auta.repository;

import com.polovni_automobili.auta.domain.Automobil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface CarRepository extends JpaRepository<Automobil, Long> , QuerydslPredicateExecutor<Automobil> {

    Set< Automobil > findByIdIn(Set< Long > cars );
    Set< Automobil > findAllByOwner(Long owner);
    boolean existsByMarkaId(Long id);
    boolean existsByModelId(Long id);
    boolean existsByGorivoId(Long id);
    boolean existsByMenjacId(Long id);
    boolean existsByKaroserijaId(Long id);

    List< Automobil > findByMarkaId(Long markaId);
}
