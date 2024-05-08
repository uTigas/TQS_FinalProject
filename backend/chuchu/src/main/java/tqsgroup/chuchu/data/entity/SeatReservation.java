package tqsgroup.chuchu.data.entity;

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
    @Column(name = "seat")
    @JoinColumn(name = "seat_id")
    @NotNull
    private Seat seat;

    @ManyToOne
    @Column(name = "connection")
    @JoinColumn(name = "connection_id")
    @NotNull
    private Connection connection;

    @Column(name = "seatPrice")
    @Min(MIN_PRICE)
    @Max(MAX_PRICE)
    @NotNull
    private long seatPrice;

    public SeatReservation() {
    }

    public SeatReservation(Seat seat, Connection connection) {
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

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public long getSeatPrice() {
        return seatPrice;
    }

    public void setSeatPrice(long seatPrice) {
        this.seatPrice = seatPrice;
    }

    public boolean isConnectionValid(SeatReservation next) {
        // Check timestamps and stations
        if (this.connection.getDepartureTime().isAfter(next.connection.getArrivalTime())) {
            return false;
        }
        return this.connection.getDestination().equals(next.connection.getOrigin());
    }
}
