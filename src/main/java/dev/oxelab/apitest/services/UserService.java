package dev.oxelab.apitest.services;

import dev.oxelab.apitest.domain.User;

import java.util.List;

public interface UserService {

    User findById(Integer id);
    List<User> findAll();
}
