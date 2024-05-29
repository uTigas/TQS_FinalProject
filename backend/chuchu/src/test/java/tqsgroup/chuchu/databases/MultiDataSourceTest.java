package tqsgroup.chuchu.databases;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tqsgroup.chuchu.data.entity.Role;
import tqsgroup.chuchu.data.entity.Station;
import tqsgroup.chuchu.data.repository.RoleRepository;
import tqsgroup.chuchu.data.repository.neo.StationRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MultiDataSourceTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private StationRepository stationRepository;

    @BeforeEach
    void setup(){
        Station newStation = new Station();
        newStation.setName("Test Station");
        newStation.setNumberOfLines(9);
        stationRepository.save(newStation);

        Role newRole = new Role("TEST");
        roleRepository.save(newRole);
    }

    @AfterEach
    void teardown() {
        // Clean up the data created during the test
        roleRepository.delete( roleRepository.findByRole("TEST") );
        stationRepository.deleteAll();
    }

    @Test
    public void testMultiDataSource() {
        // Perform operations with PostgreSQL repository

        roleRepository.findAll().forEach(role -> {
           assertThat(roleRepository.findAll().size()==1);
        });

        // Perform operations with Neo4j repository
        stationRepository.findAll().forEach(station -> {
            assertThat(stationRepository.findAll().size()==1);
        });
    }
    
}
