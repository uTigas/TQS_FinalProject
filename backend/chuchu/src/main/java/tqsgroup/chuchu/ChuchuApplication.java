package tqsgroup.chuchu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.config.EnableNeo4jAuditing;

@SpringBootApplication
@EnableNeo4jAuditing
public class ChuchuApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChuchuApplication.class, args);
	}
}
