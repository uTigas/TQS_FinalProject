package tqsgroup.chuchu.data.service;

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
        checkEmptyStationName(station.getName());
        checkUniqueStationName(station.getName());
        checkValidNumberOfLines(station.getNumberOfLines());
        return stationRepository.save(station);
    }

    public Station getStationByName(String name) {
        checkEmptyStationName(name);
        return stationRepository.findByName(name);
    }

    public Station getStationById(Long id) {
        return stationRepository.findById(id).get();
    }

    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }


    // Helper methods
    private void checkEmptyStationName(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Station name must not be empty");
        }
    }

    private void checkUniqueStationName(String name) {
        if (stationRepository.findByName(name) != null) {
            throw new IllegalArgumentException("Station name must be unique");
        }
    }

    private void checkValidNumberOfLines(int numberOfLines) {
        if (numberOfLines <= 0 || numberOfLines > 30){
            throw new IllegalArgumentException("Number of lines in a Station must be between 1 and 30 inclusive");
        }
    }
}
