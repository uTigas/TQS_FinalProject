package tqsgroup.chuchu.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqsgroup.chuchu.data.entity.Connection;
import tqsgroup.chuchu.data.entity.Ticket;
import tqsgroup.chuchu.data.repository.TicketRepository;
import tqsgroup.chuchu.data.repository.neo.ConnectionRepository;

import java.util.List;
import java.util.UUID;

@Service
public class TicketService {

    private static final long MIN_PRICE = 0L;
    private static final long MAX_PRICE = 100_000L;

    final SeatReservationService reservationService;

    final TicketRepository ticketRepository;

    @Autowired
    private ConnectionRepository connectionRepository;

    public TicketService(TicketRepository ticketRepository, SeatReservationService reservationService) {
        this.ticketRepository = ticketRepository;
        this.reservationService = reservationService;
    }

    public Ticket saveTicket(Ticket ticket) {
        checkValidRoute(ticket);
        Double totalPrice = calculateTotalPrice(ticket.getRoute());
        checkValidPrice(totalPrice);
        ticket.setTotalPrice(totalPrice);
        return ticketRepository.save(ticket);
    }

    public List<Ticket> findAllByUserName(String username) {
        return ticketRepository.findAllByUserUsername(username);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    // Helper methods
    private void checkValidRoute(Ticket ticket) {
        if (ticket.getRoute() == null || ticket.getRoute().size() == 0) {
            throw new IllegalArgumentException("Ticket must have at least one seat reservation in the route");
        }

        // Check if all connections from seat reservation connect with each other
        for (int i = 0; i < ticket.getRoute().size() - 1; i++) {
            if (!reservationService.isConnectionValid(ticket.getRoute().get(i),ticket.getRoute().get(i + 1))) {
                throw new IllegalArgumentException("Seat reservations must be consecutive in the route");
            }
        }
    }

    public Double calculateTotalPrice(List<UUID> route) {
        Double totalPrice = 0.0;
        for (UUID co : route) {
            Connection con = connectionRepository.findById(co).get();
            totalPrice += con.getPrice();
        }
        return totalPrice;
    }

    private void checkValidPrice(Double price) {
        if (price < MIN_PRICE || price > MAX_PRICE) {
            throw new IllegalArgumentException("Price must be between " + MIN_PRICE + " and " + MAX_PRICE + " inclusive");
        }
    }
}
