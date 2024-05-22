package tqsgroup.chuchu.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqsgroup.chuchu.data.entity.Ticket;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByUserUsername(String username);
    List<Ticket> findAll(); //for admin purposes
}