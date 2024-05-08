package tqsgroup.chuchu.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "SEATS")
public class Seat {

    private static final int MIN_SEAT_NUMBER = 1;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number")
    @Min(MIN_SEAT_NUMBER)
    @NotNull
    private int number;

    @ManyToOne
    @JoinColumn(name = "carriage_id")
    @NotNull
    private Carriage carriage;

    @Column(name = "isReserved")
    private boolean isReserved = false;

    public Seat() {
    }

    public Seat(int number, Carriage carriage) {
        this.number = number;
        this.carriage = carriage;
    }

    public Long getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Carriage getCarriage() {
        return this.carriage;
    }

    public void setCarriage(Carriage carriage) {
        this.carriage = carriage;
    }
    
    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean isReserved) {
        this.isReserved = isReserved;
    }

    public double getCarriageMultiplier() {
        return carriage.getMultiplier();
    }
}
