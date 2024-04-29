package tqsgroup.chuchu.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqsgroup.chuchu.data.entity.SeatReservation;

@Repository
public interface SeatReservationRepository extends JpaRepository<SeatReservation, Long> {
    
}
