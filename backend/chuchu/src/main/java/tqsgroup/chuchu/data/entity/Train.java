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

    public Train() {
    }

    public Train(TrainType type, int number) {
        this.type = type;
        this.number = number;
    }

    public Long getId() {
        return id;
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
