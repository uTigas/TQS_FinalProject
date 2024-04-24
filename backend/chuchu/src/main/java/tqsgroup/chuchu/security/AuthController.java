package tqsgroup.chuchu.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tqsgroup.chuchu.data.entity.User;
import tqsgroup.chuchu.service.UserService;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserPasswordAuthenticationProvider authenticationProvider;

    @Autowired 
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService users;
    
    @GetMapping("/register")
    public String getRegisterForm(Model model){
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(User newUser){
        newUser.setAdmin(false);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        users.createUser(newUser);
        return "login";
    }

    @GetMapping("/login")
    public String getlogin(Model model) {
        return "login";
    }

}
