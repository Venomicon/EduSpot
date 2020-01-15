package com.eduspot.repository;

import com.eduspot.domain.Credentials;
import com.eduspot.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CredentialsRepository extends CrudRepository<Credentials, Long> {
    Optional<Credentials> findByUsername(String username);

    @Override
    List<Credentials> findAll();

    @Override
    Optional<Credentials> findById(Long id);

    @Override
    Credentials save(Credentials credentials);

    @Override
    void deleteById(Long id);

    @Override
    long count();
}
