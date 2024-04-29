package tqsgroup.chuchu.data.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CARRIAGES")
public class Carriage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CarriageType type;    
    
    @ManyToOne
    private Train train;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "multiplier")
    private double multiplier;

    @OneToMany(mappedBy = "carriage")
    private List<Seat> seats;

    private double selectMultiplier() {
        switch (type) {
            case FIRST_CLASS:
                return 4.0;
            case SECOND_CLASS:
                return 3.0;
            case NORMAL:
                return 1.0;
            default:
                throw new IllegalStateException("Unknown carriage type");
        }
    }

    public Carriage() {
    }

    public Carriage(CarriageType type, Train train, int capacity) {
        this.type = type;
        this.train = train;
        this.capacity = capacity;
        this.multiplier = selectMultiplier();
        this.seats = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public CarriageType getType() {
        return type;
    }

    public void setType(CarriageType type) {
        this.type = type;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public List<Seat> getSeats() {
        return seats;
    }
}
