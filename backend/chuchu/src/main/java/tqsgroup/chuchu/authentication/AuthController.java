package tqsgroup.chuchu.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import tqsgroup.chuchu.authentication.service.AuthenticationService;
import tqsgroup.chuchu.data.dao.UserDAO;
import tqsgroup.chuchu.data.entity.Role;
import tqsgroup.chuchu.data.entity.User;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private String loginForm = "login";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationService authenticationService;
    
    @Operation(summary = "Render registration form")
    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        logger.info("Received request to get register form.");
        return "register";
    }

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public String registerUser(User newUser) {
        logger.info("Received request to register a new user");
        Role userRole = authenticationService.getRole("USER").get();
        newUser.setRole(userRole);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        authenticationService.createUser(newUser);
        logger.info("User registered successfully");
        return loginForm;
    }

    @Operation(summary = "Render login form")
    @GetMapping("/login")
    public String getLogin(Model model) {
        logger.info("Received request to get login form.");
        return loginForm;
    }

    @PostMapping("/login")
    public String postLogin(Model model) {
        logger.info("Received request to process login form.");
        return loginForm;
    }

    @GetMapping("/user")
    public ResponseEntity<Object> getUser(Model model) {
        logger.info("Received request to get user details.");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            User principal = (User) authentication.getPrincipal();
            UserDAO userDAO = new UserDAO();
            userDAO.setUsername(principal.getUsername());
            userDAO.setName(principal.getName());
            userDAO.setRole(principal.getRole());
            
            return new ResponseEntity<>(userDAO, HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<>("Not logged In", HttpStatus.FORBIDDEN);
        }

    }
}
