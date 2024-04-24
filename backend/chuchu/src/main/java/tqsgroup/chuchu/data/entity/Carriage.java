package tqsgroup.chuchu.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "CARRIAGES")
public class Carriage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CarriageType type;

    @Column(name = "capacity")
    private int capacity;

    @PrePersist
    @PreUpdate
    private void validateCapacity() {
        if (capacity <= 0) {
            throw new IllegalStateException("Carriage must have a positive capacity");
        }
    }

    public Carriage() {
    }

    public Carriage(CarriageType type, int capacity) {
        this.type = type;
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
