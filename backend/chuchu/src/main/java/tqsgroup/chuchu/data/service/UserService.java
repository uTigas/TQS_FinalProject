package tqsgroup.chuchu.data.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import tqsgroup.chuchu.data.entity.Role;
import tqsgroup.chuchu.data.entity.User;
import tqsgroup.chuchu.data.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository repo;

    @Autowired
    RoleService roleService;

    @Autowired 
    private PasswordEncoder passwordEncoder;

    public Optional<User> findByUsername(String username){
        return repo.findByUsername(username);
    }

    public User createUser(User newUser){
        return repo.save(newUser);
    }

        @EventListener(ApplicationReadyEvent.class)
        public void insertAdminUser() {
            
            Role adminRole = new Role("ADMIN");
            roleService.save(adminRole);
            Role userRole = new Role("USER");
            roleService.save(userRole);

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setRole(adminRole);
            admin.setName("Admin User");
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("password"));
            user.setRole(userRole);
            user.setName("User");
            
            repo.save(admin);
        }
}
