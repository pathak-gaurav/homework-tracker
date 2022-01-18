package com.tracker.homeworktracker;

import com.tracker.homeworktracker.entity.User;
import com.tracker.homeworktracker.entity.UserDetails;
import com.tracker.homeworktracker.repository.UserDetailsRepository;
import com.tracker.homeworktracker.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
        User userDrake = new User("Drake", "Bell", "drake.bell@gmail.com",
                "drake.bell", "qwerty@12345");
        User userJohn = new User("John", "Ross", "john.ross@gmail.com",
                "john.ross", "qwerty@12345");
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
}
