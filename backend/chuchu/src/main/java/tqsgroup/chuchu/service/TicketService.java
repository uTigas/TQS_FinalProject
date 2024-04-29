package tqsgroup.chuchu.service;

import org.springframework.stereotype.Service;


import tqsgroup.chuchu.data.entity.Ticket;
import tqsgroup.chuchu.data.repository.TicketRepository;

@Service
public class TicketService {
    
    final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket saveTicket(Ticket ticket) {
        checkValidRoute(ticket);
        checkValidPrice(ticket);
        return ticketRepository.save(ticket);
    }

    // Helper methods
    private void checkValidRoute(Ticket ticket) {
        if (ticket.getRoute() == null || ticket.getRoute().length == 0) {
            throw new IllegalArgumentException("Route must not be empty");
        }

        // Check if all connections from seat reservation connect with each other
        for (int i = 0; i < ticket.getRoute().length - 1; i++) {
            if (!ticket.getRoute()[i].isConnectionValid(ticket.getRoute()[i + 1])) {
                throw new IllegalArgumentException("Connections in a route must connect to each other");
            }
        }
    }

    private void checkValidPrice(Ticket ticket) {
        if (ticket.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
    }
}
