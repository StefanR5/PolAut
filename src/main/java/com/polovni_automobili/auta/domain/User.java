package com.polovni_automobili.auta.domain;

import com.polovni_automobili.auta.dto.UserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name="users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( unique=true, nullable=false )
    private String email;

    @Column( nullable=false )
    private String password;

    private String full_name;

    @ManyToMany( cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<Role>();

    private boolean active;

    //@Column(name="rola", insertable = false, updatable = false)
    //@Enumerated(EnumType.STRING)
    //private RoleTypes roleType;

    public User(String email,String full_name){
        this.email = email;
        this.full_name = full_name;
        this.active = true;
    }



}
