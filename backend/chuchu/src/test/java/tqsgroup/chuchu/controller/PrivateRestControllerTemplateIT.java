package tqsgroup.chuchu.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import tqsgroup.chuchu.data.entity.Ticket;
import tqsgroup.chuchu.data.entity.User;
import tqsgroup.chuchu.data.repository.neo.ConnectionRepository;
import tqsgroup.chuchu.data.service.TicketService;
import tqsgroup.chuchu.data.service.UserService;
import tqsgroup.chuchu.user.RestApi;

@WebMvcTest(RestApi.class)
public class PrivateRestControllerTemplateIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConnectionRepository connectionRepository;

    @MockBean
    private TicketService ticketService;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFetchUser() throws Exception {
        User user = new User();
        user.setUsername("testUser");
        when(userService.findByUsername(any()).get()).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/private/api/v1/user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", is("testUser")));
    }

    @Test
    void testBuyTicket() throws Exception {
        UUID ticketId = UUID.randomUUID();
        Ticket ticket = new Ticket();
        when(ticketService.saveTicket(any())).thenReturn(ticket);

        mockMvc.perform(MockMvcRequestBuilders.post("/private/api/v1/tickets")
                .content("{ \"price\": 10.0, \"connections\": [] }")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(ticketId.toString())));
    }

    @Test
    void testGetTickets() throws Exception {
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        List<Ticket> tickets = Arrays.asList(ticket1, ticket2);
        when(ticketService.findAllByUserName(any())).thenReturn(tickets);

        mockMvc.perform(MockMvcRequestBuilders.get("/private/api/v1/tickets")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", notNullValue()));
    }
}