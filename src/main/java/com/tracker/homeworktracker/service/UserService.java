package com.tracker.homeworktracker.service;

import com.tracker.homeworktracker.AssignmentPending;
import com.tracker.homeworktracker.entity.User;
import com.tracker.homeworktracker.entity.UserDetails;
import com.tracker.homeworktracker.repository.UserDetailsRepository;
import com.tracker.homeworktracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private UserDetailsRepository userDetailsRepository;

    public UserService(UserRepository userRepository, UserDetailsRepository userDetailsRepository) {
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
    }


    public List<AssignmentPending> findUserCourses(User userByUsername) {
        List<UserDetails> userDetailsList = userDetailsRepository.findByUser(userByUsername);
        List<AssignmentPending> assignmentPendingList = new ArrayList<>();
        userDetailsList.forEach(element -> {
            AssignmentPending assignmentPending = new AssignmentPending();
            assignmentPending.setCourseName(element.getCourseName());
            assignmentPending.setAssignmentPendingDate(element.getAssignmentPendingDate());
            assignmentPendingList.add(assignmentPending);
        });
        return assignmentPendingList;
    }
}
