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
@RequestMapping("api/email")
@AllArgsConstructor
@RestController
public class EmailController {
    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    private CryptoTool cryptoTool;

    @PatchMapping("/send")
    public ResponseEntity<?> notificationAboutStatementStatus(
            @RequestParam("id") String hashIdOfStatement, @RequestParam("email") String emailOfUser){


        emailSenderService.sendEmailAboutStatementStatus(emailOfUser, cryptoTool.idOf(hashIdOfStatement));


        return ResponseEntity.ok().body("Email send");
    }

}
