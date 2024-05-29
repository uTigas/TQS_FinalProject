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
                logger.info("Entered Deploy Mode.");
                String deployValue = args.getOptionValues("deploy").get(0);
                if (Boolean.parseBoolean(deployValue)) {
                    System.setProperty("spring.port", "");
                    System.setProperty("ionic.port", "");
                }
                else
                    logger.info("Entered Development Mode.");
            }
            else
                logger.info("Entered Development Mode.");

        };
    }
}
