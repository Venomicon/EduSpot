package com.eduspot.controller;

import com.eduspot.domain.Course;
import com.eduspot.domain.Post;
import com.eduspot.domain.User;
import com.eduspot.exception.CourseNotFoundException;
import com.eduspot.exception.UnableToPostException;
import com.eduspot.service.CourseService;
import com.eduspot.service.PostService;
import com.eduspot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class CourseController {
    public static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

    private CourseService courseService;
    private UserService userService;
    private PostService postService;

    @Autowired
    public CourseController(CourseService courseService, UserService userService, PostService postService) {
        this.courseService = courseService;
        this.userService = userService;
        this.postService = postService;
    }

    @ModelAttribute("loggedUser")
    public com.eduspot.domain.User getUser() throws UsernameNotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findUserByUsername(username);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/course/{courseId}/add")
    public String addCourseToTaken(@PathVariable Long courseId) throws Exception {
        courseService.addCourseToTaken(courseId);
        return "redirect:/success";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/course/{courseId}/remove")
    public String removeCourseFromTaken(@PathVariable Long courseId) throws CourseNotFoundException {
        courseService.removeCourseFromTaken(courseId);
        return "redirect:/created";
    }

    @RequestMapping(value = "/course/new")
    public String createCourseInitial(Course course) {
        return "course/createCoursePage";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/course/new")
    public String createCourse(@ModelAttribute("course") @Valid Course course, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return "course/createCoursePage";
        }
        course.setTeacher(getUser());
        courseService.createCourse(course);
        return "redirect:/created";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/course/{courseId}")
    public ModelAndView getCourse(@PathVariable Long courseId, Post post) throws CourseNotFoundException {
        ModelAndView modelAndView = new ModelAndView("course/courseDetails");
        Course course = courseService.findCourseById(courseId);
        List<User> students = course.getStudents();
        List<Post> posts = course.sortPostsByCreationTime();
        modelAndView.addObject("course", course);
        modelAndView.addObject("students", students);
        modelAndView.addObject("posts", posts);
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/course/{courseId}/delete")
    public String deleteCourse(@PathVariable Long courseId) throws CourseNotFoundException {
        courseService.deleteCourseById(courseId);
        return "redirect:/created";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/course/{courseId}/post")
    public String addPost(@PathVariable Long courseId, @ModelAttribute("post") Post post) throws Exception {
        Course course = courseService.findCourseById(courseId);
        User logged = getUser();
        if (course.getStudents().contains(logged) || course.getTeacher().equals(logged)) {
            postService.savePostinDatabase(post, course, logged);
            return "redirect:/success";
        } else {
            throw new UnableToPostException("Only students and teachers are able to post messages");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/course/{courseId}/post/delete")
    public String deletePost(@PathVariable Long courseId, Long postId) throws Exception {
        Course course = courseService.findCourseById(courseId);
        User logged = getUser();
        postService.deletePostById(postId, course, logged);
        return "redirect:/success";
    }
}
