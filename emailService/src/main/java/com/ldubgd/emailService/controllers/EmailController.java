package com.ldubgd.emailService.controllers;

import com.ldubgd.emailService.services.EmailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/email")
@AllArgsConstructor
@RestController
public class EmailController {
    @Autowired
    EmailSenderService emailSenderService;

    @PatchMapping("/send/status")
    public ResponseEntity<?> notificationAboutStatementStatus(
            @RequestParam("id") String idOfStatement, @RequestParam("email") String emailOfUser){




        emailSenderService.sendSimpleEmail(
                "serhiikmyta@gmail.com",
                "your id: "+idOfStatement,
                "email "+emailOfUser);


        return ResponseEntity.ok().body("Email send");
    }

}
