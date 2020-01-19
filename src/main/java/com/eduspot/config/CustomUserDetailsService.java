package com.eduspot.config;

import com.eduspot.domain.Credentials;
import com.eduspot.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("userDetailsService")
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    CredentialsService credentialsService;

    @Autowired
    public CustomUserDetailsService(CredentialsService credentialsService) {
        this.credentialsService = credentialsService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Credentials user = credentialsService.findByUsername(username);
        //return new User(user.getUsername(), "{noop}"+user.getPassword(), user.isEnabled(), true, true, true, user.getAuthorities());
        if (user != null) {
            return new User(user.getUsername(), "{noop}"+user.getPassword(), user.isEnabled(), true, true, true, user.getAuthorities());
        } else {
            throw new UsernameNotFoundException("No user found for: " + username);
        }
    }
}
