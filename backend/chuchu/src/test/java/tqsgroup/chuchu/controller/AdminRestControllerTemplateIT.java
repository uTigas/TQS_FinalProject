package tqsgroup.chuchu.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import tqsgroup.chuchu.authentication.service.SecurityService;
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
    }

    @Test
    @Order(2)
    void whenValidStation_thenCreateStation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(ADMIN_API + "/stations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"new station\", \"numberOfLines\": 10}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @Order(3)
    void givenStations_whenGetStations_thenStatus200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ADMIN_API + "/stations"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3));
    }
}
