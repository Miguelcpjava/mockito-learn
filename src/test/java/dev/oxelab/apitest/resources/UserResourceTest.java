package dev.oxelab.apitest.resources;

import dev.oxelab.apitest.domain.User;
import dev.oxelab.apitest.domain.dto.UserDTO;
import dev.oxelab.apitest.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserResourceTest {

    public static final int ID          = 1;
    public static final String NAME     = "Miguel";
    public static final String EMAIL    = "miguel@mail.com";
    public static final String PASSWORD = "123";
    public static final int INDEX = 0;


    @InjectMocks
    private UserResource resource;
    @Mock
    private UserServiceImpl service;
    @Mock
    private ModelMapper mapper;

    private User user;

    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenFindByIdThenReturnWithSuccess() {
        when(service.findById(anyInt())).thenReturn(user);
        when(mapper.map(any(),any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = resource.findById(ID);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class,response.getClass());
        assertEquals(ResponseEntity.class,response.getClass());
        assertEquals(UserDTO.class,response.getBody().getClass());
        assertEquals(ID,response.getBody().getId());
        assertEquals(EMAIL,response.getBody().getEmail());
        assertEquals(NAME,response.getBody().getName());
        assertEquals(PASSWORD,response.getBody().getPassword());
    }

    @Test
    void whenFindAllThenReturnAListOfUserDTO() {
        /* Para mocar o mapper, ou melhor a tranformaçao do mapper no objeto
        que é necessario, informa o metodo any, pois tras qualquer objeto para
        ser tranformado em um objeto UserDTO.
         */
        when(service.findAll()).thenReturn(List.of(user));
        when(mapper.map(any(),any())).thenReturn(userDTO);

        ResponseEntity<List<UserDTO>> response = resource.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(ResponseEntity.class,response.getClass());
        assertEquals(ArrayList.class,response.getBody().getClass());
        assertEquals(UserDTO.class,response.getBody().get(INDEX).getClass());
        assertEquals(ID,response.getBody().get(INDEX).getId());
        assertEquals(NAME,response.getBody().get(INDEX).getName());
        assertEquals(EMAIL,response.getBody().get(INDEX).getEmail());
        assertEquals(PASSWORD,response.getBody().get(INDEX).getPassword());
    }

    @Test
    void whenCreateThenReturnCreated() {
        /* Neste caso é mocado o servico de criacao do usuario
        onde passado qualquer objeto é obrigado a retorno um objeto user.
         */
        when(service.create(any())).thenReturn(user);

        ResponseEntity response = resource.create(userDTO);

        assertEquals(ResponseEntity.class,response.getClass());
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @Test
    void whenUpdateThenReturnSucess() {
        /* Para mocar a chamada utiliza o when, o objeto dessa classe vai
        depender da construção original, neste caso precisou chamar o updte passando
        o objeto userDTO e esperando que o retorno traga um objeto user.
         */
        when(service.update(userDTO)).thenReturn(user);
        when(mapper.map(any(),any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = resource.update(ID,userDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class,response.getClass());
        assertEquals(ID,response.getBody().getId());
        assertEquals(NAME,response.getBody().getName());
        assertEquals(EMAIL,response.getBody().getEmail());
    }

    @Test
    void whenDeleteThenReturnSuccess() {
        /* Aqui eu estou mockando a chamada do método delete passando algum id,
        que é a varivale que o método solicita, um Inteiro.
        O doNothing é ppara casos em que o metodo é do tipo void, ou seja,
        não há um retorno explicito
         */
        doNothing().when(service).delete(anyInt());
        ResponseEntity<UserDTO> response = resource.delete(ID);

        assertNotNull(response);
        assertEquals(ResponseEntity.class,response.getClass());

        assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
        verify(service, times(1)).delete(anyInt());
    }

    private void startUser(){
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
    }
}