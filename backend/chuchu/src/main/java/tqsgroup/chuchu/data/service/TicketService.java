package tqsgroup.chuchu.data.service;

import org.springframework.stereotype.Service;

import tqsgroup.chuchu.data.entity.SeatReservation;
import tqsgroup.chuchu.data.entity.Ticket;
import tqsgroup.chuchu.data.repository.TicketRepository;

import java.util.List;

@Service
public class TicketService {

    private static final long MIN_PRICE = 0L;
    private static final long MAX_PRICE = 100_000L;

    final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket saveTicket(Ticket ticket) {
        checkValidRoute(ticket);
        Long totalPrice = calculateTotalPrice(ticket.getRoute());
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
        if (ticket.getRoute() == null || ticket.getRoute().length == 0) {
            throw new IllegalArgumentException("Ticket must have at least one seat reservation in the route");
        }

        // Check if all connections from seat reservation connect with each other
        for (int i = 0; i < ticket.getRoute().length - 1; i++) {
            if (!ticket.getRoute()[i].isConnectionValid(ticket.getRoute()[i + 1])) {
                throw new IllegalArgumentException("Seat reservations must be consecutive in the route");
            }
        }
    }

    private long calculateTotalPrice(SeatReservation[] route) {
        long totalPrice = 0;
        for (SeatReservation seatReservation : route) {
            totalPrice += seatReservation.getSeatPrice();
        }
        return totalPrice;
    }

    private void checkValidPrice(Long price) {
        if (price < MIN_PRICE || price > MAX_PRICE) {
            throw new IllegalArgumentException("Price must be between " + MIN_PRICE + " and " + MAX_PRICE + " inclusive");
        }
    }
}
