package tqsgroup.chuchu.data.repository.neo;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import tqsgroup.chuchu.data.entity.Connection;
import tqsgroup.chuchu.data.entity.Station;
import tqsgroup.chuchu.data.entity.Train;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ConnectionRepository extends Neo4jRepository<Connection, UUID> {
    List<Connection> findAllByOrigin(Station origin);
    List<Connection> findAllByDestination(Station destination);
    @Query("MATCH (destination:Station)<-[:DESTINATION]-(connection:Connection) WHERE destination.name = $destinationStationName RETURN connection")
    List<Connection> findAllByDestinationStationName(String destinationStationName);
    List<Connection> findAllByOriginAndDestination(Station origin, Station destination);
    List<Connection> findAllByTrain(Train train);
    List<Connection> findAllByDepartureTimeAfter(LocalTime departureTime);
    List<Connection> findAllByArrivalTimeBefore(LocalTime arrivalTime);
    List<Connection> findAllByOriginAndLineNumber(Station origin, int lineNumber);
    List<Connection> findAllByPriceBetween(long minPrice, long maxPrice);
}
