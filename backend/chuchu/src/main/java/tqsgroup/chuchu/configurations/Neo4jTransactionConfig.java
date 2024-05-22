package tqsgroup.chuchu.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
@EnableTransactionManagement
public class Neo4jTransactionConfig {

    // Define Neo4jTemplate bean
    @Bean
    public Neo4jTemplate neo4jTemplate(Neo4jClient transactionManager) {
        return new Neo4jTemplate(transactionManager);
    }

    // Define Neo4jTransactionManager bean
    @Bean
    public PlatformTransactionManager transactionManager(org.neo4j.driver.Driver driver) {
        return new Neo4jTransactionManager(driver);
    }

    // Define TransactionTemplate bean
    @Bean
    public TransactionTemplate transactionTemplate(Neo4jTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }
}
