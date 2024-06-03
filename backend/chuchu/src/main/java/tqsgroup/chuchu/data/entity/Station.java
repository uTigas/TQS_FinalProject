package tqsgroup.chuchu.data.entity;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import jakarta.validation.constraints.Size;

@Node("Station")
public class Station {

    private static final int MIN_STATION_NAME_LENGTH = 3;
    private static final int MAX_STATION_NAME_LENGTH = 255;

    @Id
    private String name;

    @Size(min = MIN_STATION_NAME_LENGTH, max = MAX_STATION_NAME_LENGTH)
    private int numberOfLines; //Should be equivalent to the number of screens

    public Station() {
    }

    public Station(String name, int numberOfLines) {
        this.name = name;
        this.numberOfLines = numberOfLines;
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
