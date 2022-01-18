package com.tracker.homeworktracker.repository;

import com.tracker.homeworktracker.entity.User;
import com.tracker.homeworktracker.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

    //find empty course names
    @Query("select ud from UserDetails ud where ud.courseName=:course_name and ud.user is NULL ")
    UserDetails findByCourseName(@Param("course_name") String courseName);

    @Query("select ud from UserDetails ud where ud.user=:user_id")
    List<UserDetails> findByUser(@Param("user_id") User userId);

    @Query("select ud from UserDetails ud where ud.user is NOT NULL and ud.assignmentPendingDate is not null")
    List<UserDetails> findAssignmentPending();

}
