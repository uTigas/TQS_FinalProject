package tqsgroup.chuchu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChuchuApplication {
    private static final Logger logger = LoggerFactory.getLogger(ChuchuApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ChuchuApplication.class, args);
	}

	// Adjust port values based on deploy flag
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationArguments args) {
        return arg -> {
            if (args.containsOption("deploy")) {
                String deployValue = args.getOptionValues("deploy").get(0);
                if (Boolean.parseBoolean(deployValue)) {
                    logger.info("Entered Deploy Mode.");
                    System.setProperty("spring.port", "");
                    System.setProperty("ionic.port", "");
                    System.setProperty("spring.data.neo4j.uri", "bolt://neo:7687");
                    System.setProperty("spring.datasource.url", "jdbc:postgresql://postgres:5432/chuchu");
                }
                else
                    logger.info("Entered Development Mode.");
            }
            else{
                logger.info("Entered Development Mode.");
                System.setProperty("spring.port", "8080");
                System.setProperty("ionic.port", "8100");
                System.setProperty("spring.datasource.url", "jdbc:postgresql://localhost:5432/chuchu");
                System.setProperty("spring.data.neo4j.uri", "bolt://localhost:7687");
            }

        };
    }
}
