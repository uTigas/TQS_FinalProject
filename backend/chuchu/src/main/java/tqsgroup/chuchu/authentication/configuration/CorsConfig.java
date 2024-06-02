package tqsgroup.chuchu.authentication.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${ionic.port}")
    private String ionicPort;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8100", userIonic,"http://localhost:8080","http://localhost:5500") 
                .allowedMethods("GET", "POST", "PUT", "DELETE") 
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
