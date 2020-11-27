package com.polovni_automobili.auta.repository;

import com.polovni_automobili.auta.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long>{


    Optional<City> findById(Long id);

    Boolean existsByName(final String name );

    Boolean existsByZip( final Integer zip );

    Boolean existsByNameAndIdNot( final String name, final Long id );

    Boolean existsByZipAndIdNot( final Integer zip, final Long id );

}
