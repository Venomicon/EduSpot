package com.eduspot.repository;

import com.eduspot.domain.Credentials;
import com.eduspot.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
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

    List<Credentials> findAllByUsername(String username);
}
