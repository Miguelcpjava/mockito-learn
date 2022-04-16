package dev.oxelab.apitest.services.impl;

import dev.oxelab.apitest.domain.User;
import dev.oxelab.apitest.repositories.UserRepository;
import dev.oxelab.apitest.services.UserService;
import dev.oxelab.apitest.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User findById(Integer id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!"));
    }

    public List<User> findAll(){
        return repository.findAll();
    }
}
