package tqsgroup.chuchu.data.service;

import org.springframework.stereotype.Service;

import tqsgroup.chuchu.data.entity.Train;
import tqsgroup.chuchu.data.repository.TrainRepository;

import java.util.List;

@Service
public class TrainService {

    private final int MIN_NUMBER = 1;
    private final int MAX_NUMBER = 9999;
    
    final TrainRepository trainRepository;

    public TrainService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    public Train saveTrain(Train train) {
        checkNumberInterval(train.getNumber());
        checkUniqueNumber(train.getNumber());
        return trainRepository.save(train);
    }

    public Train findTrainByNumber(int number) {
        checkNumberInterval(number);
        return trainRepository.findByNumber(number);
    }

    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }

    // Helper methods
    private void checkNumberInterval(int number) {
        if (number < MIN_NUMBER || number > MAX_NUMBER) {
            throw new IllegalArgumentException("Train number must be between 1 and 9999 inclusive");
        }
    }
    private void checkUniqueNumber(int number) {
        if (trainRepository.findByNumber(number) != null) {
            throw new IllegalArgumentException("Train number must be unique");
        }
    }
}
