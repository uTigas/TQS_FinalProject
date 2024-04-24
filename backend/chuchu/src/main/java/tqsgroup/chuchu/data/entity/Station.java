package tqsgroup.chuchu.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "STATIONS")
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "lines")
    private int numberOfLines; //Should be equivalent to the number of screens

    public Station() {
    }

    public Station(String name, int numberOfLines) {
        this.name = name;
        this.numberOfLines = numberOfLines;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfLines() {
        return numberOfLines;
    }

    public void setNumberOfLines(int numberOfLines) {
        this.numberOfLines = numberOfLines;
    }
}
