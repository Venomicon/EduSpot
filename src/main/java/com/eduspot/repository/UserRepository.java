package com.eduspot.repository;

import com.eduspot.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, Long> {
    @Override
    List<User> findAll();

    @Override
    Optional<User> findById(Long id);

    @Override
    User save(User user);

    @Override
    void deleteById(Long id);

    @Override
    long count();
}
