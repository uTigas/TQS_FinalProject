package tqsgroup.chuchu.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tqsgroup.chuchu.data.entity.User;


@RestController
@RequestMapping("/private/api/v1")
@PreAuthorize("@securityService.hasRole('USER')")
public class RestApi {
    private static final Logger logger = LoggerFactory.getLogger(RestApi.class);

    @GetMapping("/hello")
    public String hello(){
        return "Hello from SpringBoot API Rest Controller";
    }

    @GetMapping("/user")
    public ResponseEntity<User> fetchUser(){
        logger.info("Fetching user Details");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user =(User) authentication.getPrincipal();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
