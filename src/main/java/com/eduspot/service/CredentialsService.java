package com.eduspot.service;

import com.eduspot.domain.Credentials;
import com.eduspot.domain.User;
import com.eduspot.exception.UserNotFoundException;
import com.eduspot.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CredentialsService {
    CredentialsRepository credentialsRepository;
    UserService userService;

    @Autowired
    public CredentialsService(CredentialsRepository credentialsRepository, UserService userService) {
        this.credentialsRepository = credentialsRepository;
        this.userService = userService;
    }

    public Credentials findByUsername(String username) throws UsernameNotFoundException {
        Optional<Credentials> credentials = credentialsRepository.findByUsername(username);
        if (credentials.isPresent()) {
            return credentials.get();
        } else {
            throw new UsernameNotFoundException("Username not found");
        }
    }

    public Credentials findById(Long userId) throws UserNotFoundException {
        User user = userService.findUserById(userId);
        Optional<Credentials> credentials = credentialsRepository.findById(user.getCredentials().getCredentialsId());
        if (credentials.isPresent()) {
            return credentials.get();
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    public List<Credentials> findAllCredentials() {
        return credentialsRepository.findAll();
    }

}
