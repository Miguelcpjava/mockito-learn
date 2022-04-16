package dev.oxelab.apitest.config;

import dev.oxelab.apitest.domain.User;
import dev.oxelab.apitest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private UserRepository repository;
    @Bean
public void startDB(){
        User u1 = new User(null,"Miguel","miguel@mail.com","123");
        User u2 = new User(null,"Joao","joao@mail.com","123");

        repository.saveAll(List.of(u1,u2));
    }
}
