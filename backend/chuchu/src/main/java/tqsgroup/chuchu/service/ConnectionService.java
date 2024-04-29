package tqsgroup.chuchu.service;

import org.springframework.stereotype.Service;

import tqsgroup.chuchu.data.entity.Connection;
import tqsgroup.chuchu.data.repository.ConnectionRepository;

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


    // Helper methods
    private void checkValidStations(Connection connection) {
        if (connection.getOrigin().equals(connection.getDestination())) {
            throw new IllegalArgumentException("Origin and destination stations must be different");
        }
    }

    private void checkValidTimestamps(Connection connection) {
        if (connection.getDepartureTime().isAfter(connection.getArrivalTime())) {
            throw new IllegalArgumentException("Departure time must be before arrival time");
        }
    }

    private void checkValidLineNumber(Connection connection) {
        if (connection.getLineNumber() < 0 || connection.getLineNumber() > connection.getOrigin().getNumberOfLines()) {
            throw new IllegalArgumentException("Line number does not exist in the origin station");
        }
    }
}
