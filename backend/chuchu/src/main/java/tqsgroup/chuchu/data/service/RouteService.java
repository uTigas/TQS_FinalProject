package tqsgroup.chuchu.data.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.neo4j.driver.internal.InternalNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;

import tqsgroup.chuchu.data.dao.SearchRouteDAO;
import tqsgroup.chuchu.data.entity.Connection;
import tqsgroup.chuchu.data.entity.Station;
import tqsgroup.chuchu.data.repository.neo.ConnectionRepository;

@Service
public class RouteService {
    @Autowired
    private Neo4jClient neo4j;

    @Autowired
    private ConnectionRepository connectionRepository;

    public List<SearchRouteDAO> searchRoutes(Station origin, Station destination){
        List<SearchRouteDAO> routes = new ArrayList<>();
        try {
            String routesQuery = "MATCH paths = (start:Station {name: $from})-[:ORIGIN|DESTINATION*]-(end:Station {name: $to}) WITH [node in nodes(paths) WHERE node:Connection] AS paths ORDER BY size(paths) ASC RETURN collect(paths)[..10] AS routes";
            Map<String, Object> params = new HashMap<>();
            params.put("from", origin.getName());
            params.put("to", destination.getName());
            
            @SuppressWarnings("unchecked")
            List<List<InternalNode>> result = (List<List<InternalNode>>) neo4j.query(routesQuery)
                    .bindAll(params)
                    .fetch()
                    .all()
                    .stream()
                    .map(row -> row.get("routes"))
                    .toList().get(0);
    
            List<SearchRouteDAO> searchRouteDAOs = new ArrayList<>();
            
            for (List<InternalNode> routeNodes : result) {
                System.out.println(routeNodes.get(0).keys());
                SearchRouteDAO searchRouteDAO = new SearchRouteDAO();
                Double price = 0.0;
                List<Connection> connections = new ArrayList<>();
                for (InternalNode node : routeNodes) {
                    String nodeId = node.get("id").toString().replace("\"","");
                    System.out.println("Node Id:" + nodeId.length());
                    Connection connection = connectionRepository.findById(UUID.fromString(nodeId)).orElse(null);
                    if (connection != null) {
                        connections.add(connection);
                        price += connection.getPrice();
                    }
                }
                if (!connections.isEmpty()) {
                    searchRouteDAO.setPrice(price);
                    searchRouteDAO.setDeparture(connections.get(0).getDepartureTime());
                    searchRouteDAO.setArrival(connections.get(connections.size() - 1).getArrivalTime());
                    searchRouteDAO.setConnections(connections);
                    searchRouteDAOs.add(searchRouteDAO);
                }
                
            }
    
            return searchRouteDAOs;
    
        } catch (Exception e) {
            e.printStackTrace();
            return routes;
        }
    }
    
}
