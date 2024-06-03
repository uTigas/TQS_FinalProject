package tqsgroup.chuchu.admin.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionRequest {
    private String origin;
    private String destination;
    private int trainNumber;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private int lineNumber;
    private long price;
}
