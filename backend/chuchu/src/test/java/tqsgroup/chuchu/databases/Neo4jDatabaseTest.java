package tqsgroup.chuchu.databases;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tqsgroup.chuchu.data.entity.Station;
import tqsgroup.chuchu.data.repository.neo.StationRepository;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class Neo4jDatabaseTest {

    @Autowired
    private StationRepository stationRepository;

    @Test
    public void testNeo4jConnection() {
        int prevSize = stationRepository.findAll().size();

        Station newStation = new Station();
        newStation.setName("Test Station");
        newStation.setNumberOfLines(999);
        stationRepository.save(newStation);
        // Perform a simple query to verify the connection
        assertEquals(stationRepository.findAll().size(), prevSize + 1);
        
    }
}
