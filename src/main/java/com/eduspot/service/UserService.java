package com.eduspot.service;

import com.eduspot.domain.Credentials;
import com.eduspot.domain.User;
import com.eduspot.exception.UserAlreadyExistException;
import com.eduspot.exception.UserNotFoundException;
import com.eduspot.repository.CredentialsRepository;
import com.eduspot.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    public static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;
    private CredentialsRepository credentialsRepository;

    @Autowired
    public UserService(UserRepository userRepository, CredentialsRepository credentialsRepository) {
        this.userRepository = userRepository;
        this.credentialsRepository = credentialsRepository;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public boolean isExist(User checkedUser) {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getCredentials().equals(checkedUser.getCredentials())) {
                return true;
            }
        }
        return false;
    }

    public User signupUser(User user) throws UserAlreadyExistException {
        if (!isExist(user)) {
            credentialsRepository.save(user.getCredentials());
            User saved = userRepository.save(user);
            LOGGER.info("Successfully saved user in a database");
            return saved;
        } else {
            throw new UserAlreadyExistException("User with these credentials already exists");
        }
    }

    public User findUserById(Long userId) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException("User not found --- userId: " + userId);
        }
    }

    public User getLoggedUser() throws UsernameNotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return findUserByUsername(username);
    }

    public User findUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Credentials> userCredentials = credentialsRepository.findByUsername(username);
        if (userCredentials.isPresent()) {
            return userCredentials.get().getUser();
        } else {
            throw new UsernameNotFoundException("No user found for: " + username);
        }
    }
}
