package dev.oxelab.apitest.resources.exceptions;

import dev.oxelab.apitest.services.exceptions.DataIntegratyViolationException;
import dev.oxelab.apitest.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ResourceExceptionHandlerTest {
    public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";
    public static final String EMAIL_JA_ENCONTRADO = "Email já encontrado!";
    @InjectMocks
    private ResourceExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenObjectNotFoundExcpetionThenReturnAResponseEntity() {
        ResponseEntity<StandardError> response = exceptionHandler
                .objectNotFound(
                        new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO),
                        new MockHttpServletRequest()
                );


        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertEquals(ResponseEntity.class,response.getClass());
        assertEquals(StandardError.class,response.getBody().getClass());
        assertEquals(OBJETO_NAO_ENCONTRADO,response.getBody().getError());
        assertEquals(404,response.getBody().getStatus());
        assertNotEquals("/user/2",response.getBody().getPath());
        assertNotEquals(LocalDateTime.now(),response.getBody().getTimestamp());
    }

    @Test
    void dataIntegrityViolationException() {
        ResponseEntity<StandardError> response = exceptionHandler
                .dataIntegrityViolationException(
                        new DataIntegratyViolationException(EMAIL_JA_ENCONTRADO),
                        new MockHttpServletRequest()
                );
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertEquals(ResponseEntity.class,response.getClass());
        assertEquals(StandardError.class,response.getBody().getClass());
        assertEquals(EMAIL_JA_ENCONTRADO,response.getBody().getError());
        assertEquals(400,response.getBody().getStatus());

    }
}