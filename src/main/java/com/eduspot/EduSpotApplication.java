package com.eduspot;

import com.eduspot.domain.CourseDto;
import com.eduspot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EduSpotApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduSpotApplication.class, args);
    }


}
