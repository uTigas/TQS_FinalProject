package tqsgroup.chuchu.data.entity;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node
public class Connection {
    
    @Id
    @GeneratedValue
    private Long id;

    @Relationship(type = "ORIGIN")
    private Station origin;

    @Relationship(type = "DESTINATION")
    private Station destination;

    @NotNull
    private int trainId;

    @NotNull
    private LocalTime departureTime;

    @NotNull
    private LocalTime arrivalTime;

    @NotNull
    private int lineNumber; // Line number from the origin Station

    @NotNull
    private long price;

    public Connection() {
    }

    public Connection(Station origin, Station destination, Train train, LocalTime departureTime, LocalTime arrivalTime, int lineNumber, long price) {
        this.origin = origin;
        this.destination = destination;
        this.trainId = train.getNumber();
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
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

    public int getTrain() {
        return trainId;
    }

    public void setTrain(Train train) {
        this.trainId = train.getNumber();
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
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
