package com.tracker.homeworktracker.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USER_DETAILS")
@Getter
@Setter
public class UserDetails {

    @Id
    @Column(name = "USER_DETAILS_ID")
    @GeneratedValue
    private Long userDetailsId;

    @Column(name = "COURSE_NAME")
    private String courseName;

    @Column(name = "ASSIGNMENT_PENDING_DATE")
    private LocalDate assignmentPendingDate;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH,
            CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    public UserDetails(String courseName) {
        this.courseName = courseName;
    }
}
