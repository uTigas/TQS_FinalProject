package tqsgroup.chuchu.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

import java.time.LocalTime;

@Entity
@Table(name = "CONNECTIONS")
public class Connection {

    private static final int MIN_LINE_NUMBER = 1;
    private static final int MAX_LINE_NUMBER = 30;

    private static final long MIN_PRICE = 0L;
    private static final long MAX_PRICE = 1_000L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "origin_id")
    private Station origin;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Station destination;

    @ManyToOne
    @JoinColumn(name = "train_id")
    private Train train;

    @Column(name = "arrivalTime")
    @NotNull
    private LocalTime arrivalTime;

    @Column(name = "departureTime")
    @NotNull
    private LocalTime departureTime;

    @Column(name = "lineNumber")
    @Min(MIN_LINE_NUMBER)
    @Max(MAX_LINE_NUMBER)
    @NotNull
    private int lineNumber; // Line number from the origin Station

    @Column(name = "price")
    @Min(MIN_PRICE)
    @Max(MAX_PRICE)
    @NotNull
    private long price;

    public Connection() {
    }

    public Connection(Station origin, Station destination, Train train, LocalTime arrivalTime, LocalTime departureTime, int lineNumber, long price) {
        this.origin = origin;
        this.destination = destination;
        this.train = train;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.lineNumber = lineNumber;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Station getOrigin() {
        return origin;
    }

    public void setOrigin(Station origin) {
        this.origin = origin;
    }

    public Station getDestination() {
        return destination;
    }

    public void setDestination(Station destination) {
        this.destination = destination;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
