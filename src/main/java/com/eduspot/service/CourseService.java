package com.eduspot.service;

import com.eduspot.domain.Course;
import com.eduspot.domain.User;
import com.eduspot.exception.AddingCourseException;
import com.eduspot.exception.CourseAlreadyExistsException;
import com.eduspot.exception.CourseNotFoundException;
import com.eduspot.exception.UserNotFoundException;
import com.eduspot.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

    public Course createCourse(Course course) throws Exception {
        if (!isExist(course)) {
            User teacher = userService.findUserById(course.getTeacher().getUserId());
            course.getDayOfClasses();
            teacher.getCarriedCourses().add(course);
            return courseRepository.save(course);
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
                //SEND INFORMATION EMAIL
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

    public void addCourseToTaken(Course course) throws Exception {
        Optional<Course> courseOptional = courseRepository.findById(course.getCourseId());
        if (courseOptional.isPresent()) {
            org.springframework.security.core.userdetails.User userDetails = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername();
            User user = userService.findUserByUsername(username);
            List<Course> takenCourses = user.getTakenCourses();
            if (!course.getTeacher().equals(user)) {
                if (!takenCourses.contains(course)) {
                    course.getStudents().add(user);
                    takenCourses.add(course);
                    LOGGER.info("Successfully added course (courseId = "+ course.getCourseId()+ "), to user (userId = " + user.getUserId() + "). Currently assigned to user: "  + userService.findUserByUsername(username).getTakenCourses().size());
                } else {
                    throw new AddingCourseException("Course already taken by user --- userId = " + user.getUserId() + " --- courseId = " +course.getCourseId());
                }
            } else {
                throw new AddingCourseException("Teacher id equals student id --- userId = " + user.getUserId() + " --- courseId = " + course.getCourseId());
            }
        } else {
            throw new CourseNotFoundException("No course found with id --- courseId = " + course.getCourseId());
        }
    }
}
