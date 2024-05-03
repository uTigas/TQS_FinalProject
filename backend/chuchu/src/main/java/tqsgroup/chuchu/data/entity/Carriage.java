package tqsgroup.chuchu.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

@Entity
@Table(name = "CARRIAGES")
public class Carriage {

    private static final int MIN_CARRIAGE_CAPACITY = 10;
    private static final int MAX_CARRIAGE_CAPACITY = 200;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    @NotNull
    private CarriageType type;    
    
    @ManyToOne
    @JoinColumn(name = "train_id")
    @NotNull
    private Train train;

    @Column(name = "capacity")
    @Min(MIN_CARRIAGE_CAPACITY)
    @Max(MAX_CARRIAGE_CAPACITY)
    @NotNull
    private int capacity;

    @Column(name = "multiplier")
    @NotNull
    private double multiplier;

    public Carriage() {
    }

    public Carriage(CarriageType type, int capacity, Train train) {
        this.type = type;
        this.capacity = capacity;
        this.train = train;
        this.multiplier = selectMultiplier();
    }

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

    public Long getId() {
        return id;
    }

    public CarriageType getType() {
        return type;
    }

    public void setType(CarriageType type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public double getMultiplier() {
        return multiplier;
    }
}
