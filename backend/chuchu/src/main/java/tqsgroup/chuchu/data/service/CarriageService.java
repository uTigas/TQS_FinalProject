package tqsgroup.chuchu.data.service;

import org.springframework.stereotype.Service;

import tqsgroup.chuchu.data.entity.Carriage;
import tqsgroup.chuchu.data.entity.CarriageType;
import tqsgroup.chuchu.data.entity.Train;
import tqsgroup.chuchu.data.entity.TrainType;
import tqsgroup.chuchu.data.repository.CarriageRepository;

import java.util.List;

@Service
public class CarriageService {

    private final int MIN_SEATS = 10;
    private final int MAX_SEATS = 200;

    final CarriageRepository carriageRepository;

    public CarriageService(CarriageRepository carriageRepository) {
        this.carriageRepository = carriageRepository;
    }

    public Carriage saveCarriage(Carriage carriage) {
        checkMatchingTypes(carriage.getType(), carriage.getTrain().getType());
        checkValidSeats(carriage);
        return carriageRepository.save(carriage);
    }

    public Carriage findByCarriageType(CarriageType type) {
        return carriageRepository.findByType(type);
    }

    public List<Carriage> findAllByTrain(Train train) {
        return carriageRepository.findAllByTrain(train);
    }

    public List<Carriage> getAllCarriages() {
        return carriageRepository.findAll();
    }

    // Helper methods
    private void checkMatchingTypes(CarriageType carriageType, TrainType trainType) {
        if (carriageType == CarriageType.NORMAL) {
            if (trainType == TrainType.ALPHA || trainType == TrainType.INTERCITY) {
                throw new IllegalArgumentException(carriageType.toString() + " carriage is not allowed for " + trainType.toString() + " train");
            }
        } else { //then it is first or second class
            if (trainType == TrainType.REGIONAL || trainType == TrainType.URBAN || trainType == TrainType.SPECIAL) {
                throw new IllegalArgumentException(carriageType.toString() + " carriage is not allowed for " + trainType.toString() + " train");
            }
        }
    }

    private void checkValidSeats(Carriage carriage) {
        if (carriage.getCapacity() < MIN_SEATS || carriage.getCapacity() > MAX_SEATS) {
            throw new IllegalArgumentException("Number of seats must be between " + MIN_SEATS + " and " + MAX_SEATS);
        }
    }
}
