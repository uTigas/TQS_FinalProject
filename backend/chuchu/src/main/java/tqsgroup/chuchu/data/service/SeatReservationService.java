package tqsgroup.chuchu.data.service;

import org.springframework.stereotype.Service;

import tqsgroup.chuchu.data.entity.SeatReservation;
import tqsgroup.chuchu.data.entity.Seat;
import tqsgroup.chuchu.data.entity.Connection;
import tqsgroup.chuchu.data.repository.SeatReservationRepository;
import tqsgroup.chuchu.data.repository.neo.ConnectionRepository;
import tqsgroup.chuchu.data.repository.SeatRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SeatReservationService {

    private static final long MIN_PRICE = 0L;
    private static final long MAX_PRICE = 10_000L;
    
    final SeatReservationRepository seatReservationRepository;
    final SeatRepository seatRepository;
    final ConnectionRepository connectionRepository;

    public SeatReservationService(SeatReservationRepository seatReservationRepository, SeatRepository seatRepository, ConnectionRepository connectionRepository) {
        this.seatReservationRepository = seatReservationRepository;
        this.seatRepository = seatRepository;
        this.connectionRepository = connectionRepository;
    }

    public SeatReservation saveSeatReservation(SeatReservation seatReservation) {
        Optional<Connection> temp = connectionRepository.findById(seatReservation.getConnection());
        Connection con = null;
        if (temp.isPresent())
            con = temp.get();

        Long seatReservationPrice = calculateSeatReservationPrice(seatReservation.getSeat(), con);
        checkValidPrice(seatReservationPrice);
        seatReservation.setSeatPrice(seatReservationPrice);
        return seatReservationRepository.save(seatReservation);
    }

    public boolean reserveSeat(Seat seat, Connection connection) {
        SeatReservation existingReservation = findSeatReservationBySeat(seat);
        if (existingReservation != null) {
            return false; // Seat is already reserved
        }

        SeatReservation newReservation = new SeatReservation(seat, connection.getId());
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

    public boolean isConnectionValid(UUID prev, UUID next) {
        // Check timestamps and stations
        Connection prevCon = connectionRepository.findById(prev).get();
        Connection nextCon = connectionRepository.findById(next).get();

        if (connectionRepository.findById(prevCon.getId()).get().getDepartureTime().isAfter(connectionRepository.findById(nextCon.getId()).get().getArrivalTime())) {
            return false;
        }
        return connectionRepository.findById(prevCon.getId()).get().getDestination().equals(connectionRepository.findById(nextCon.getId()).get().getOrigin());
    }

}
