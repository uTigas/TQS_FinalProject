package tqsgroup.chuchu.databases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.neo4j.core.Neo4jClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class Neo4jDatabaseTest {

    @Autowired
    private Neo4jClient neo4jClient;

    @Test
    public void testNeo4jConnection() {
        // Perform a simple query to verify the connection
        assertNotNull(neo4jClient.query("MATCH (n) RETURN n").fetch().one(), "Connected to Neo4j database successfully");
        
    }
}
