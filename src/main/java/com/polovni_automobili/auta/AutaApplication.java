package com.polovni_automobili.auta;

import com.polovni_automobili.auta.domain.Role;
import com.polovni_automobili.auta.domain.RoleTypes;
import com.polovni_automobili.auta.domain.User;
import com.polovni_automobili.auta.repository.RoleRepository;
import com.polovni_automobili.auta.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class AutaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutaApplication.class, args);
    }

  @Bean
    CommandLineRunner runner(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {


      /*      Role r = new Role();
            r.setName(RoleTypes.ADMIN);
            roleRepository.save(r);

            Role r1 = new Role();
            r1.setName(RoleTypes.USER);
            roleRepository.save(r1);

            Set<Role> roles = new HashSet<>();
            roles.add(r);
            roles.add(r1);


            User us = new User();
            us.setEmail("stefa@roganovic.com");
            us.setFull_name("Stefan Roganovic");
            us.setPassword("12345");
            userRepository.save(us);

            User us1 = new User();
            us1.setEmail("marko@markovic.com");
            us1.setFull_name("Marko Markovic");
            us1.setPassword("54321");
            userRepository.save(us1);*/
/*
            //INSERT INTO polovni_automobili_back.users_roles(`user_id`, `role_id`) VALUES (1, 1)
            //INSERT INTO polovni_automobili_back.users_roles(`user_id`, `role_id`) VALUES (2, 2)


            //INSERT IGNORE INTO polovni_automobili_back.model(`id`,`name`)VALUES(5,"Lexus")

            //INSERT IGNORE INTO `polovni_automobili_back.marka`(`id`,`name`)VALUES(1,"Bugatti");
            //INSERT IGNORE INTO `polovni_automobili_back.marka`(`id`,`name`)VALUES(2,"Ferrari");
            //INSERT IGNORE INTO `polovni_automobili_back.marka`(`id`,`name`)VALUES(3,"AUDI");
            //INSERT IGNORE INTO `polovni_automobili_back.marka`(`id`,`name`)VALUES(4,"CITROEN");
            //INSERT IGNORE INTO  `polovni_automobili_back.marka`(`id`,`name`)VALUES(5,"Lexus");
            //INSERT IGNORE INTO  `polovni_automobili_back.marka`(`id`,`name`)VALUES(6,"Nissan");
*/

        };
    }

}
