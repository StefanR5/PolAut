package com.polovni_automobili.auta.service;


import com.polovni_automobili.auta.domain.Automobil;
import com.polovni_automobili.auta.domain.User;
import com.polovni_automobili.auta.dto.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserService {

    ResponseEntity< ? > login(User user);
    ResponseEntity<ApiResponse> register(User user);

    User findByEmail(String email);
    Iterable<User> listOfUsers();
    User create(User user);
    User read(long id);
    User update(long id,User user);
    void delete(long id);

    Page<Automobil> get_users_cars(Long owner, Integer page, Integer size);

    Optional<Automobil> idDetailsOfCar(Long id);

    User updateRole(long id, String rola);
}
