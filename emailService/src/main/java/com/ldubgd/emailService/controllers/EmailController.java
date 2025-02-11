package com.ldubgd.emailService.controllers;

import com.ldubgd.emailService.services.EmailSenderService;
import com.ldubgd.utils.CryptoTool;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/email/send")
@AllArgsConstructor
@RestController
public class EmailController {
    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    private CryptoTool cryptoTool;

    @PatchMapping("/statement")
    public ResponseEntity<?> notificationAboutStatementStatus(
            @RequestParam("id") String hashIdOfStatement){

        boolean status=emailSenderService.sendEmailAboutStatementStatus(cryptoTool.idOf(hashIdOfStatement));

        if(!status){
            return ResponseEntity.badRequest().body("Error while sending email");
        }

        return ResponseEntity.ok().body("Email sent successfully");
    }

    @PatchMapping("/forgot-password")
    public ResponseEntity<?> notificationAboutForgotPasswordStatus(
            @RequestParam("id") String hashIdOfStatement){

        boolean status=emailSenderService.sendEmailAboutForgotPasswordStatus(cryptoTool.idOf(hashIdOfStatement));

        if(!status){
            return ResponseEntity.badRequest().body("Error while sending email");
        }

        return ResponseEntity.ok().body("Email sent successfully");
    }

}
