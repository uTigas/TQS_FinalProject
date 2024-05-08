package tqsgroup.chuchu.data.service;

import org.springframework.stereotype.Service;

import tqsgroup.chuchu.data.entity.SeatReservation;
import tqsgroup.chuchu.data.entity.Seat;
import tqsgroup.chuchu.data.entity.Connection;
import tqsgroup.chuchu.data.repository.SeatReservationRepository;
import tqsgroup.chuchu.data.repository.SeatRepository;

import java.util.List;

@Service
public class SeatReservationService {

    private static final long MIN_PRICE = 0L;
    private static final long MAX_PRICE = 10_000L;
    
    final SeatReservationRepository seatReservationRepository;
    final SeatRepository seatRepository;

    public SeatReservationService(SeatReservationRepository seatReservationRepository, SeatRepository seatRepository) {
        this.seatReservationRepository = seatReservationRepository;
        this.seatRepository = seatRepository;
    }

    public SeatReservation saveSeatReservation(SeatReservation seatReservation) {
        Long seatReservationPrice = calculateSeatReservationPrice(seatReservation.getSeat(), seatReservation.getConnection());
        checkValidPrice(seatReservationPrice);
        seatReservation.setSeatPrice(seatReservationPrice);
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
    private long calculateSeatReservationPrice(Seat seat, Connection connection) {
        if (seat == null || connection == null) {
            throw new IllegalArgumentException("Seat and connection are mandatory for calculating seat reservation price");
        }

        long basePrice = connection.getPrice();
        double multiplier = seat.getCarriageMultiplier();
        return (long) (basePrice * multiplier);
    }

    private void checkValidPrice(long price) {
        if (price < MIN_PRICE || price > MAX_PRICE) {
            throw new IllegalArgumentException("Price must be between " + MIN_PRICE + " and " + MAX_PRICE + " inclusive");
        }
    }

}
