package com.partofa.service.impl;

import com.partofa.domain.Role;
import com.partofa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by tust on 10.09.2016.
 */
@Service("myService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User loadUserByUsername(String email)
            throws UsernameNotFoundException {
        com.partofa.domain.User user = userRepository.findByEmail(email);
        User springUser = null;
        if (user != null) {
            Role role = user.getRole();
            final Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

            authorities.add(new SimpleGrantedAuthority(role.name().toString()));
            springUser = new User(user.getEmail(), user.getPassword(), user.getIsEnabled(), true, true, true, authorities);
        } else {
            throw new BadCredentialsException(email);
        }
        return springUser;
    }
}
