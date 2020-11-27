package com.polovni_automobili.auta.service.implementation;

import com.polovni_automobili.auta.domain.User;
import com.polovni_automobili.auta.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class MyUserDetailsService  implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("Could not found user  " + username);
        }
        return UserDetailsServiceImpl.create(user);
    }




    @Transactional
    public UserDetails loadUserById( Long id )
    {
        User user = userRepository.findById( id ).orElseThrow( () -> new UsernameNotFoundException( "User not found with id : " + id ) );

        return UserDetailsServiceImpl.create( user );

    }

}
