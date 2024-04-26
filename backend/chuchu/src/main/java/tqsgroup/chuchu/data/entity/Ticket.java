package tqsgroup.chuchu.data.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TICKETS")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @ManyToOne
    private SeatReservation[] route;

    @Column(name = "price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; 

    public Ticket() {
    }

    public Ticket(SeatReservation[] route, double price, User user) {
        this.timestamp = LocalDateTime.now();
        this.route = route;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
