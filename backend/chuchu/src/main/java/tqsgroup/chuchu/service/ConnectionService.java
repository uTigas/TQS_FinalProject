package tqsgroup.chuchu.service;

import org.springframework.stereotype.Service;

import tqsgroup.chuchu.data.entity.Connection;
import tqsgroup.chuchu.data.entity.Station;
import tqsgroup.chuchu.data.entity.Train;
import tqsgroup.chuchu.data.repository.ConnectionRepository;

import java.time.LocalTime;
import java.util.List;

@Service
public class ConnectionService {
    final ConnectionRepository connectionRepository;

    public ConnectionService(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    public Connection saveConnection(Connection connection) {
        checkValidStations(connection);
        checkValidTimestamps(connection);
        checkValidLineNumber(connection);
        return connectionRepository.save(connection);
    }

    public List<Connection> findAllByOrigin(Station origin) {
        return connectionRepository.findAllByOrigin(origin);
    }

    public List<Connection> findAllByDestination(Station destination) {
        return connectionRepository.findAllByDestination(destination);
    }

    public List<Connection> findAllByOriginAndDestination(Station origin, Station destination) {
        return connectionRepository.findAllByOriginAndDestination(origin, destination);
    }

    public List<Connection> findAllByTrain(Train train) {
        return connectionRepository.findAllByTrain(train);
    }

    public List<Connection> findAllByArrivalTimeAfter(LocalTime arrivalTime) {
        return connectionRepository.findAllByArrivalTimeAfter(arrivalTime);
    }

    public List<Connection> findAllByDepartureTimeAfter(LocalTime departureTime) {
        return connectionRepository.findAllByDepartureTimeAfter(departureTime);
    }

    public List<Connection> findAllByOriginAndLineNumber(Station origin, int lineNumber) {
        return connectionRepository.findAllByOriginAndLineNumber(origin, lineNumber);
    }

    public List<Connection> getAllConnections() {
        return connectionRepository.findAll();
    }

    // Helper methods
    private void checkValidStations(Connection connection) {
        if (connection.getOrigin().equals(connection.getDestination())) {
            throw new IllegalArgumentException("Origin and destination stations must be different");
        }
    }

    private void checkValidTimestamps(Connection connection) {
        if (connection.getArrivalTime().isAfter(connection.getDepartureTime())) {
            throw new IllegalArgumentException("Arrival time must be before departure time");
        }
    }

    private void checkValidLineNumber(Connection connection) {
        int lineNumber = connection.getLineNumber();
        if (lineNumber < 1 || lineNumber > connection.getOrigin().getNumberOfLines()) {
            throw new IllegalArgumentException("Line number does not exist in the origin station");
        }
    }
}
