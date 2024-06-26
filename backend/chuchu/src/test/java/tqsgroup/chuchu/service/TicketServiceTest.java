package tqsgroup.chuchu.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import tqsgroup.chuchu.data.entity.*;
import tqsgroup.chuchu.data.repository.TicketRepository;
import tqsgroup.chuchu.data.repository.neo.ConnectionRepository;
import tqsgroup.chuchu.data.service.SeatReservationService;
import tqsgroup.chuchu.data.service.TicketService;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock(lenient = true)    
    private ConnectionRepository connectionRepository;
    
    @Mock(lenient = true)
    private TicketRepository ticketRepository;

    @Mock(lenient = true)
    private SeatReservationService reservationService;

    @InjectMocks
    private TicketService ticketService;

    Carriage carriage = new Carriage(CarriageType.NORMAL, 50, new Train(TrainType.URBAN, 2));
    Seat seat = new Seat(1, carriage);
    Station station1 = new Station("D", 4);
    Station station2 = new Station("E", 5);
    Station station3 = new Station("F", 6);
    Station station4 = new Station("G", 7);
    Train train = new Train(TrainType.ALPHA, 3);

    Connection con1 = new Connection(station1, station2, train, LocalTime.of(14, 0), LocalTime.of(15, 0), 3, 1L);
    Connection con2 = new Connection(station2, station3, train, LocalTime.of(15, 0), LocalTime.of(16, 0), 3, 2L);
    Connection con3 = new Connection(station3, station4, train, LocalTime.of(16, 0), LocalTime.of(17, 0), 3, 3L);

    List<UUID> validRoute = new ArrayList<>(Arrays.asList(con1.getId(), con2.getId(), con3.getId()));
    List<UUID> validRoute2 = new ArrayList<>(Arrays.asList(con1.getId(), con2.getId()));
    List<UUID> emptyRoute = new ArrayList<>();
    List<UUID> invalidRoute = new ArrayList<>(Arrays.asList(con1.getId(), con3.getId()));

    Role role = new Role("role");
    User u = new User("user", "password", "email", role);

    Ticket validTicket = new Ticket(validRoute, u);
    Ticket validTicket2 = new Ticket(validRoute2, u);
    Ticket emptyTicket = new Ticket(emptyRoute, u);
    Ticket invalidTicket = new Ticket(invalidRoute, u);

    List<Ticket> validTickets = Arrays.asList(validTicket, validTicket2);


    @Test
    void whenSaveTicketWithEmptyRoute_thenTicketShouldNotBeSaved() {
        try {
            ticketService.saveTicket(emptyTicket);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Ticket must have at least one seat reservation in the route");
        }
        verify(ticketRepository, never()).save(emptyTicket);
    }

    @Test
    void whenSaveTicketWithInvalidRoute_thenTicketShouldNotBeSaved() {
        try {
            when(reservationService.isConnectionValid(invalidTicket.getRoute().get(0), invalidTicket.getRoute().get(1))).thenReturn(false);
            ticketService.saveTicket(invalidTicket);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Seat reservations must be consecutive in the route");
        }
        verify(ticketRepository, never()).save(invalidTicket);
    }

    @Test
    void whenFindByUserId_thenTicketShouldBeReturned() {
        when(ticketRepository.findAllByUserUsername(u.getUsername())).thenReturn(validTickets);
        assertThat(ticketService.findAllByUserName(u.getUsername())).isEqualTo(validTickets);
        verify(ticketRepository, times(1)).findAllByUserUsername(u.getUsername());
    }

    @Test
    void whenGetAllTickets_thenAllTicketsShouldBeReturned() {
        when(ticketRepository.findAll()).thenReturn(validTickets);
        assertThat(ticketService.getAllTickets()).isEqualTo(validTickets);
        verify(ticketRepository, times(1)).findAll();
    }
}
