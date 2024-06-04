package tqsgroup.chuchu.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TICKETS")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "timestamp")
    @NotNull
    private LocalDateTime timestamp;

    @NotNull
    private List<UUID> route;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user; 

    @Column(name = "totalPrice")
    @NotNull
    private Double totalPrice;

    private String origin;

    private String destination;

    private LocalTime departure;

    private LocalTime arrival;
    public String getOrigin() {
        return origin;
    }

    public void setorigin(String origin) {
        this.origin = origin;
    }

    public String getTo() {
        return destination;
    }

    public void setTo(String to) {
        this.destination = to;
    }

    public LocalTime getDeparture() {
        return departure;
    }

    public void setDeparture(LocalTime departure) {
        this.departure = departure;
    }

    public LocalTime getArrival() {
        return arrival;
    }

    public void setArrival(LocalTime arrival) {
        this.arrival = arrival;
    }

    public Ticket() {
        this.timestamp = LocalDateTime.now();
    }

    public Ticket(List<UUID> route, User user) {
        this.timestamp = LocalDateTime.now();
        this.route = route;
        this.user = user;
    }

    public UUID getId() {
        return id;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public List<UUID> getRoute() {
        return route;
    }

    public void setRoute(List<UUID> route) {
        this.route = route;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
