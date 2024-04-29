package tqsgroup.chuchu.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/private/api/v1")
public class RestApi {
    private static final Logger logger = LoggerFactory.getLogger(RestApi.class);

    @GetMapping("/hello")
    public String hello(){
        return "Hello from SpringBoot API Rest Controller";
    }
    
}
