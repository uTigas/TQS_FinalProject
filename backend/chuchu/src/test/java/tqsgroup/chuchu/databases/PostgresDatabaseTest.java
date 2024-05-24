package tqsgroup.chuchu.databases;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PostgresDatabaseTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void testConnection() {
        // Ensure the DataSource is correctly configured
        assertNotNull(dataSource);

        // Use JdbcTemplate to perform a simple query
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String testQuery = "SELECT 1";
        Integer result = jdbcTemplate.queryForObject(testQuery, Integer.class);

        // Verify the result
        assertNotNull(result, "Connected to PostgreSQL database successfully");
    }
}
