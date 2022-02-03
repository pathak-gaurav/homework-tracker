package com.tracker.homeworktracker.configuration;

import com.tracker.homeworktracker.entity.UserDetails;
import com.tracker.homeworktracker.repository.UserDetailsRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service("emailService")
@EnableScheduling
public class EmailConfig {

    private JavaMailSender mailSender;

    private UserDetailsRepository userDetailsRepository;

    public EmailConfig(JavaMailSender mailSender, UserDetailsRepository userDetailsRepository) {
        this.mailSender = mailSender;
        this.userDetailsRepository = userDetailsRepository;
    }

    @Scheduled(fixedDelay = 5000)
//    @Scheduled(cron = "0 0 0 ? * * *")
    public void scheduleFixedDelayTask() {
        List<UserDetails> assignmentPending = userDetailsRepository.findAssignmentPending();
        assignmentPending.stream().forEach(element -> {
            if(ChronoUnit.DAYS.between(LocalDate.now(),element.getAssignmentPendingDate())<3){
                System.out.println("Days: "+ ChronoUnit.DAYS.between(LocalDate.now(),element.getAssignmentPendingDate()));
                String toEmail = element.getUser().getEmail();
                String courseName = element.getCourseName();
                System.out.println("===>>> Email" +toEmail+"     Course: "+courseName);
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(toEmail);
                message.setSubject("Assignment Pending for "+courseName);
                message.setText("Assignment Pending for "+courseName +", Due date is "+element.getAssignmentPendingDate());
                mailSender.send(message);
            }
        });
    }
}
