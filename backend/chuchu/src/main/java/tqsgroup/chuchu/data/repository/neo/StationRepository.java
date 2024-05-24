package tqsgroup.chuchu.data.repository.neo;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import tqsgroup.chuchu.data.entity.Station;


public interface StationRepository extends Neo4jRepository<Station, Long> {
    public Station findByName(String name);
}
