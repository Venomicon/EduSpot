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

    @Autowired
    UserService userService;

    @Autowired
    public CredentialsService(CredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository;
    }

    public Credentials findByUsername(String username) throws UsernameNotFoundException {
        if (credentialsRepository.findByUsername(username).isPresent()) {
            return credentialsRepository.findByUsername(username).get();
        } else {
            throw new UsernameNotFoundException("Username not found");
        }
    }

    public Credentials findById(Long userId) throws UserNotFoundException {
        User user = userService.findUserById(userId);
        if (credentialsRepository.findById(user.getCredentials().getCredentialsId()).isPresent()) {
            return credentialsRepository.findById(user.getCredentials().getCredentialsId()).get();
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    public List<Credentials> findAllCredentials() {
        return credentialsRepository.findAll();
    }
}
