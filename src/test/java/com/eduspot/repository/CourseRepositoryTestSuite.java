package com.eduspot.repository;

import com.eduspot.domain.*;
import com.eduspot.mapper.CourseMapper;
import com.eduspot.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class CourseRepositoryTestSuite {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseRepositoryTestSuite.class);
    private static User teacher;
    private static Course course;
    static UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    UserMapper userMapper;


    @BeforeAll
    public static void prepareData() {
        Credentials teacherCredentials = Credentials.builder()
                .email("teacher@gmail.com")
                .password("teacher123")
                .username("teacher123")
                .build();

        teacher = User.builder()
                .firstName("Teacher")
                .lastName("TEACHER")
                .birthDate(LocalDate.of(2000, 5, 20))
                .credentials(teacherCredentials)
                .build();

        userRepository.save(teacher);
    }

    @AfterAll
    public static void cleanUp() {
        //userRepository.deleteById(teacher.getUserId());
    }

    @Before
    public void initialize() {
        course = Course.builder()
                .title("Test title")
                .teacher(teacher)
                .level("Entry")
                .startDate(LocalDate.of(2020, 3, 20))
                .startTime(LocalTime.now())
                .weeks(2)
                .build();
    }

    @Test
    public void shouldFindAllCourses() {
        //Given
        Course saved = courseRepository.save(course);

        //When
        List<Course> fetched = courseRepository.findAll();

        //Then
        Assert.assertTrue(fetched.size() > 0);
        LOGGER.info("Test completed");

        //CleanUp
        courseRepository.deleteById(saved.getCourseId());
    }

    @Test
    public void shouldFindCourseById() {
        //Given
        Course saved = courseRepository.save(course);

        //When
        Optional<Course> fetched = courseRepository.findById(saved.getCourseId());

        //Then
        Assert.assertTrue(fetched.isPresent());
        LOGGER.info("Test completed");

        //CleanUp
        courseRepository.deleteById(saved.getCourseId());
    }

    @Test
    public void shouldSaveCourse() {}

    @Test
    public void shouldDeleteById() {}

    @Test
    public void shouldCountCourses() {}

    @Test
    public void shouldFindWithSearch() {}

    @Test
    public void shouldFindAllTakenByUser() {}

    @Test
    public void shouldFindAllCarriedByUser() {}

    @Test
    public void shouldFindAllFinishedCourses() {}
}
