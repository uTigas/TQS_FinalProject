package tqsgroup.chuchu.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqsgroup.chuchu.data.entity.Connection;
import tqsgroup.chuchu.data.entity.Station;
import tqsgroup.chuchu.data.entity.Train;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {
    List<Connection> findAllByOrigin(Station origin);
    List<Connection> findAllByDestination(Station destination);
    List<Connection> findAllByOriginAndDestination(Station origin, Station destination);
    List<Connection> findAllByTrain(Train train);
    List<Connection> findAllByArrivalTimeAfter(LocalTime arrivalTime);
    List<Connection> findAllByDepartureTimeAfter(LocalTime departureTime);
    List<Connection> findAllByOriginAndLineNumber(Station origin, int lineNumber);
}
