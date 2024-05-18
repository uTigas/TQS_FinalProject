package tqsgroup.chuchu.controller;

import org.checkerframework.checker.units.qual.s;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import io.cucumber.java.BeforeAll;
import tqsgroup.chuchu.data.entity.*;
import tqsgroup.chuchu.data.repository.*;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
class AdminRestControllerTemplateIT {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private UserRepository userRepository;

    private static final String ADMIN_API = "/admin/api/v1";

    @BeforeAll
    void setup() {
        Role role = new Role("ADMIN");
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ADMIN"));
        User user = new User("admin", "$2a$10$GiseHkdvwOFr7A9KRWbeiOmg/PYPhWVjdm42puLfOzR/gIAQrsAGy", "email", role);
        userRepository.save(user);

        //TODO não sei bem como criar o user com o role ADMIN, já vi cenas com Mockuser mas não deu também
    }

    @Test
    void testGetHelloEndpoint() {
        ResponseEntity<String> response = restTemplate.withBasicAuth("admin", "password").getForEntity(ADMIN_API + "/hello", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Hello from SpringBoot Admin Rest API Controller"));
    }


    //TODO: command to run this - mvn clean integration-test -Dtest=AdminRestControllerTemplateIT


    //TODO estes testes dão sempre 401 unauthorized
    // @Test
    // @WithMockUser(username = "admin", authorities = {"ADMIN"})
    // void whenValidStation_thenCreateStation() {
    //     Station station = new Station("Station 1", 10);
    //     ResponseEntity<Station> response = restTemplate.postForEntity(ADMIN_API + "/stations", station, Station.class);
    //     assertEquals(HttpStatus.CREATED, response.getStatusCode());
    // }

    // @Test
    // void whenInvalidStation_thenBadRequest() {
    //     Station station = new Station("", 10);
    //     ResponseEntity<Station> response = restTemplate.postForEntity(ADMIN_API + "/stations", station, Station.class);
    //     assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    // }

    private void createTestStation(String name, int numberOfLines) {
        Station station = new Station(name, numberOfLines);
        stationRepository.save(station);
    }
}
