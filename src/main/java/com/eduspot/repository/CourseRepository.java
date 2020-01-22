package com.eduspot.repository;

import com.eduspot.domain.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CourseRepository extends CrudRepository<Course, Long> {
    @Override
    List<Course> findAll();

    @Override
    Optional<Course> findById(Long id);

    @Override
    Course save(Course course);

    @Override
    void deleteById(Long id);

    @Override
    long count();

    @Query(nativeQuery = true)
    List<Course> searchByString(@Param("search") String search);
}
