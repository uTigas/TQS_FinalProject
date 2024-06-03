package tqsgroup.chuchu.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.cucumber.java.AfterAll;
import tqsgroup.chuchu.data.entity.Station;
import tqsgroup.chuchu.data.entity.Train;
import tqsgroup.chuchu.data.entity.TrainType;
import tqsgroup.chuchu.data.entity.Connection;
import tqsgroup.chuchu.data.repository.neo.StationRepository;
import tqsgroup.chuchu.data.repository.TrainRepository;
import tqsgroup.chuchu.data.repository.neo.ConnectionRepository;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.time.LocalTime;

@SpringBootTest
class ConnectionRepositoryTest {

    @Autowired
    private StationRepository stationRepository;

    @Autowired 
    private TrainRepository trainRepository;

    @Autowired
    private ConnectionRepository connectionRepository;

    @BeforeEach
    void setUp() {
        connectionRepository.deleteAll();
        stationRepository.deleteAll();
        trainRepository.deleteAll();
    }

    @AfterAll
    void tearDown() {
        connectionRepository.deleteAll();
        stationRepository.deleteAll();
        trainRepository.deleteAll();
    }

    Station origin = new Station("Origin Station", 1);
    Station destination = new Station("Destination Station", 2);
    Train train = new Train(TrainType.URBAN, 1);

    @Test
    void givenValidConnection_thenConnectionShouldBeSaved() {
        // Given
        stationRepository.save(origin);
        stationRepository.save(destination);
        trainRepository.save(train);

        LocalTime departureTime = LocalTime.of(12, 0);
        LocalTime arrivalTime = LocalTime.of(13, 0);
        Connection connection = new Connection(origin, destination, train, departureTime, arrivalTime, 1, 100);

        // When
        Connection c = connectionRepository.save(connection);

        // Then
        assertNotNull(c);
        assertEquals(connection.getOrigin().getName(), c.getOrigin().getName());
    }

    @Test
    void givenConnections_whenFindAllByPriceRange_thenReturnConnections() {
        // Given
        stationRepository.save(origin);
        stationRepository.save(destination);
        trainRepository.save(train);

        Connection connection1 = new Connection(origin, destination, train, LocalTime.of(12, 0), LocalTime.of(13, 0), 1, 100);
        Connection connection2 = new Connection(destination, origin, train, LocalTime.of(13, 0), LocalTime.of(14, 0), 1, 200);
        Connection connection3 = new Connection(origin, destination, train, LocalTime.of(14, 0), LocalTime.of(15, 0), 1, 300);
        connectionRepository.save(connection1);
        connectionRepository.save(connection2);
        connectionRepository.save(connection3);

        // When
        List<Connection> connections = connectionRepository.findAllByPriceBetween(150, 250);

        // Then
        assertEquals(1, connections.size());
    }

    @Test 
    void givenConnections_whenFindAll_thenReturnAllConnections() {
        // Given
        stationRepository.save(origin);
        stationRepository.save(destination);
        trainRepository.save(train);

        Connection connection1 = new Connection(origin, destination, train, LocalTime.of(12, 0), LocalTime.of(13, 0), 1, 100);
        Connection connection2 = new Connection(origin, destination, train, LocalTime.of(13, 0), LocalTime.of(14, 0), 1, 100);
        Connection connection3 = new Connection(origin, destination, train, LocalTime.of(14, 0), LocalTime.of(15, 0), 1, 100);
        connectionRepository.save(connection1);
        connectionRepository.save(connection2);
        connectionRepository.save(connection3);

        // When
        List<Connection> connections = connectionRepository.findAll();

        // Then
        assertEquals(3, connections.size());
    }
}
