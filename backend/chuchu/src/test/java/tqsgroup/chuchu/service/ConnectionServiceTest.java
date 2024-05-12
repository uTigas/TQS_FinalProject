package tqsgroup.chuchu.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import tqsgroup.chuchu.data.entity.Connection;
import tqsgroup.chuchu.data.entity.Station;
import tqsgroup.chuchu.data.entity.Train;
import tqsgroup.chuchu.data.entity.TrainType;
import tqsgroup.chuchu.data.repository.ConnectionRepository;
import tqsgroup.chuchu.data.service.ConnectionService;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class ConnectionServiceTest {
    
    @Mock(lenient = true)
    private ConnectionRepository connectionRepository;

    @InjectMocks
    private ConnectionService connectionService;

    /* Save tests */

    @Test
    void whenSaveValidConnection_thenConnectionShouldBeSaved() {
        Connection c = new Connection(new Station("D", 4), new Station("E", 5), new Train(TrainType.ALPHA, 3), LocalTime.of(14, 0), LocalTime.of(15, 0), 3, 1L);
        connectionService.saveConnection(c);
        verify(connectionRepository, times(1)).save(c);
    }

    @Test
    void whenSaveConnectionWithInvalidStations_thenConnectionShouldNotBeSaved() {
        Station s = new Station("F", 6);
        Connection c = new Connection(s, s, new Train(TrainType.ALPHA, 4), LocalTime.of(15, 0), LocalTime.of(16, 0), 4, 1L);
        try {
            connectionService.saveConnection(c);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Origin and destination stations must be different");
        }
        verify(connectionRepository, never()).save(c);
    }

    @Test
    void whenSaveConnectionWithInvalidTimestamps_thenConnectionShouldNotBeSaved() {
        LocalTime arrivalTime = LocalTime.of(16, 0);
        LocalTime departureTime = LocalTime.of(15, 0);
        Connection c = new Connection(new Station("G", 7), new Station("H", 8), new Train(TrainType.ALPHA, 5), arrivalTime, departureTime, 5, 1L);
        try {
            connectionService.saveConnection(c);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Departure time must be before arrival time - Train departs from origin and arrives at destination");
        }
        verify(connectionRepository, never()).save(c);
    }

    @Test
    void whenSaveConnectionWithInvalidLineNumber_thenConnectionShouldNotBeSaved() {
        int lineNumber = 0;
        Connection c = new Connection(new Station("I", 9), new Station("J", 10), new Train(TrainType.ALPHA, 6), LocalTime.of(17, 0), LocalTime.of(18, 0), lineNumber, 1L);
        try {
            connectionService.saveConnection(c);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Line number does not exist in the origin station");
        }
        verify(connectionRepository, never()).save(c);
    }

    @Test
    void whenSaveConnectionWithInvalidPrice_thenConnectionShouldNotBeSaved() {
        long price = -1L;
        Connection c = new Connection(new Station("K", 11), new Station("L", 12), new Train(TrainType.ALPHA, 7), LocalTime.of(18, 0), LocalTime.of(19, 0), 7, price);
        try {
            connectionService.saveConnection(c);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Price must be between 0 and 1_000 inclusive");
        }
        verify(connectionRepository, never()).save(c);
    }

    /* Search tests */

    @Test
    void whenSearchValidOrigin_thenConnectionsShouldBeFound() {
        // Given
        Station origin = new Station("A", 1);
        Connection c = new Connection(origin, new Station("B", 2), new Train(TrainType.ALPHA, 1), LocalTime.of(12, 0), LocalTime.of(13, 0), 1, 1L);
        when(connectionRepository.findAllByOrigin(origin)).thenReturn(Arrays.asList(c));
    
        // When
        List<Connection> found = connectionService.findAllByOrigin(origin);
    
        // Then
        assertThat(found).containsOnly(c);
        verify(connectionRepository, times(1)).findAllByOrigin(origin);
    }

    @Test
    void whenSearchInvalidOrigin_thenConnectionsShouldNotBeFound() {
        // Given
        Station origin = new Station("K", 11);
        when(connectionRepository.findAllByOrigin(origin)).thenReturn(Arrays.asList());
    
        // When
        List<Connection> found = connectionService.findAllByOrigin(origin);
    
        // Then
        assertThat(found).isEmpty();
        verify(connectionRepository, times(1)).findAllByOrigin(origin);
    }

    @Test
    void whenSearchValidDestination_thenConnectionsShouldBeFound() {
        // Given
        Station destination = new Station("B", 2);
        Connection c = new Connection(new Station("A", 1), destination, new Train(TrainType.ALPHA, 1), LocalTime.of(12, 0), LocalTime.of(13, 0), 1, 1L);
        when(connectionRepository.findAllByDestination(destination)).thenReturn(Arrays.asList(c));
    
        // When
        List<Connection> found = connectionService.findAllByDestination(destination);
    
        // Then
        assertThat(found).containsOnly(c);
        verify(connectionRepository, times(1)).findAllByDestination(destination);
    }

    @Test
    void whenSearchInvalidDestination_thenConnectionsShouldNotBeFound() {
        // Given
        Station destination = new Station("L", 12);
        when(connectionRepository.findAllByDestination(destination)).thenReturn(Arrays.asList());
    
        // When
        List<Connection> found = connectionService.findAllByDestination(destination);
    
        // Then
        assertThat(found).isEmpty();
        verify(connectionRepository, times(1)).findAllByDestination(destination);
    }


    @Test
    void whenSearchValidOriginAndDestination_thenConnectionsShouldBeFound() {
        // Given
        Station origin = new Station("A", 1);
        Station destination = new Station("B", 2);
        Connection c = new Connection(origin, destination, new Train(TrainType.ALPHA, 1), LocalTime.of(12, 0), LocalTime.of(13, 0), 1, 1L);
        when(connectionRepository.findAllByOriginAndDestination(origin, destination)).thenReturn(Arrays.asList(c));
    
        // When
        List<Connection> found = connectionService.findAllByOriginAndDestination(origin, destination);
    
        // Then
        assertThat(found).containsOnly(c);
        verify(connectionRepository, times(1)).findAllByOriginAndDestination(origin, destination);
    }

    @Test
    void whenSearchInvalidOriginAndDestination_thenConnectionsShouldNotBeFound() {
        // Given
        Station origin = new Station("A", 1);
        Station destination = new Station("C", 3);
        when(connectionRepository.findAllByOriginAndDestination(origin, destination)).thenReturn(Arrays.asList());
    
        // When
        List<Connection> found = connectionService.findAllByOriginAndDestination(origin, destination);
    
        // Then
        assertThat(found).isEmpty();
        verify(connectionRepository, times(1)).findAllByOriginAndDestination(origin, destination);
    }

    @Test
    void whenSearchValidTrain_thenConnectionsShouldBeFound() {
        // Given
        Train train = new Train(TrainType.ALPHA, 1);
        Connection c = new Connection(new Station("A", 1), new Station("B", 2), train, LocalTime.of(12, 0), LocalTime.of(13, 0), 1, 1L);
        when(connectionRepository.findAllByTrain(train)).thenReturn(Arrays.asList(c));
    
        // When
        List<Connection> found = connectionService.findAllByTrain(train);
    
        // Then
        assertThat(found).containsOnly(c);
        verify(connectionRepository, times(1)).findAllByTrain(train);
    }

    @Test
    void whenSearchInvalidTrain_thenConnectionsShouldNotBeFound() {
        // Given
        Train train = new Train(TrainType.ALPHA, 2);
        when(connectionRepository.findAllByTrain(train)).thenReturn(Arrays.asList());
    
        // When
        List<Connection> found = connectionService.findAllByTrain(train);
    
        // Then
        assertThat(found).isEmpty();
        verify(connectionRepository, times(1)).findAllByTrain(train);
    }

    @Test
    void whenSearchValidArrivalTime_thenConnectionsShouldBeFound() {
        // Given
        LocalTime currentTime = LocalTime.of(11, 59);
        LocalTime arrivalTime = LocalTime.of(12, 0);
        Connection c = new Connection(new Station("A", 1), new Station("B", 2), new Train(TrainType.ALPHA, 1), arrivalTime, LocalTime.of(13, 0), 1, 1L);
        when(connectionRepository.findAllByArrivalTimeBefore(currentTime)).thenReturn(Arrays.asList(c));
    
        // When
        List<Connection> found = connectionService.findAllByArrivalTimeBefore(currentTime);
    
        // Then
        assertThat(found).containsOnly(c);
        verify(connectionRepository, times(1)).findAllByArrivalTimeBefore(currentTime);
    }

    @Test
    void whenSearchInvalidArrivalTime_thenConnectionsShouldNotBeFound() {
        // Given
        LocalTime currentTime = LocalTime.of(12, 0);
        when(connectionRepository.findAllByArrivalTimeBefore(currentTime)).thenReturn(Arrays.asList());
    
        // When
        List<Connection> found = connectionService.findAllByArrivalTimeBefore(currentTime);
    
        // Then
        assertThat(found).isEmpty();
        verify(connectionRepository, times(1)).findAllByArrivalTimeBefore(currentTime);
    }

    @Test
    void whenSearchValidDepartureTime_thenConnectionsShouldBeFound() {
        // Given
        LocalTime currentTime = LocalTime.of(11, 59);
        LocalTime departureTime = LocalTime.of(12, 0);
        Connection c = new Connection(new Station("A", 1), new Station("B", 2), new Train(TrainType.ALPHA, 1), LocalTime.of(11, 0), departureTime, 1, 1L);
        when(connectionRepository.findAllByDepartureTimeAfter(currentTime)).thenReturn(Arrays.asList(c));
    
        // When
        List<Connection> found = connectionService.findAllByDepartureTimeAfter(currentTime);
    
        // Then
        assertThat(found).containsOnly(c);
        verify(connectionRepository, times(1)).findAllByDepartureTimeAfter(currentTime);
    }

    @Test
    void whenSearchInvalidDepartureTime_thenConnectionsShouldNotBeFound() {
        // Given
        LocalTime currentTime = LocalTime.of(12, 0);
        when(connectionRepository.findAllByDepartureTimeAfter(currentTime)).thenReturn(Arrays.asList());
    
        // When
        List<Connection> found = connectionService.findAllByDepartureTimeAfter(currentTime);
    
        // Then
        assertThat(found).isEmpty();
        verify(connectionRepository, times(1)).findAllByDepartureTimeAfter(currentTime);
    }

    @Test
    void whenSearchValidOriginAndLineNumber_thenConnectionsShouldBeFound() {
        // Given
        Station origin = new Station("A", 1);
        int lineNumber = 1;
        Connection c = new Connection(origin, new Station("B", 2), new Train(TrainType.ALPHA, 1), LocalTime.of(12, 0), LocalTime.of(13, 0), 1, 1L);
        when(connectionRepository.findAllByOriginAndLineNumber(origin, lineNumber)).thenReturn(Arrays.asList(c));
    
        // When
        List<Connection> found = connectionService.findAllByOriginAndLineNumber(origin, lineNumber);
    
        // Then
        assertThat(found).containsOnly(c);
        verify(connectionRepository, times(1)).findAllByOriginAndLineNumber(origin, lineNumber);
    }

    @Test
    void whenSearchInvalidOriginAndLineNumber_thenConnectionsShouldNotBeFound() {
        // Given
        Station origin = new Station("A", 1);
        int lineNumber = 2;
        when(connectionRepository.findAllByOriginAndLineNumber(origin, lineNumber)).thenReturn(Arrays.asList());
    
        // When
        List<Connection> found = connectionService.findAllByOriginAndLineNumber(origin, lineNumber);
    
        // Then
        assertThat(found).isEmpty();
        verify(connectionRepository, times(1)).findAllByOriginAndLineNumber(origin, lineNumber);
    }

    @Test 
    void whenSearchValidPriceRange_thenConnectionsShouldBeFound() {
        // Given
        long minPrice = 1L;
        long maxPrice = 2L;
        Connection c = new Connection(new Station("A", 1), new Station("B", 2), new Train(TrainType.ALPHA, 1), LocalTime.of(12, 0), LocalTime.of(13, 0), 1, 1L);
        when(connectionRepository.findAllByPriceBetween(minPrice, maxPrice)).thenReturn(Arrays.asList(c));
    
        // When
        List<Connection> found = connectionService.findAllByPriceBetween(minPrice, maxPrice);
    
        // Then
        assertThat(found).containsOnly(c);
        verify(connectionRepository, times(1)).findAllByPriceBetween(minPrice, maxPrice);
    }

    @Test
    void whenSearchInvalidPriceRange_thenException() {
        // Given
        long minPrice = 2L;
        long maxPrice = 1L;
        when(connectionRepository.findAllByPriceBetween(minPrice, maxPrice)).thenReturn(Arrays.asList());
    
        // When
        try {
            connectionService.findAllByPriceBetween(minPrice, maxPrice);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Invalid price range");
        }
        verify(connectionRepository, never()).findAllByPriceBetween(minPrice, maxPrice);
    }


    @Test
    void whenSearchAllConnections_thenAllConnectionsShouldBeFound() {
        // Given
        Station s1 = new Station("A", 1);
        Station s2 = new Station("B", 2);
        Station s3 = new Station("C", 3);
        Train t1 = new Train(TrainType.ALPHA, 1);
        Connection c1 = new Connection(s1, s2, t1, LocalTime.of(12, 0), LocalTime.of(13, 0), 1, 1L);
        Connection c2 = new Connection(s2, s3, new Train(TrainType.URBAN, 2), LocalTime.of(13, 0), LocalTime.of(14, 0), 2, 1L);
        List<Connection> connections = Arrays.asList(c1,c2);
        when(connectionRepository.findAll()).thenReturn(connections);
    
        // When
        List<Connection> found = connectionService.getAllConnections();
    
        // Then
        assertThat(found).containsOnly(c1, c2);
        verify(connectionRepository, times(1)).findAll();
    }
}
