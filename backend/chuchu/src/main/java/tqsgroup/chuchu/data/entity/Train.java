package tqsgroup.chuchu.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "TRAINS")
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TrainType type;

    @Column(name = "number")
    private int number;

    @Column(name = "carriages")
    private Carriage[] carriages;

    @PrePersist
    @PreUpdate
    private void validateCarriages() {
        if (carriages.length == 0) {
            throw new IllegalStateException("Train must have at least one carriage");
        }

        if (type.equals(TrainType.ALPHA) || type.equals(TrainType.INTERCITY)) {
            for (Carriage c : carriages) {
                if (!c.getType().equals(CarriageType.FIRST_CLASS) && !c.getType().equals(CarriageType.SECOND_CLASS)) {
                    throw new IllegalStateException("Alpha and Intercity trains can only have first and second class carriages");
                }
            }
        } else {
            for (Carriage c : carriages) {
                if (!c.getType().equals(CarriageType.NORMAL)) {
                    throw new IllegalStateException("Normal trains can only have normal carriages");
                }
            }
        }
    }

    public Train() {
    }

    public Train(TrainType type, int number, Carriage[] carriages) {
        this.type = type;
        this.number = number;
        this.carriages = carriages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TrainType getType() {
        return type;
    }

    public void setType(TrainType type) {
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Carriage[] getCarriages() {
        return carriages;
    }

    public void setCarriages(Carriage[] carriages) {
        this.carriages = carriages;
    }

    public int getTotalCapacity() {
        int capacity = 0;
        for (Carriage c : carriages) {
            capacity += c.getCapacity();
        }
        return capacity;
    }
}
