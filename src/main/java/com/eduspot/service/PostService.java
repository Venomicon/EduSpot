package com.eduspot.service;

import com.eduspot.domain.Course;
import com.eduspot.domain.Post;
import com.eduspot.domain.User;
import com.eduspot.exception.PostAlreadyExistException;
import com.eduspot.exception.PostNotFoundException;
import com.eduspot.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

@Service
public class PostService {
    public static final Logger LOGGER = LoggerFactory.getLogger(PostService.class);

    @Autowired
    PostRepository postRepository;

    public boolean isExist(Post checkedPost) {
        List<Post> posts = postRepository.findAll();
        if (posts.isEmpty()) {
            return false;
        } else {
            for (Post post : posts) {
                if (post.equals(checkedPost)) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    public Post findPostById(Long postId) throws PostNotFoundException{
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            return postOptional.get();
        } else {
            throw new PostNotFoundException("No post found with id = " + postId);
        }
    }

    public Post savePostinDatabase(Post post, Course course, User user) throws PostAlreadyExistException {
        if (!isExist(post)) {
            post.setCourse(course);
            post.setAuthor(user);
            course.getPosts().add(post);
            user.getPosts().add(post);
            Post saved = postRepository.save(post);
            LOGGER.info("Successfully saved post in database");
            return saved;
        } else {
            throw new PostAlreadyExistException("Specified post already exists");
        }
    }

    public void deletePostById(Long postId, Course course, User user) throws PostNotFoundException {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            user.getPosts().remove(post);
            course.getPosts().remove(post);
            postRepository.deleteById(postId);
            LOGGER.info("Successfully deleted post with id = " + postId);
        } else {
            throw new PostNotFoundException("No post found with id = " + postId);
        }
    }
}
