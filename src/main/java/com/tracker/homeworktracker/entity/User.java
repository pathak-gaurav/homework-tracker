package com.tracker.homeworktracker.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
@Getter
@Setter
public class User {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue
    private Long userId;
    private String firstName;
    private String lastName;
    @Column(unique=true)
    private String email;
    @Column(unique=true)
    private String username;
    private String token;

    public User(String firstName, String lastName, String email, String username, String token) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.token = token;
    }

}
