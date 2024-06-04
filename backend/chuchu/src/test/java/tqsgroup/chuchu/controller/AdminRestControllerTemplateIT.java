package tqsgroup.chuchu.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import tqsgroup.chuchu.authentication.service.SecurityService;
import tqsgroup.chuchu.admin.dao.StationDAO;
import tqsgroup.chuchu.data.entity.Station;
import tqsgroup.chuchu.data.repository.UserRepository;
import tqsgroup.chuchu.data.repository.neo.StationRepository;
import tqsgroup.chuchu.data.repository.RoleRepository;
import tqsgroup.chuchu.data.repository.SeatRepository;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdminRestControllerTemplateIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SecurityService securityService;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    SeatRepository seatRepository;


    private static final String ADMIN_API = "/admin/api/v1";
    
    
    private void createTestStation(String name, int numberOfLines) {
        Station station = new Station(name, numberOfLines);
        stationRepository.save(station);
    }
    
    @BeforeEach
    void setUp() {
        when(securityService.hasRole("ADMIN")).thenReturn(true);
        
        when(securityService.hasRole("ADMIN")).thenReturn(true);
        
        createTestStation("station 1", 5);
        createTestStation("station 2", 3);
        createTestStation("station 3", 7);
    }

    @AfterEach
    void tearDown() {
        stationRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    @Order(1)
    void testGetHelloEndpoint() {
        RestAssuredMockMvc.given().mockMvc(mockMvc)
                          .when().get(ADMIN_API + "/hello")
                          .then().statusCode(HttpStatus.OK.value())
                          .body(is("Hello from SpringBoot Admin Rest API Controller"));
        RestAssuredMockMvc.given().mockMvc(mockMvc)
                          .when().get(ADMIN_API + "/hello")
                          .then().statusCode(HttpStatus.OK.value())
                          .body(is("Hello from SpringBoot Admin Rest API Controller"));
    }

    @Test
    @Order(2)
    void whenValidStation_thenCreateStation() {
        RestAssuredMockMvc.given().mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .body("{\"name\": \"new station\", \"numberOfLines\": 10}")
                .when().post(ADMIN_API + "/stations")
                .then().statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @Order(3)
    void givenStations_whenGetStations_thenStatus200() {
        RestAssuredMockMvc.given().mockMvc(mockMvc)
                .when().get(ADMIN_API + "/stations")
                .then().statusCode(HttpStatus.OK.value())
                .body("$.size()", is(3));
    }

    @Test
    @Order(4)
    void givenStations_whenEditStation_thenStatus200() {
        String nameToEdit = "station 1";
        Station stationToEdit = stationRepository.findByName(nameToEdit);
        StationDAO editedStation = new StationDAO(nameToEdit + " edited", stationToEdit.getNumberOfLines() + 1);
        
        RestAssuredMockMvc.given().mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .body(editedStation)
                .when().put(ADMIN_API + "/stations/" + stationToEdit.getName())
                .then().statusCode(HttpStatus.OK.value());
    }

    @Test
    @Order(5)
    void givenStations_whenEditBadStation_thenStatus400() {
        String nameToEdit = "station 1";
        Station stationToEdit = stationRepository.findByName(nameToEdit);
        StationDAO editedStation = new StationDAO(nameToEdit + " edited", 0);
        
        RestAssuredMockMvc.given().mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .body(editedStation)
                .when().put(ADMIN_API + "/stations/" + stationToEdit.getName())
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @Order(6)
    void givenNoStation_whenEdit_thenStatus404() {
        String nameToEdit = "non-existing station";
        RestAssuredMockMvc.given().mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .body(new StationDAO(nameToEdit, 4))
                .when().put(ADMIN_API + "/stations/" + nameToEdit)
                .then().statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @Order(7)
    void givenStations_whenEditConflict_thenStatus409() {
        String nameToEdit = "station 1";
        Station stationToEdit = stationRepository.findByName(nameToEdit);
        StationDAO editedStation = new StationDAO("station 2", stationToEdit.getNumberOfLines());
        
        RestAssuredMockMvc.given().mockMvc(mockMvc)
                .contentType(ContentType.JSON)
                .body(editedStation)
                .when().put(ADMIN_API + "/stations/" + stationToEdit.getName())
                .then().statusCode(HttpStatus.CONFLICT.value());
    }
}