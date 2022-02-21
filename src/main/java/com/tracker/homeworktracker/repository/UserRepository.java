package com.tracker.homeworktracker.repository;

import com.tracker.homeworktracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Finding the username
    User findByUsername(@Param("username") String username);

    User findByUsernameAndToken(@Param("username") String username, @Param("password") String password);

    User findByEmail(@Param("email") String email);
}
