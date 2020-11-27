package com.polovni_automobili.auta.repository;


import com.polovni_automobili.auta.domain.RoleTypes;
import com.polovni_automobili.auta.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);
    Boolean existsByEmail( String email );
    Set< User > findByRolesName(RoleTypes role );
    List< User > findByIdIn(Set< Long > userIds );
}
