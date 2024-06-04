package tqsgroup.chuchu.data.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import tqsgroup.chuchu.data.entity.Train;
import tqsgroup.chuchu.data.entity.Station;

import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionDAO {
    private UUID id;
    private Station from;
    private Station to;
    private Train train;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private int lineNumber;
    private Double price;
}
