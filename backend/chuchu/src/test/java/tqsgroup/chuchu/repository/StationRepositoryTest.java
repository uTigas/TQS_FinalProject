package tqsgroup.chuchu.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.cucumber.java.AfterAll;
import tqsgroup.chuchu.data.entity.Station;
import tqsgroup.chuchu.data.repository.neo.StationRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

@SpringBootTest
class StationRepositoryTest {
    
    @Autowired
    private StationRepository stationRepository;

    @BeforeEach
    void setUp() {
        stationRepository.deleteAll();
    }

    @Test
    void whenFindByName_thenReturnStation() {
        // Given
        String stationName = "Test Station";
        Station station = new Station(stationName, 1);
        stationRepository.save(station);

        // When
        Station found = stationRepository.findByName(stationName);

        // Then
        assertEquals(station.getName(), found.getName());
    }

    @Test
    void whenFindByInvalidName_thenReturnNull() {
        // Given
        String stationName = "Test Station";
        Station station = new Station(stationName, 1);
        stationRepository.save(station);

        // When
        Station found = stationRepository.findByName("Invalid Station");

        // Then
        assertNull(found);
    }

    @Test
    void givenSetOfStations_whenFindAll_thenReturnAllStations() {
        // Given
        Station station1 = new Station("Station 1", 1);
        Station station2 = new Station("Station 2", 2);
        Station station3 = new Station("Station 3", 3);
        stationRepository.save(station1);
        stationRepository.save(station2);
        stationRepository.save(station3);

        // When
        List<Station> stations = stationRepository.findAll();

        // Then
        assertEquals(stations.size(), 3);
    }

    @AfterAll
    void tearDown() {
        // Delete all stations
        stationRepository.deleteAll();
    }
}
