package dev.oxelab.apitest.resources;

import dev.oxelab.apitest.domain.User;
import dev.oxelab.apitest.domain.dto.UserDTO;
import dev.oxelab.apitest.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/user")
public class UserResource {

    @Autowired
    private UserService service;
    @Autowired
    private ModelMapper mapper;

    @GetMapping(value="/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Integer id){
        return ResponseEntity.ok().body(mapper.map(service.findById(id),UserDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll(){
        return ResponseEntity
                .ok()
                .body(service.findAll().stream()
                        .map(dto -> mapper.map(dto,UserDTO.class))
                        .collect(Collectors.toList())
    );
    }
    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO dto){
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(service.create(dto).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
//new User(1,"Miguel","miguel@mail.com","123")
}
