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
    
    @GetMapping("/register")
    public String getRegisterForm(Model model){
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(User newUser){
        Role userRole = authenticationService.getRole("USER").get();
        newUser.setRole(userRole);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        authenticationService.createUser(newUser);
        return "login";
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(Model model) {
        return "login";
    }
}
