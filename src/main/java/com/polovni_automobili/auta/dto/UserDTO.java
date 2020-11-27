package com.polovni_automobili.auta.dto;

import com.polovni_automobili.auta.domain.Role;
import com.polovni_automobili.auta.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String email;
    private String password;
    private String fullname;
    private Set<Role> role;


    public UserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.fullname = user.getFull_name();
        this.password = user.getPassword();
        this.role = user.getRoles();
    }

}
