package tqsgroup.chuchu.data.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;
import org.neo4j.driver.Driver;

@Configuration
@EnableTransactionManagement
@EnableNeo4jRepositories(basePackages = "tqsgroup.chuchu.data.repository.neo")
public class Neo4jConfig {

    @Autowired
    private Driver neo4jDriver;

    @Bean
    public Neo4jClient neo4jClient() {
        return Neo4jClient.create(neo4jDriver);
    }

    @Bean
    public PlatformTransactionManager neo4jTransactionManager() {
        return new Neo4jTransactionManager(neo4jDriver);
    }
}
