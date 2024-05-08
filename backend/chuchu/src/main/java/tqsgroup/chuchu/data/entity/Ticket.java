package tqsgroup.chuchu.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

import java.time.LocalDateTime;

@Entity
@Table(name = "TICKETS")
public class Ticket {

    private static final long MIN_PRICE = 0L;
    private static final long MAX_PRICE = 100_000L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp")
    @NotNull
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "route_id")
    @NotNull
    private SeatReservation[] route;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user; 

    @Column(name = "totalPrice")
    @Min(MIN_PRICE)
    @Max(MAX_PRICE)
    @NotNull
    private long totalPrice;

    public Ticket() {
    }

    public Ticket(SeatReservation[] route, User user) {
        this.timestamp = LocalDateTime.now();
        this.route = route;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public SeatReservation[] getRoute() {
        return route;
    }

    public void setRoute(SeatReservation[] route) {
        this.route = route;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }
}
