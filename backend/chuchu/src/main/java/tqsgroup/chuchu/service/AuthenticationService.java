package tqsgroup.chuchu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private JwtService jwtService;

    public String authenticate(Authentication auth){
        return jwtService.generateToken(auth);
    }
}
