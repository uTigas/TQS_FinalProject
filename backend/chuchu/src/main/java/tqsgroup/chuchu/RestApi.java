package tqsgroup.chuchu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tqsgroup.chuchu.service.AuthenticationService;
import tqsgroup.chuchu.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class RestApi {
    private static final Logger logger = LoggerFactory.getLogger(RestApi.class);
    
    @Autowired
    private UserService service;

    @Autowired
    private AuthenticationService authentication;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(Authentication auth){
        return new ResponseEntity<>(authentication.authenticate(auth), HttpStatus.OK);

    }

}
