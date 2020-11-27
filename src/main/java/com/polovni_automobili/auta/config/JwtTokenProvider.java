package com.polovni_automobili.auta.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.polovni_automobili.auta.domain.JwtToken;
import com.polovni_automobili.auta.repository.JwtTokenRepository;
import com.polovni_automobili.auta.service.implementation.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Autowired
    private PropertiesConfig configuration;
    @Autowired
    private JwtTokenRepository jwtTokenRepository;


    public String generateToken( Authentication authentication ) {

        UserDetailsServiceImpl userPrincipal = ( UserDetailsServiceImpl ) authentication.getPrincipal();

        //Date now = new Date();
        //Date expiryDate = new Date( now.getTime() + configuration.getJwtExpirationInMs() );

        //String token = Jwts.builder().setSubject( Long.toString( userPrincipal.getId() ) ).setIssuedAt( new Date() ).setExpiration( expiryDate )
         //       .signWith( SignatureAlgorithm.HS512, configuration.getJwtSecret() ).compact();


        String token = Jwts.builder().setSubject( Long.toString( userPrincipal.getId() ) )
                .signWith( SignatureAlgorithm.HS512, "Secret" ).compact();

        jwtTokenRepository.save( new JwtToken( token,userPrincipal.getId() ) );

        return token;

    }

    public void suspendToken( String token ) {

        Optional< JwtToken > token2delete = jwtTokenRepository.findByToken( token.substring( 0, token.length() - 1 ) );

        if ( token2delete.isPresent() )
        {
            jwtTokenRepository.delete( token2delete.get() );
        }
        return;
    }

    public boolean validateToken( String authToken ) {

        Optional< JwtToken > optionalToken = jwtTokenRepository.findById( authToken );

        if ( !optionalToken.isPresent() ) {
            return false;
        }

        try {
            Jwts.parser().setSigningKey("Secret").parseClaimsJws( authToken );
            return true;
        }
        catch ( SignatureException ex ) {
            logger.error( "Invalid JWT signature" );
        }
        catch ( MalformedJwtException ex ) {
            logger.error( "Invalid JWT token" );
        }
        catch ( ExpiredJwtException ex ) {
            logger.error( "Expired JWT token" );
        }
        catch ( UnsupportedJwtException ex ) {
            logger.error( "Unsupported JWT token" );
        }
        catch ( IllegalArgumentException ex ) {
            logger.error( "JWT claims string is empty." );
        }
        return false;

    }



    public Long getUserIdFromJWT( String token ) {
        Claims claims = Jwts.parser().setSigningKey( "Secret" ).parseClaimsJws( token ).getBody();
        return Long.parseLong( claims.getSubject() );
    }

    public Date getExpirationFromJWT( String token ) {
        Claims claims = Jwts.parser().setSigningKey( configuration.getJwtSecret() ).parseClaimsJws( token ).getBody();
        return claims.getExpiration();

    }

}
