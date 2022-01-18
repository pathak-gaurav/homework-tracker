package com.tracker.homeworktracker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AssignmentPending {

    private String courseName;
    private LocalDate assignmentPendingDate;
}
