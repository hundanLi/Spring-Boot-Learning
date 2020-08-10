package com.tcl.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author li
 * @version 1.0
 * @date 2020/8/7 17:49
 */
@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @GetMapping("/send/{to}/{msg}")
    public String sendMail(@PathVariable String to, @PathVariable String msg) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setSubject("Spring Boot Mail Test");
        message.setSentDate(new Date());
        message.setText(msg);
        message.setTo(to);

        mailSender.send(message);

        return "Send successfully.";
    }

}
