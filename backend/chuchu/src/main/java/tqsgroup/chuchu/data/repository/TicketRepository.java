package tqsgroup.chuchu.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqsgroup.chuchu.data.entity.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    
}
