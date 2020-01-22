package com.eduspot.service;

import com.eduspot.domain.Course;
import com.eduspot.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    @Autowired
    CourseRepository courseRepository;

    public List<Course> findAllThatContain(String search) {
        return courseRepository.searchByString("%" + search + "%");
    }
}
