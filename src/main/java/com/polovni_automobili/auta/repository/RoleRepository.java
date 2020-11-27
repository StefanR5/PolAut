package com.polovni_automobili.auta.repository;

import com.polovni_automobili.auta.domain.Role;
import com.polovni_automobili.auta.domain.RoleTypes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional< Role > findByName(RoleTypes name );
    Set< Role > findByIdIn(Set< Long > ids );

}
