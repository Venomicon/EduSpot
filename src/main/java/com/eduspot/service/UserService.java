package com.eduspot.service;

import com.eduspot.domain.Credentials;
import com.eduspot.domain.User;
import com.eduspot.exception.UserAlreadyExistException;
import com.eduspot.exception.UserNotFoundException;
import com.eduspot.repository.CredentialsRepository;
import com.eduspot.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private CredentialsRepository credentialsRepository;

    @Autowired
    public UserService(UserRepository userRepository, CredentialsRepository credentialsRepository) {
        this.userRepository = userRepository;
        this.credentialsRepository = credentialsRepository;
    }

    public boolean doesExistInDatabase(User checkedUser) throws UserAlreadyExistException {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getCredentials().equals(checkedUser.getCredentials())) {
                throw new UserAlreadyExistException("User with these credentials already exists.");
            }
        }
        return false;
    }

    public User signupUser(User user) throws UserAlreadyExistException {
        doesExistInDatabase(user);
        credentialsRepository.save(user.getCredentials());
        return userRepository.save(user);
    }

    public User findUserById(Long userId) throws UserNotFoundException {
        if (userRepository.findById(userId).isPresent()) {
            return userRepository.findById(userId).get();
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
