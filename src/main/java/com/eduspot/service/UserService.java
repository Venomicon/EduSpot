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
            return userRepository.save(user);
        } else {
            throw new UserAlreadyExistException("User with these credentials already exists.");
        }
    }

    public User findUserById(Long userId) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    public User updateUser(User user) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(user.getUserId());
        if (userOptional.isPresent()) {
            User inDatabase = userOptional.get();
            LOGGER.info("User in database: " + inDatabase.getFirstName() + " " + inDatabase.getLastName() + ",  " + inDatabase.getBirthDate());
            inDatabase.setFirstName(user.getFirstName());
            inDatabase.setLastName(user.getLastName());
            inDatabase.setBirthDate(user.getBirthDate());
            LOGGER.info("User saved in database: " + inDatabase.getFirstName() + " " + inDatabase.getLastName() + ",  " + inDatabase.getBirthDate());
            return userRepository.save(inDatabase);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    public User findUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Credentials> userCredentials = credentialsRepository.findByUsername(username);
        if (userCredentials.isPresent()) {
            return userCredentials.get().getUser();
        } else if (username.equalsIgnoreCase("admin")) {
            return new User();
        } else {
            throw new UsernameNotFoundException("No user found for: " + username);
        }
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
