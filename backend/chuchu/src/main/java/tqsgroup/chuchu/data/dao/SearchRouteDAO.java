package tqsgroup.chuchu.data.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

import tqsgroup.chuchu.data.entity.Connection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchRouteDAO {
    List<Connection> connections;
    Double price;
    LocalTime departure;
    LocalTime arrival;
}
