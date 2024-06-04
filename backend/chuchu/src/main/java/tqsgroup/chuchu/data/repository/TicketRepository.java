package tqsgroup.chuchu.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqsgroup.chuchu.data.entity.Ticket;

import java.util.List;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    List<Ticket> findAllByUserUsername(String username);
    List<Ticket> findAll(); //for admin purposes
}
