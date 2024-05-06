package tqsgroup.chuchu.service;

import org.springframework.stereotype.Service;

import tqsgroup.chuchu.data.entity.SeatReservation;
import tqsgroup.chuchu.data.entity.Seat;
import tqsgroup.chuchu.data.entity.Connection;
import tqsgroup.chuchu.data.repository.SeatReservationRepository;
import tqsgroup.chuchu.data.repository.SeatRepository;

import java.util.List;

@Service
public class SeatReservationService {
    
    final SeatReservationRepository seatReservationRepository;
    final SeatRepository seatRepository;

    public SeatReservationService(SeatReservationRepository seatReservationRepository, SeatRepository seatRepository) {
        this.seatReservationRepository = seatReservationRepository;
        this.seatRepository = seatRepository;
    }

    public SeatReservation saveSeatReservation(SeatReservation seatReservation) {
        checkValidSeat(seatReservation.getSeat());
        checkValidConnection(seatReservation.getConnection());
        return seatReservationRepository.save(seatReservation);
    }

    public boolean reserveSeat(Seat seat, Connection connection) {
        SeatReservation existingReservation = findSeatReservationBySeat(seat);
        if (existingReservation != null) {
            return false; // Seat is already reserved
        }

        SeatReservation newReservation = new SeatReservation(seat, connection);
        seat.setReserved(true);
        seatRepository.save(seat);
        seatReservationRepository.save(newReservation);
        return true;
    }

    public boolean releaseSeat(Seat seat) {
        SeatReservation existingReservation = findSeatReservationBySeat(seat);
        if (existingReservation == null) {
            return false; // Seat is not reserved
        }

        seat.setReserved(false);
        seatRepository.save(seat);
        seatReservationRepository.delete(existingReservation);
        return true;
    }

    public SeatReservation findSeatReservationBySeat(Seat seat) {
        return seatReservationRepository.findBySeat(seat);
    }

    public List<SeatReservation> findAllSeatReservationsByConnection(Connection connection) {
        return seatReservationRepository.findAllByConnection(connection);
    }


    // Helper methods
    private void checkValidSeat(Seat seat) {
        if (seat == null) {
            throw new IllegalArgumentException("Seat cannot be null");
        }
    }

    private void checkValidConnection(Connection connection) {
        if (connection == null) {
            throw new IllegalArgumentException("Connection cannot be null");
        }
    }
}
