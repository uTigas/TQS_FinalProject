package tqsgroup.chuchu.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "SEATS")
public class Seat {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seatNumber")
    private int seatNumber;

    @ManyToOne
    private Carriage carriage;

    public Seat() {
    }

    public Seat(int seatNumber, Carriage carriage) {
        this.seatNumber = seatNumber;
        this.carriage = carriage;
    }

    public Long getId() {
        return id;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Carriage getCarriage() {
        return this.carriage;
    }

    public void setCarriage(Carriage carriage) {
        this.carriage = carriage;
    }  
}
