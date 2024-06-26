package tqsgroup.chuchu.authentication.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqsgroup.chuchu.data.entity.Role;
import tqsgroup.chuchu.data.entity.User;
import tqsgroup.chuchu.data.repository.RoleRepository;
import tqsgroup.chuchu.data.service.UserService;

@Service
public class AuthenticationService {
    @Autowired
    private UserService userService;

    @Autowired 
    private RoleRepository roleRepository;


    public User createUser(User newUser){
        return userService.createUser(newUser);
    }

    public Optional<Role> getRole(String role){
        return roleRepository.findById(role);
    }


}
