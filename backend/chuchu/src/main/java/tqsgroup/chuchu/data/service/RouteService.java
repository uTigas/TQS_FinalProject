package tqsgroup.chuchu.data.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;

import tqsgroup.chuchu.data.dao.SearchRouteDAO;
import tqsgroup.chuchu.data.entity.Connection;
import tqsgroup.chuchu.data.entity.Station;

@Service
public class RouteService {
    @Autowired
    private Neo4jClient neo4j;

    public List<SearchRouteDAO> searchRoutes(Station origin, Station destination){
        List<SearchRouteDAO> routes = new ArrayList<>();
        String routesQuery = "MATCH paths = (start:Station {name: $from})-[:ORIGIN|DESTINATION*]-(end:Station {name: $to}) WITH [node in nodes(paths) WHERE node:Connection] AS paths ORDER BY size(paths) ASC RETURN collect(paths)[..10] AS routes";
        Map<String, Object> params = new HashMap<>();
        params.put("from", origin.getName());
        params.put("to", destination.getName());

        
        Collection<List<Connection>> result = neo4j.query(routesQuery)
                .bindAll(params)
                .fetch()
                .all()
                .stream()
                .map(row -> (List<Connection>) row.get("routes"))
                .collect(Collectors.toList());
        
        System.out.println(result);
        for (List<Connection> route : result) {
            SearchRouteDAO temp = new SearchRouteDAO();
            System.out.println("Route:"+ route.get(0));

            temp.setPrice(0.0);
            for (Connection con : route) {
                temp.setPrice(temp.getPrice() + con.getPrice());
            }
            temp.setDeparture(route.get(0).getDepartureTime());
            temp.setArrival(route.get(-1).getArrivalTime());
            temp.setConnections(route);
        }
        return routes;
    }
}
