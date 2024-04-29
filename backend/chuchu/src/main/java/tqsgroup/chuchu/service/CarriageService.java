package tqsgroup.chuchu.service;

import org.springframework.stereotype.Service;

import tqsgroup.chuchu.data.entity.Carriage;
import tqsgroup.chuchu.data.entity.CarriageType;
import tqsgroup.chuchu.data.entity.TrainType;
import tqsgroup.chuchu.data.repository.CarriageRepository;

@Service
public class CarriageService {

    private final int MIN_SEATS = 10;
    private final int MAX_SEATS = 200;

    final CarriageRepository carriageRepository;

    public CarriageService(CarriageRepository carriageRepository) {
        this.carriageRepository = carriageRepository;
    }

    public Carriage saveCarriage(Carriage carriage) {
        checkValidTrain(carriage);
        checkValidCarriageType(carriage);
        checkValidSeats(carriage);
        return carriageRepository.save(carriage);
    }

    // Helper methods
    private void checkValidTrain(Carriage carriage) {
        if (carriage.getTrain() == null) {
            throw new IllegalArgumentException("Train must not be null");
        }
    }

    private void checkValidCarriageType(Carriage carriage) {
        if (carriage.getType() == null) {
            throw new IllegalArgumentException("Carriage type must not be null");
        }
        CarriageType carriageType = carriage.getType();
        TrainType trainType = carriage.getTrain().getType();

        if (carriageType == CarriageType.NORMAL) {
            if (trainType == TrainType.ALPHA || trainType == TrainType.INTERCITY) {
                throw new IllegalArgumentException("Normal carriage is not allowed for this train type");
            }
        } else { //then it is first or second class
            if (trainType == TrainType.REGIONAL || trainType == TrainType.URBAN || trainType == TrainType.SPECIAL) {
                throw new IllegalArgumentException("First or second class carriage is not allowed for this train type");
            }
        }
    }

    private void checkValidSeats(Carriage carriage) {
        if (carriage.getCapacity() < MIN_SEATS || carriage.getCapacity() > MAX_SEATS) {
            throw new IllegalArgumentException("Number of seats must be between " + MIN_SEATS + " and " + MAX_SEATS);
        }
    }
}
