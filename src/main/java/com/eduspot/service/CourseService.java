package com.eduspot.service;

import com.eduspot.domain.Course;
import com.eduspot.domain.User;
import com.eduspot.email.EmailService;
import com.eduspot.exception.AddingCourseException;
import com.eduspot.exception.CourseAlreadyExistsException;
import com.eduspot.exception.CourseNotFoundException;
import com.eduspot.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
            course.getEndOfCourse();
            Course saved = courseRepository.save(course);
            LOGGER.info("Successfully saved course in database with id = " + saved.getCourseId());
            return saved;
        } else {
            throw new CourseAlreadyExistsException("Specified course already exists.");
        }
    }

    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> findAllCoursesTakenByUser(Long userId) {
        return courseRepository.getAllTakenByUser(userId);
    }

    public List<Course> findAllCoursesCarriedByUser(Long userId) {
        return courseRepository.getAllCarriedByUser(userId);
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
            List<User> students = course.getStudents();
            for (User student : students) {
                student.getTakenCourses().remove(course);
                //emailService.sendDeleteInformationEmail(student, course); <--- !!!!!!!!!!!!!!!
            }
            List<Course> teacherCourses = course.getTeacher().getCarriedCourses();
            teacherCourses.remove(course);
            courseRepository.deleteById(course.getCourseId());
            LOGGER.info("Successfully deleted course with id --- courseId = " + courseId);
        } else {
            throw new CourseNotFoundException("No course found with id --- courseId = " + courseId);
        }
    }

    public void addCourseToTaken(Long courseId) throws Exception {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            User user = userService.getLoggedUser();
            List<Course> takenCourses = user.getTakenCourses();
            if (!course.getTeacher().equals(user)) {
                if (!takenCourses.contains(course)) {
                    course.getStudents().add(user);
                    takenCourses.add(course);
                    LOGGER.info("Successfully added course (courseId = "+ course.getCourseId() + "), to user (userId = " + user.getUserId() + "). Currently assigned to user: "  + userService.findUserByUsername(user.getCredentials().getUsername()).getTakenCourses().size());
                } else {
                    throw new AddingCourseException("Course already taken by user --- userId = " + user.getUserId() + " --- courseId = " +course.getCourseId());
                }
            } else {
                throw new AddingCourseException("Teacher id equals student id --- userId = " + user.getUserId() + " --- courseId = " + course.getCourseId());
            }
        } else {
            throw new CourseNotFoundException("No course found with id --- courseId = " + courseId);
        }
    }

    public void removeCourseFromTaken(Long courseId) throws CourseNotFoundException {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()) {
            Course course =courseOptional.get();
            User user = userService.getLoggedUser();
            user.getTakenCourses().remove(course);
            course.getStudents().remove(user);
            LOGGER.info("Successfully removed course (courseId = "+ course.getCourseId() + "), from user (userId = " + user.getUserId() + "). Currently assigned to user: "  + userService.findUserByUsername(user.getCredentials().getUsername()).getTakenCourses().size());
        } else {
            throw new CourseNotFoundException("No course found with id --- courseId = " +courseId);
        }
    }

    @Scheduled(cron = "0 59 23 * * *")
    public void deleteFinishedCourses() {
        List<Course> finishedCourses = courseRepository.getFinishedCourses();
        int count = 0;
        for (Course course : finishedCourses) {
            try {
                deleteCourseById(course.getCourseId());
                count++;
            } catch (CourseNotFoundException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        LOGGER.info("Successfully deleted " + count + " finished courses.");
    }
}
