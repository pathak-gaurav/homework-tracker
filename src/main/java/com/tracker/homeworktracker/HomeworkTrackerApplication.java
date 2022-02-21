package com.tracker.homeworktracker;

import com.tracker.homeworktracker.entity.User;
import com.tracker.homeworktracker.entity.UserDetails;
import com.tracker.homeworktracker.repository.UserDetailsRepository;
import com.tracker.homeworktracker.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class HomeworkTrackerApplication implements CommandLineRunner {

    private UserRepository userRepository;
    private UserDetailsRepository userDetailsRepository;

    public HomeworkTrackerApplication(UserRepository userRepository, UserDetailsRepository userDetailsRepository) {
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(HomeworkTrackerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //Saving Data in Different Tables
        User userDrake = new User("Drake", "Bell", "drakebell@gmail.com",
                "drake.bell", "qwerty@12345");
        User userJohn = new User("John", "Ross", "johnross@gmail.com",
                "abc", "abc");
        userDrake.setAddress("1765, West End Street, Toronto Ontario");
        userDrake.setDepartment("Computer Science Engineering");
        userDrake.setMobile("+1-0862576571");

        userJohn.setAddress("11, Downing Street, London, United Kingdom");
        userJohn.setDepartment("Civil Engineering");
        userJohn.setMobile("+44-1868576579");

        ArrayList<UserDetails> userDetailsListDrake = new ArrayList<>(Arrays.asList(new UserDetails("Science"),
                new UserDetails("Maths"), new UserDetails("English")));
        List<UserDetails> userDetails = userDetailsRepository.saveAll(userDetailsListDrake);
        userRepository.save(userDrake);

        // Fetching Data From Database
        UserDetails drakeEnglishCourse = userDetailsRepository.findByCourseName("English");
        drakeEnglishCourse.setAssignmentPendingDate(LocalDate.of(2022, Month.FEBRUARY, 25));
        drakeEnglishCourse.setUser(userDrake);
        userDetailsRepository.save(drakeEnglishCourse);

        UserDetails johnScienceCourse = userDetailsRepository.findByCourseName("Science");
        johnScienceCourse.setAssignmentPendingDate(LocalDate.of(2022, Month.JANUARY, 19));
        johnScienceCourse.setUser(userJohn);
        userDetailsRepository.save(johnScienceCourse);

    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }
}
