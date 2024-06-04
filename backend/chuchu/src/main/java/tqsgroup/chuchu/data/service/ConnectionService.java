package tqsgroup.chuchu.data.service;

import org.springframework.stereotype.Service;

import tqsgroup.chuchu.data.entity.Connection;
import tqsgroup.chuchu.data.entity.Station;
import tqsgroup.chuchu.data.entity.Train;
import tqsgroup.chuchu.data.repository.neo.ConnectionRepository;

import java.time.LocalTime;
import java.util.List;

@Service
public class ConnectionService {

    private final int MIN_PRICE = 0;
    private final int MAX_PRICE = 1_000;

    final ConnectionRepository connectionRepository;

    public ConnectionService(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    public Connection saveConnection(Connection connection) {
        checkValidStations(connection);
        checkValidTimestamps(connection);
        checkValidLineNumber(connection);
        checkValidPriceValue(connection.getPrice());
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

    public List<Connection> findAllByDepartureTimeAfter(LocalTime departureTime) {
        return connectionRepository.findAllByDepartureTimeAfter(departureTime);
    }

    public List<Connection> findAllByArrivalTimeBefore(LocalTime arrivalTime) {
        return connectionRepository.findAllByArrivalTimeBefore(arrivalTime);
    }

    public List<Connection> findAllByOriginAndLineNumber(Station origin, int lineNumber) {
        return connectionRepository.findAllByOriginAndLineNumber(origin, lineNumber);
    }

    public List<Connection> findAllByPriceBetween(long minPrice, long maxPrice) {
        checkValidPriceRange(minPrice, maxPrice);
        return connectionRepository.findAllByPriceBetween(minPrice, maxPrice);
    }

    public List<Connection> getAllConnections() {
        return connectionRepository.findAll();
    }

    public List<Connection> getArrivals(Station station, int limit, LocalTime time) {
        return connectionRepository.findAllByDestinationStationName(station.getName()).stream().filter(
            connection -> connection.getDepartureTime().isAfter(time)).limit(limit).toList();
    }

    public List<Connection> getArrivalsFromTrack(Station station, int limit, int trackNumber, LocalTime time) {
        return connectionRepository.findAllByDestinationStationName(station.getName()).stream().filter( 
            connection -> connection.getLineNumber() == trackNumber && connection.getDepartureTime().isAfter(time)).limit(limit).toList();
    }

    // Helper methods
    private void checkValidStations(Connection connection) {
        if (connection.getOrigin().equals(connection.getDestination())) {
            throw new IllegalArgumentException("Origin and destination stations must be different");
        }
    }

    private void checkValidTimestamps(Connection connection) {
        if (connection.getDepartureTime().isAfter(connection.getArrivalTime())) {
            throw new IllegalArgumentException("Departure time must be before arrival time - Train departs from origin and arrives at destination");
        }
    }

    private void checkValidLineNumber(Connection connection) {
        int lineNumber = connection.getLineNumber();
        if (lineNumber < 1 || lineNumber > connection.getOrigin().getNumberOfLines()) {
            throw new IllegalArgumentException("Line number does not exist in the origin station");
        }
    }

    private void checkValidPriceValue(long price) {
        if (price < MIN_PRICE || price > MAX_PRICE) {
            throw new IllegalArgumentException("Price must be between 0 and 1_000 inclusive");
        }
    }

    private void checkValidPriceRange(long minPrice, long maxPrice) {
        if (minPrice < MIN_PRICE || maxPrice < MIN_PRICE || minPrice > maxPrice) {
            throw new IllegalArgumentException("Invalid price range");
        }
    }
}
