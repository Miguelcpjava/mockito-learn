package dev.oxelab.apitest.services;

import dev.oxelab.apitest.domain.User;
import dev.oxelab.apitest.domain.dto.UserDTO;

import java.util.List;

public interface UserService {

    User findById(Integer id);
    List<User> findAll();
    User create(UserDTO obj);
    User update(UserDTO obj);
}
