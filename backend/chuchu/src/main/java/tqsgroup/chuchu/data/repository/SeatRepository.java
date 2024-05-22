package tqsgroup.chuchu.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqsgroup.chuchu.data.entity.Seat;
import tqsgroup.chuchu.data.entity.Carriage;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findAllByCarriage(Carriage carriage);
    Seat findByNumberAndCarriage(int number, Carriage carriage);
}
