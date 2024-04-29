package tqsgroup.chuchu.service;

import org.springframework.stereotype.Service;

import tqsgroup.chuchu.data.entity.Station;
import tqsgroup.chuchu.data.repository.StationRepository;

import java.util.List;

@Service
public class StationService {

    final StationRepository stationRepository;

    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public Station saveStation(Station station) {
        checkValidStationName(station);
        checkValidNumberOfLines(station);
        return stationRepository.save(station);
    }

    public Station getStationByName(String name) {
        return stationRepository.findByName(name);
    }

    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }


    // Helper methods
    private void checkValidStationName(Station station) {
        if (station.getName() == null || station.getName().isEmpty()) {
            throw new IllegalArgumentException("Station name must not be empty");
        }
    }

    private void checkValidNumberOfLines(Station station) {
        if (station.getNumberOfLines() <= 0) {
            throw new IllegalArgumentException("Number of lines must be greater than 0");
        }
    }
}
