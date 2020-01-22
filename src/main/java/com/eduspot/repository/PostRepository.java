package com.eduspot.repository;

import com.eduspot.domain.Credentials;
import com.eduspot.domain.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PostRepository extends CrudRepository<Post, Long> {
    @Override
    List<Post> findAll();

    @Override
    Optional<Post> findById(Long id);

    @Override
    Post save(Post post);

    @Override
    void deleteById(Long id);

    @Override
    long count();
}
