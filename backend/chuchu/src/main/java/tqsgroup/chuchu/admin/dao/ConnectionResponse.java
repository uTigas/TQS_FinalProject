package tqsgroup.chuchu.admin.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import tqsgroup.chuchu.data.entity.Train;
import tqsgroup.chuchu.data.entity.Station;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionResponse {
    private Station from;
    private Station to;
    private Train train;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private int lineNumber;
    private long price;
}
