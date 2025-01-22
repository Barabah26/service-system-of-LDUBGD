package com.ldubgd.emailService.services;

import com.ldubgd.components.dao.Statement;
import com.ldubgd.emailService.repositories.FileInfoRepository;
import com.ldubgd.emailService.repositories.StatementRepository;
import com.ldubgd.utils.CryptoTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Value("${fileService.url}")
    private String fileServiceUrl;

    @Autowired
    private CryptoTool cryptoTool;

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @Autowired
    private StatementRepository statementRepository;


    public void sendEmailAboutStatementStatus(String emailOfUser, Long idOfStatement){

        Statement statement = statementRepository.findById(idOfStatement).orElseThrow(() -> new RuntimeException("Statement not found"));

        String texOfEmail;

        if(fileInfoRepository.existsById(statement.getId())){
            String urlOfFile=new StringBuilder(fileServiceUrl).append("/api/file/download?id=").append(cryptoTool.hashOf(idOfStatement)).toString();
            texOfEmail = generateNotificationMessageWithFile(statement,urlOfFile);
        }else {
            texOfEmail = generateNotificationMessage(statement);
        }

        sendEmail(emailOfUser, "Сповіщення про статус заявки", texOfEmail);
    }


    private void sendEmail(String toEmail, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
    }



    private String generateNotificationMessage(Statement statement) {
        StringBuilder messageBuilder = new StringBuilder();

        messageBuilder.append("Ваша заявка: '")
                .append(statement.getTypeOfStatement())
                .append("' готова!\n\n")
                .append("Деталі заявки:\n")
                .append("- Факультет: ").append(statement.getFaculty()).append("\n")
                .append("- Група: ").append(statement.getGroup()).append("\n")
                .append("- Рік народження: ").append(statement.getYearBirthday()).append("\n")
                .append("- Контактний телефон: ").append(statement.getPhoneNumber()).append("\n\n");
        return messageBuilder.toString();
    }



    private String generateNotificationMessageWithFile(Statement statement,String url) {

        return new StringBuilder(generateNotificationMessage(statement))
                .append("Файл заявки: ")
                .append(url).toString();
    }
}

