package tqsgroup.chuchu.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqsgroup.chuchu.data.entity.SeatReservation;
import tqsgroup.chuchu.data.entity.Connection;
import tqsgroup.chuchu.data.entity.Seat;

import java.util.List;

@Repository
public interface SeatReservationRepository extends JpaRepository<SeatReservation, Long> {
    SeatReservation findBySeat(Seat seat);
    List<SeatReservation> findAllByConnection(Connection connection);
}
