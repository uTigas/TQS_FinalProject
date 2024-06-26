package tqsgroup.chuchu.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

@Entity
@Table(name = "TRAINS")
public class Train {

    private static final int MIN_TRAIN_NUMBER = 1;
    private static final int MAX_TRAIN_NUMBER = 9999;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    @NotNull
    private TrainType type;

    @Id
    @Column(name = "number", unique = true)
    @Min(MIN_TRAIN_NUMBER)
    @Max(MAX_TRAIN_NUMBER)
    private int number;

    public Train() {
    }

    public Train(TrainType type, int number) {
        this.type = type;
        this.number = number;
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
}
