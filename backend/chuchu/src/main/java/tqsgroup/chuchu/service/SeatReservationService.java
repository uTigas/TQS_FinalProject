package tqsgroup.chuchu.service;

import org.springframework.stereotype.Service;

import tqsgroup.chuchu.data.entity.SeatReservation;
import tqsgroup.chuchu.data.repository.SeatReservationRepository;

@Service
public class SeatReservationService {
    
    final SeatReservationRepository seatReservationRepository;

    public SeatReservationService(SeatReservationRepository seatReservationRepository) {
        this.seatReservationRepository = seatReservationRepository;
    }

    public SeatReservation saveSeatReservation(SeatReservation seatReservation) {
        checkValidSeat(seatReservation);
        checkValidConnection(seatReservation);
        checkSeatNonReserved(seatReservation);
        reserveSeat(seatReservation);
        return seatReservationRepository.save(seatReservation);
    }

    public SeatReservation cancelSeatReservation(SeatReservation seatReservation) {
        checkValidSeat(seatReservation);
        checkValidConnection(seatReservation);
        checkSeatReserved(seatReservation);
        releaseSeat(seatReservation);
        return seatReservationRepository.save(seatReservation);
    }

    // Helper methods
    private void checkValidSeat(SeatReservation seatReservation) {
        if (seatReservation.getSeat() == null) {
            throw new IllegalArgumentException("Seat must be assigned to a seat reservation");
        }
    }

    private void checkValidConnection(SeatReservation seatReservation) {
        if (seatReservation.getConnection() == null) {
            throw new IllegalArgumentException("Connection must be assigned to a seat reservation");
        }
    }

    private void checkSeatNonReserved(SeatReservation seatReservation) {
        if (seatReservation.getSeat().isReserved()) {
            throw new IllegalArgumentException("Seat is already reserved");
        }
    }

    private void checkSeatReserved(SeatReservation seatReservation) {
        if (!seatReservation.getSeat().isReserved()) {
            throw new IllegalArgumentException("Seat is not reserved");
        }
    }

    private void reserveSeat(SeatReservation seatReservation) {
        seatReservation.getSeat().setReserved(true);
    }

    private void releaseSeat(SeatReservation seatReservation) {
        seatReservation.getSeat().setReserved(false);
    }
}
