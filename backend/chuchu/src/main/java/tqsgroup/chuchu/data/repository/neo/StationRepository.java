package tqsgroup.chuchu.data.repository.neo;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import tqsgroup.chuchu.data.entity.Station;

@Repository
public interface StationRepository extends Neo4jRepository<Station, Long> {
    public Station findByName(String name);
}
