package com.partofa.security;

import com.partofa.domain.Role;
import com.partofa.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        log.info("Init parameters method loadUserByUsername: " + "email " + email);

        com.partofa.domain.User user = userRepository.findByEmail(email);
        org.springframework.security.core.userdetails.User springUser;
        if (user == null) {
            throw new BadCredentialsException(email);
        }
        Role role = user.getRole();
        final Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(role.getParamName()));
        springUser = new User(user.getEmail(), user.getPassword(), user.getIsEnabled(), true, true, true, authorities);
        return springUser;
    }
}

