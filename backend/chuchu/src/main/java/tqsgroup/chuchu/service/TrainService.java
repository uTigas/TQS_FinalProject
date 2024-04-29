package tqsgroup.chuchu.service;

import org.springframework.stereotype.Service;

import tqsgroup.chuchu.data.entity.Train;
import tqsgroup.chuchu.data.repository.TrainRepository;

@Service
public class TrainService {
    
    final TrainRepository trainRepository;

    public TrainService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    public Train saveTrain(Train train) {
        checkValidTrainType(train);
        checkValidTrainNumber(train);
        return trainRepository.save(train);
    }

    // Helper methods
    private void checkValidTrainType(Train train) {
        if (train.getType() == null) {
            throw new IllegalArgumentException("Train type must not be null");
        }
    }

    private void checkValidTrainNumber(Train train) {
        if (train.getNumber() <= 0) {
            throw new IllegalArgumentException("Train number must be greater than 0");
        }
    }
}
