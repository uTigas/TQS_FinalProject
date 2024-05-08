package tqsgroup.chuchu.authentication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import tqsgroup.chuchu.data.entity.User;
import tqsgroup.chuchu.data.service.UserService;

@Component
public class UserPasswordAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<User> user = userService.findByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword()))
        {
            User cUser = user.get();
            List<GrantedAuthority> roles = new ArrayList<>();
            
            
            if (cUser.getRole().getRole().matches("ADMIN"))
                roles.add(new SimpleGrantedAuthority("ADMIN"));
            else
                roles.add(new SimpleGrantedAuthority("USER"));
            
            return new UsernamePasswordAuthenticationToken(cUser, password, roles);
        }
        else
            throw new BadCredentialsException("Incorrect username or password");
        }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
