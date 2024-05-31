package tqsgroup.chuchu.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import tqsgroup.chuchu.authentication.service.AuthenticationService;
import tqsgroup.chuchu.data.entity.Role;
import tqsgroup.chuchu.data.entity.User;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired 
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationService authenticationService;
    
    @Operation(summary = "Render registration form")
    @GetMapping("/register")
    public String getRegisterForm(Model model){
        logger.info("Rendering register form");
        return "register";
    }

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public String registerUser(User newUser){
        logger.info("Received registration request for user: {}", newUser.getUsername());
        Role userRole = authenticationService.getRole("USER").get();
        newUser.setRole(userRole);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        authenticationService.createUser(newUser);
        logger.info("User registered successfully: {}", newUser.getUsername());
        return "login";
    }

    @Operation(summary = "Render login form")
    @GetMapping("/login")
    public String getLogin(Model model) {
        logger.info("Rendering login form");
        return "login";
    }
}
