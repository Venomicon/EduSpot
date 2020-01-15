package com.eduspot.mapper;

import com.eduspot.domain.Credentials;
import com.eduspot.domain.CredentialsDto;
import com.eduspot.exception.UserNotFoundException;
import com.eduspot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CredentialsMapper {
    private static Logger LOGGER = LoggerFactory.getLogger(CredentialsMapper.class);

    private UserService userService;

    @Autowired
    public CredentialsMapper(UserService userService) {
        this.userService = userService;
    }

    public Credentials mapToCredentials(CredentialsDto credentialsDto) {
        Credentials credentials = Credentials.builder()
                .credentialsId(credentialsDto.getCredentialsId())
                .username(credentialsDto.getUsername())
                .password(credentialsDto.getPassword())
                .email(credentialsDto.getEmail())
                .build();
        try {
            credentials.setUser(userService.findUserById(credentialsDto.getUserId()));
        } catch (UserNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return credentials;
    }

    public CredentialsDto mapToCredentialsDto(Credentials credentials) {
        return CredentialsDto.builder()
                .userId(credentials.getUser().getUserId())
                .credentialsId(credentials.getCredentialsId())
                .username(credentials.getUsername())
                .password(credentials.getPassword())
                .email(credentials.getEmail())
                .build();
    }

    public List<Credentials> mapToCredentialsList(List<CredentialsDto> credentialsDtos) {
        return credentialsDtos.stream()
                .map(this::mapToCredentials)
                .collect(Collectors.toList());
    }

    public List<CredentialsDto> mapToCredentialsDtoList(List<Credentials> credentialsList) {
        return credentialsList.stream()
                .map(this::mapToCredentialsDto)
                .collect(Collectors.toList());
    }
}
