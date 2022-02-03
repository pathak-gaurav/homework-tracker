package com.tracker.homeworktracker.controller;

import com.tracker.homeworktracker.AssignmentPending;
import com.tracker.homeworktracker.entity.User;
import com.tracker.homeworktracker.entity.UserDetails;
import com.tracker.homeworktracker.repository.UserDetailsRepository;
import com.tracker.homeworktracker.repository.UserRepository;
import com.tracker.homeworktracker.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class UserController {

    private UserRepository userRepository;
    private UserDetailsRepository userDetailsRepository;
    private UserService userService;

    public UserController(UserRepository userRepository, UserDetailsRepository userDetailsRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.userService = userService;
    }

    @GetMapping("/find-user")
    public @ResponseBody
    ResponseEntity<Object> findUser(@RequestParam("username") String username) {
        User userByUsername = userRepository.findByUsername(username);
        if (userByUsername == null) {
            return new ResponseEntity<>("User Not Found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userByUsername, HttpStatus.OK);
    }

    @GetMapping("/login")
    public @ResponseBody
    ResponseEntity<Object> login(@RequestParam("username") String username,
                                 @RequestParam("password") String password) {
        User loginUser = userRepository.findByUsernameAndToken(username, password);
        if (loginUser != null) {
            return new ResponseEntity<>(loginUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Username and Password Invalid", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/find-user-courses")
    public @ResponseBody
    ResponseEntity<Object> findUserCourses(@RequestParam("username") String username) {
        User userByUsername = userRepository.findByUsername(username);
        if (userByUsername == null) {
            return new ResponseEntity<>("User Not Found", HttpStatus.BAD_REQUEST);
        }
        List<AssignmentPending> assignmentPendingList = userService.findUserCourses(userByUsername);
        return new ResponseEntity<>(assignmentPendingList, HttpStatus.OK);
    }


    @PostMapping("/user")
    public @ResponseBody
    ResponseEntity<Object> createUser(@RequestBody User user) {
        if (userInputDataCheck(user))
            return new ResponseEntity<>("Email, Username, Password are required", HttpStatus.BAD_REQUEST);
        userRepository.save(user);
        return new ResponseEntity<>("User Added Successfully", HttpStatus.OK);
    }

    private boolean userInputDataCheck(@RequestBody User user) {
        if (user.getEmail() == null || user.getUsername() == null || user.getToken() == null) {
            return true;
        }
        return false;
    }

    @PutMapping("/user")
    public @ResponseBody
    ResponseEntity<Object> updateUser(@RequestBody User user) {
        User tempUser = userRepository.findByUsername(user.getUsername());
        tempUser.setEmail(user.getEmail());
        tempUser.setFirstName(user.getFirstName());
        tempUser.setLastName(user.getLastName());
        tempUser.setToken(user.getToken());
        tempUser.setUsername(user.getUsername());
        userRepository.save(tempUser);
        return new ResponseEntity<>("User Modified Successfully", HttpStatus.OK);
    }

    @PostMapping("/user-courses")
    public @ResponseBody
    ResponseEntity<Object> addUserCourses(@RequestBody UserDetails userDetails,
                                          @RequestParam("username") String username) {
        if (userDetails.getCourseName() == null || userDetails.getAssignmentPendingDate() == null) {
            return new ResponseEntity<>("Course Name and Date Cannot be Empty", HttpStatus.BAD_REQUEST);
        }
        User userByUsername = userRepository.findByUsername(username);
        List<AssignmentPending> assignmentPendingList = userService.findUserCourses(userByUsername);
        boolean present = assignmentPendingList.stream().filter(element -> element.getCourseName().equalsIgnoreCase(userDetails.getCourseName())).findAny().isPresent();
        if(present){
            return new ResponseEntity<>("Course Name already exist", HttpStatus.BAD_REQUEST);
        }
        userDetails.setUser(userByUsername);
        userDetailsRepository.save(userDetails);
        return new ResponseEntity<>("User Courses Added Successfully", HttpStatus.OK);
    }

    @DeleteMapping("/user-courses")
    public @ResponseBody
    ResponseEntity<Object> deleteUserCourse(@RequestParam("courseName") String courseName,
                                            @RequestParam("username") String username) {
        User userByUsername = userRepository.findByUsername(username);
        List<UserDetails> userDetailsList = userDetailsRepository.findByUser(userByUsername);
        UserDetails userDetails = userDetailsList.stream().filter(el -> el.getCourseName().equalsIgnoreCase(courseName)).findFirst().orElse(null);
        if (userDetails == null) {
            return new ResponseEntity<>("Course Not Present", HttpStatus.BAD_REQUEST);
        }
        userDetails.setUser(null);
        userDetailsRepository.delete(userDetails);
        return new ResponseEntity<>("User Courses Deleted Successfully", HttpStatus.OK);
    }
}
