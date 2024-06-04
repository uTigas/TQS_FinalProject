package tqsgroup.chuchu.repository;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import tqsgroup.chuchu.data.entity.Ticket;
import tqsgroup.chuchu.data.repository.TicketRepository;
import tqsgroup.chuchu.data.service.TicketService;

public class TicketRepositoryTest {

    @Mock
    private TicketRepository ticketRepositoryMock;

    @InjectMocks
    private TicketService ticketService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByUser() {
        String username = "User";
        List<Ticket> tickets = Arrays.asList(new Ticket(), new Ticket());
        when(ticketRepositoryMock.findAllByUserUsername(username)).thenReturn(tickets);

        List<Ticket> result = ticketService.findAllByUserName(username);

        assertNotNull(result);
        assertEquals(tickets.size(), result.size());
    }

    @Test
    public void testInvalidSave() {
        Ticket ticket = new Ticket();

        assertThrows(IllegalArgumentException.class, () -> {
            ticketService.saveTicket(ticket);
        });
    }
}
