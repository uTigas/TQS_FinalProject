package tqsgroup.chuchu.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import tqsgroup.chuchu.admin.dao.StationDAO;
import tqsgroup.chuchu.data.entity.Role;
import tqsgroup.chuchu.data.entity.User;
import tqsgroup.chuchu.data.entity.Station;
import tqsgroup.chuchu.data.repository.StationRepository;
import tqsgroup.chuchu.data.repository.UserRepository;
import tqsgroup.chuchu.data.repository.RoleRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdminRestControllerTemplateIT {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private static final String ADMIN_API = "/admin/api/v1";

    private String getUrl() {
        return "http://localhost:" + randomServerPort;
    }

    private String authenticateAndGetCookie() {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("username", "admin");
        requestBody.add("password", "password");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> authenticationResponse = restTemplate.postForEntity(
            getUrl() + "/auth/login",
            requestEntity,
            String.class
        );

        String cookie = authenticationResponse.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        return cookie;
    }

    private void createTestStation(String name, int numberOfLines) {
        Station station = new Station(name, numberOfLines);
        stationRepository.save(station);
    }

    @BeforeEach
    void setUp() {
        stationRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();

        Role adminRole = new Role("ADMIN");
        roleRepository.save(adminRole);

        User adminUser = new User("admin", "Admin User", "$2a$10$GiseHkdvwOFr7A9KRWbeiOmg/PYPhWVjdm42puLfOzR/gIAQrsAGy", adminRole);
        userRepository.save(adminUser);

        createTestStation("station 1", 5);
        createTestStation("station 2", 3);
        createTestStation("station 3", 7);
    }

    @Test
    @Order(1)
    void testGetHelloEndpoint() {
        String cookie = authenticateAndGetCookie();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, cookie);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
            getUrl() + ADMIN_API + "/hello",
            HttpMethod.GET,
            requestEntity,
            String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Hello from SpringBoot Admin Rest API Controller"));
    }

    @Test
    @Order(2)
    void whenValidStation_thenCreateStation() {
        String cookie = authenticateAndGetCookie();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, cookie);
        headers.setContentType(MediaType.APPLICATION_JSON);

        StationDAO station = new StationDAO("new station", 10);
        HttpEntity<StationDAO> requestEntity = new HttpEntity<>(station, headers);

        ResponseEntity<StationDAO> response = restTemplate.exchange(
            getUrl() + ADMIN_API + "/stations",
            HttpMethod.POST,
            requestEntity,
            StationDAO.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @Order(3)
    void givenStations_whenGetStations_thenStatus200() {
        String cookie = authenticateAndGetCookie();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, cookie);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<StationDAO[]> response = restTemplate.exchange(
            getUrl() + ADMIN_API + "/stations",
            HttpMethod.GET,
            requestEntity,
            StationDAO[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().length);
    }
}
