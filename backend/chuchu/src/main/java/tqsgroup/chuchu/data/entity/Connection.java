package tqsgroup.chuchu.data.entity;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "CONNECTIONS")
public class Connection {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "origin")
    private Station origin;

    @Column(name = "destination")
    private Station destination;

    @ManyToOne
    @JoinColumn(name = "train_id")
    private Train train;

    @Column(name = "arrivalTime")
    private LocalTime arrivalTime;

    @Column(name = "departureTime")
    private LocalTime departureTime;

    @Column(name = "lineNumber")
    private int lineNumber; // Line number from the origin Station

    public Connection() {
    }

    public Connection(Station origin, Station destination, Train train, LocalTime arrivalTime, LocalTime departureTime, int lineNumber) {
        this.origin = origin;
        this.destination = destination;
        this.train = train;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.lineNumber = lineNumber;
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
}
