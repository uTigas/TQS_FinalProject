package tqsgroup.chuchu.unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import tqsgroup.chuchu.data.entity.Station;
import tqsgroup.chuchu.data.repository.StationRepository;
import tqsgroup.chuchu.data.service.StationService;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class StationServiceTest {

    @Mock(lenient = true)
    private StationRepository stationRepository;

    @InjectMocks
    private StationService stationService;

    @BeforeEach
    void setUp() {
        Station s1 = new Station("Station 1", 1);
        Station s2 = new Station("Station 2", 2);

        List<Station> stations = Arrays.asList(s1,s2);

        when(stationRepository.save(s1)).thenReturn(s1);
        when(stationRepository.findByName("Station 1")).thenReturn(s1);
        when(stationRepository.findByName("wrong_name")).thenReturn(null);
        when(stationRepository.findAll()).thenReturn(stations);
    }

    @Test
    void whenSaveStationWithValidName_thenStationShouldBeSaved() {
        Station s3 = new Station("Station 3", 3);
        stationService.saveStation(s3);
        verify(stationRepository, times(1)).save(s3);
    }

    @Test
    void whenSaveStationWithInvalidName_thenStationShouldNotBeSaved() {
        Station s = new Station("", 1);
        try {
            stationService.saveStation(s);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Station name must not be empty");
        }
        verify(stationRepository, never()).save(s);
    }

    @Test
    void whenSaveStationWithNonUniqueName_thenStationShouldNotBeSaved() {
        Station s = new Station("Station 1", 1);
        try {
            stationService.saveStation(s);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Station name must be unique");
        }
        verify(stationRepository, never()).save(s);
    }

    @Test
    void whenSaveStationWithInvalidNumberOfLines_thenStationShouldNotBeSaved() {
        Station s = new Station("Station 4", 31);
        try {
            stationService.saveStation(s);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Number of lines in a Station must be between 1 and 30 inclusive");
        }
        verify(stationRepository, never()).save(s);
    }

    @Test
    void whenSearchValidName_thenStationShouldBeFound() {
        String name = "Station 1";
        Station found = stationService.getStationByName(name);
        assertThat(found.getName()).isEqualTo(name);
        verify(stationRepository, times(1)).findByName(name);
    }

   @Test
    void whenSearchNonExistingName_thenStationShouldNotBeFound() {
        String name = "wrong_name";
        Station found = stationService.getStationByName(name);
        assertThat(found).isNull();
        verify(stationRepository, times(1)).findByName(name);
    }

    @Test
    void whenSearchEmptyName_thenStationShouldNotBeFound() {
        String name = "";
        try {
            stationService.getStationByName(name);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Station name must not be empty");
        }
        verify(stationRepository, never()).findByName(name);
    }

    @Test
    void whenGetAllStations_thenAllStationsShouldBeReturned() {
        Station s1 = new Station("Station 1", 1);
        Station s2 = new Station("Station 2", 2);

        List<Station> allStations = stationService.getAllStations();
        assertThat(allStations).hasSize(2).extracting(Station::getName).contains(s1.getName(), s2.getName());
        verify(stationRepository, times(1)).findAll();
    }
}
