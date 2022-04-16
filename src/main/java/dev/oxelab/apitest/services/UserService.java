package dev.oxelab.apitest.services;

import dev.oxelab.apitest.domain.User;

public interface UserService {

    User findById(Integer id);
}
