package tqsgroup.chuchu.data.service;

import org.springframework.stereotype.Service;

import tqsgroup.chuchu.data.entity.Seat;
import tqsgroup.chuchu.data.entity.Carriage;
import tqsgroup.chuchu.data.repository.SeatRepository;

import java.util.List;

@Service
public class SeatService {

    final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public Seat saveSeat(Seat seat) {
        checkValidCarriage(seat);
        checkValidSeatNumber(seat);
        return seatRepository.save(seat);
    }

    public List<Seat> findAllByCarriage(Carriage carriage) {
        return seatRepository.findAllByCarriage(carriage);
    }

    public Seat findByNumberAndCarriage(int number, Carriage carriage) {
        return seatRepository.findByNumberAndCarriage(number, carriage);
    }

    // Helper methods
    private void checkValidSeatNumber(Seat seat) {
        if (seat.getNumber() < 1 || seat.getNumber() > seat.getCarriage().getCapacity()){
            throw new IllegalArgumentException("Seat number must be within carriage capacity");
        }
    }

    private void checkValidCarriage(Seat seat) {
        if (seat.getCarriage() == null) {
            throw new IllegalArgumentException("Seat must be assigned to a carriage");
        }
    }
}
