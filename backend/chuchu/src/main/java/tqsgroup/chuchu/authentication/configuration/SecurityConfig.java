package tqsgroup.chuchu.authentication.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import tqsgroup.chuchu.authentication.support.CustomLoginHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(
            auth -> auth
                .requestMatchers("/auth/**")
                .permitAll()
                .anyRequest().authenticated())
        .formLogin(form -> form
            .loginPage("/auth/login")
            .successHandler(customAuthenticationSuccessHandler())
            .permitAll()
        )
        .logout((logout) -> logout
        .logoutUrl("/auth/logout")
        .logoutSuccessUrl("/")
        .deleteCookies("JSESSIONID")
        .permitAll()
        );

    return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationSuccessHandler customAuthenticationSuccessHandler(){
        return new CustomLoginHandler();
    }
}