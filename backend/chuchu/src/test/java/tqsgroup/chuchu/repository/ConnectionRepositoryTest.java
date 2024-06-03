package tqsgroup.chuchu.repository;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tqsgroup.chuchu.data.repository.neo.StationRepository;

@SpringBootTest
public class ConnectionRepositoryTest {
    
    @Autowired
    private StationRepository stationRepository;
    
    @BeforeEach
    void setUp() {
        stationRepository.deleteAll();
    }

}
