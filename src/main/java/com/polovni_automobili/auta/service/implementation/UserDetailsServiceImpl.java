package com.polovni_automobili.auta.service.implementation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.polovni_automobili.auta.domain.Role;
import com.polovni_automobili.auta.domain.RoleTypes;
import com.polovni_automobili.auta.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserDetailsServiceImpl implements UserDetails {

    private User user;


    private Long id;
    private String full_name;
    @JsonIgnore
    private String email;
    @JsonIgnore
    private String password;
    private Collection< ? extends GrantedAuthority > authorities;


    public UserDetailsServiceImpl( Long id, String full_name,String email, String password,
                          Collection< ? extends GrantedAuthority > authorities ) {
        this.id = id;
        this.full_name = full_name;
        this.email = email;
        this.password = password;
        this.authorities = authorities;

    }


    public static UserDetailsServiceImpl create( User user )
    {
        List< GrantedAuthority > authorities =
                user.getRoles().stream().map( role -> new SimpleGrantedAuthority( role.getName().name() ) ).collect( Collectors.toList() );

        return new UserDetailsServiceImpl( user.getId(), user.getFull_name(), user.getEmail(), user.getPassword(), authorities );

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //RoleTypes roles = user.getRoleType();
        //List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        //authorities.add(new SimpleGrantedAuthority(roles.toString()));
        return this.authorities;

    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return  this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
            return true;
        if ( o == null || getClass() != o.getClass() )
            return false;
        UserDetailsServiceImpl that = ( UserDetailsServiceImpl ) o;
        return Objects.equals( id, that.id );

    }


    @Override
    public int hashCode()
    {

        return Objects.hash( id );

    }

}

