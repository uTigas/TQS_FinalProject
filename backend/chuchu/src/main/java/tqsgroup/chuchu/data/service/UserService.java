package tqsgroup.chuchu.data.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import tqsgroup.chuchu.data.entity.Role;
import tqsgroup.chuchu.data.entity.User;
import tqsgroup.chuchu.data.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository repo;

    public Optional<User> findByUsername(String username){
        return repo.findByUsername(username);
    }

    public User createUser(User newUser){
        return repo.save(newUser);
    }

        @EventListener(ApplicationReadyEvent.class)
        public void insertAdminUser() {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("password");
            admin.setRole(new Role("ADMIN"));
            admin.setName("Admin User");
            
            repo.save(admin);
        }
}
