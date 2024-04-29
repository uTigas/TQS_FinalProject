package tqsgroup.chuchu.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqsgroup.chuchu.data.entity.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    
}
