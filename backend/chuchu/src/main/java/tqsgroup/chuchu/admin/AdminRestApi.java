package tqsgroup.chuchu.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin/api/v1")
public class AdminRestApi {
    private static final Logger logger = LoggerFactory.getLogger(AdminRestApi.class);

    @GetMapping("/hello")
    public String hello(){
        return "Hello from SpringBoot Admin Rest API Controller";
    }
    
}
