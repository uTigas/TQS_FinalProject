package tqsgroup.chuchu.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import tqsgroup.chuchu.data.entity.Connection;
import tqsgroup.chuchu.data.entity.Station;
import tqsgroup.chuchu.data.service.ConnectionService;
import tqsgroup.chuchu.data.service.StationService;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PublicRestControllerTemplateIT {
    
    @Autowired
    private MockMvc mockMvc;

    private static final String PUBLIC_API = "/public/api/v1";

    @MockBean
    private StationService stationService;

    @MockBean
    private ConnectionService connectionService;

    @BeforeEach
    void setUp() {
        Station station1 = new Station("Station 1", 2);
        Station station2 = new Station("Station 2", 3);
        when(stationService.getAllStations()).thenReturn(List.of(station1, station2));
        when(stationService.getStationByName("Station 1")).thenReturn(station1);
        when(stationService.getStationByName("Station 2")).thenReturn(station2);

        Connection connection1 = new Connection(station1, station2,null, LocalTime.of( 12, 0), LocalTime.of( 13, 0),1, 100);
        Connection connection2 = new Connection(station2, station1,null, LocalTime.of( 14, 0), LocalTime.of( 15, 0),2, 200);
        Connection connection3 = new Connection(station1, station2,null, LocalTime.of( 16, 0), LocalTime.of( 17, 0),3, 300);
        Connection connection4 = new Connection(station2, station1,null, LocalTime.of( 18, 0), LocalTime.of( 19, 0),4, 400);
        Connection connection5 = new Connection(station1, station2,null, LocalTime.of( 20, 0), LocalTime.of( 21, 0),1, 500);

        when(connectionService.getAllConnections()).thenReturn(List.of(connection1, connection2, connection3, connection4, connection5));
        when(connectionService.getArrivals(station1, 10, any())).thenReturn(List.of(connection1, connection3, connection5));
        when(connectionService.getArrivals(station2, 10,any())).thenReturn(List.of(connection2, connection4));
        when(connectionService.getArrivalsFromTrack(station1, 5, 1,any())).thenReturn(List.of(connection1, connection5));
    }

    @Test
    @Order(1)
    void whenGetStations_thenReturnsStations() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PUBLIC_API + "/stations"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2));
    }

    @Test
    @Order(2)
    void whenGetStations_thenReturnsCorrectStationNames() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PUBLIC_API + "/stations"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", is("Station 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", is("Station 2")));
    }

    @Test
    @Order(3)
    void whenGetArrivals_thenReturnsArrivals() throws Exception {
        String stationName = "Station 1";
        mockMvc.perform(MockMvcRequestBuilders.get(PUBLIC_API + "/arrivals" + "?stationName=" + stationName + "&limit=10&time=12:00:00"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(3));
    }

    @Test
    @Order(4)
    void whenGetArrivals_thenReturnsCorrectArrivalTimes() throws Exception {
        String stationName = "Station 1";
        mockMvc.perform(MockMvcRequestBuilders.get(PUBLIC_API + "/arrivals" + "?stationName=" + stationName + "&limit=10&time=12:00:00"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].arrivalTime", is("13:00:00")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].arrivalTime", is("17:00:00")));
    }

    @Test
    @Order(5)
    void whenGetArrivalsFromLine_thenReturnsArrivalsFromLine() throws Exception {
        String stationName = "Station 1";
        int lineNumber = 1;
        mockMvc.perform(MockMvcRequestBuilders.get(PUBLIC_API + "/arrivals/line" + "?stationName=" + stationName + "&lineNumber=" + lineNumber + "&limit=5&time=12:00:00"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2));
    }

}
