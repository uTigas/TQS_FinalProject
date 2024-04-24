package tqsgroup.chuchu.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ROUTES")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "origin")
    private Station origin;

    @Column(name = "destination")
    private Station destination;

    @Column(name = "stops")
    private Connection[] stops;

    @PrePersist
    @PreUpdate
    // All stops must connect to the previous and next stops
    private void validateStops() {
        if (stops.length < 2) {
            throw new IllegalStateException("A route must have at least two stops");
        }

        this.origin = stops[0].getOrigin();
        this.destination = stops[stops.length - 1].getDestination();

        for (int i = 1; i < stops.length; i++) {
            if (!stops[i].getOrigin().equals(stops[i - 1].getDestination())) {
                throw new IllegalStateException("Stops must connect to the previous and next stops");
            }
        }
    }

    public Route() {
    }

    public Route(Connection[] stops) {
        this.stops = stops;
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

    public Connection[] getStops() {
        return stops;
    }

    public void setStops(Connection[] stops) {
        this.stops = stops;
    }
}
