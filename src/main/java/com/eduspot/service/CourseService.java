package com.eduspot.service;

import com.eduspot.domain.Course;
import com.eduspot.domain.User;
import com.eduspot.exception.CourseAlreadyExistsException;
import com.eduspot.exception.CourseNotFoundException;
import com.eduspot.exception.UserNotFoundException;
import com.eduspot.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    public static final Logger LOGGER = LoggerFactory.getLogger(CourseService.class);

    CourseRepository courseRepository;
    UserService userService;

    @Autowired
    public CourseService(CourseRepository courseRepository, UserService userService) {
        this.courseRepository = courseRepository;
        this.userService = userService;
    }

    public boolean isExist(Course checkedCourse) {
        List<Course> courses = courseRepository.findAll();
        for (Course course : courses) {
            if (course.equals(checkedCourse)) {
                return true;
            }
        }
        return false;
    }

    public Course createCourse(Course course) throws CourseAlreadyExistsException {
        if (!isExist(course)) {
            try {
                List<Course> teacherCourses = this.findAllCoursesCarriedByUser(course.getTeacher().getUserId());
                Course createdCourse = courseRepository.save(course);
                teacherCourses.add(createdCourse);
                return createdCourse;
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                return null;
            }
        } else {
            throw new CourseAlreadyExistsException("Specified course already exists.");
        }
    }

    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> findAllCoursesTakenByUser(Long userId) throws UserNotFoundException {
        return userService.findUserById(userId).getTakenCourses();
    }

    public List<Course> findAllCoursesCarriedByUser(Long userId) throws UserNotFoundException {
        return userService.findUserById(userId).getCarriedCourses();
    }

    public List<Course> findAllCarriedByUsername(String username) {
        List<Course> courses = userService.findUserByUsername(username).getCarriedCourses();
        if (!courses.isEmpty()) {
            return courses;
        } else {
            return new ArrayList<>();
        }
    }

    public Course findCourseById(Long courseId) throws CourseNotFoundException {
        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isPresent()) {
            return course.get();
        } else {
            throw new CourseNotFoundException("No course found with id --- courseId = " + courseId);
        }
    }

    public void deleteCourseById(Long courseId) throws CourseNotFoundException {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            courseRepository.deleteById(course.getCourseId());
            List<User> students = course.getStudents();
            for (User student : students) {
                student.getTakenCourses().remove(course);
                //SEND INFORMATION EMAIL???
            }
            List<Course> teacherCourses = course.getTeacher().getCarriedCourses();
            teacherCourses.remove(course);
        } else {
            throw new CourseNotFoundException("No course found with id --- courseId = " + courseId);
        }
    }

    public Course saveCourse(Course course) throws Exception {
        User teacher = userService.findUserById(course.getTeacher().getUserId());
        course.getDayOfClasses();
        teacher.getCarriedCourses().add(course);
        return courseRepository.save(course);
    }
}
