package tqsgroup.chuchu.data.entity;

import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

@Entity
@Table(name = "SEAT_RESERVATIONS")
public class SeatReservation {

    private static final long MIN_PRICE = 0L;
    private static final long MAX_PRICE = 10_000L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    @NotNull
    private Seat seat;

    @NotNull
    private UUID connection;

    @Column(name = "seatPrice")
    @Min(MIN_PRICE)
    @Max(MAX_PRICE)
    @NotNull
    private long seatPrice;

    public SeatReservation() {
    }

    public SeatReservation(Seat seat, UUID connection) {
        this.seat = seat;
        this.connection = connection;
    }

    public Long getId() {
        return id;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public UUID getConnection() {
        return connection;
    }

    public void setConnection(UUID connection) {
        this.connection = connection;
    }

    public long getSeatPrice() {
        return seatPrice;
    }

    public void setSeatPrice(long seatPrice) {
        this.seatPrice = seatPrice;
    }

    
}
