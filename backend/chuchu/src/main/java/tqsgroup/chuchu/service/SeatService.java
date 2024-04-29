package tqsgroup.chuchu.service;

import org.springframework.stereotype.Service;

import tqsgroup.chuchu.data.entity.Seat;
import tqsgroup.chuchu.data.repository.SeatRepository;

@Service
public class SeatService {

    final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public Seat saveSeat(Seat seat) {
        checkValidSeatNumber(seat);
        checkValidCarriage(seat);
        return seatRepository.save(seat);
    }

    // Helper methods
    private void checkValidSeatNumber(Seat seat) {
        if (seat.getSeatNumber() < 0 || seat.getSeatNumber() > seat.getCarriage().getCapacity()){
            throw new IllegalArgumentException("Seat number must be positive");
        }
    }

    private void checkValidCarriage(Seat seat) {
        if (seat.getCarriage() == null) {
            throw new IllegalArgumentException("Seat must be assigned to a carriage");
        }
    }
}
