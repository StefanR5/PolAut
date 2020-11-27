package com.polovni_automobili.auta.repository;

import com.polovni_automobili.auta.domain.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JwtTokenRepository extends JpaRepository<JwtToken, String> {

    Optional<JwtToken> findByToken(String token);

}
