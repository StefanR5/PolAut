package com.polovni_automobili.auta.repository;

import com.polovni_automobili.auta.domain.SlikaAuta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<SlikaAuta, Long> {
}
