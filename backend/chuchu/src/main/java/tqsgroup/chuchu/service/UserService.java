package tqsgroup.chuchu.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqsgroup.chuchu.data.entity.User;
import tqsgroup.chuchu.data.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository repo;

    public Optional<User> findByUsername(String username){
        return repo.findByUsername(username);
    }
}
