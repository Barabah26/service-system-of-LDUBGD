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
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(rollbackFor = Exception.class)
    public boolean  sendEmailAboutStatementStatus(Long idOfStatement){

        Statement statement;

        try {
            statement = statementRepository.findById(idOfStatement).orElseThrow(() -> new RuntimeException("Statement not found"));
        }  catch (Exception e) {
            System.err.println("Exception occurred while sending notification: " + e.getMessage());
            return false;
        }


        String texOfEmail;

        if(fileInfoRepository.existsById(statement.getId())){
            String urlOfFile=new StringBuilder(fileServiceUrl).append("/api/file/download?id=").append(cryptoTool.hashOf(idOfStatement)).toString();
            texOfEmail = generateNotificationMessageWithFile(statement,urlOfFile);
        }else {
            texOfEmail = generateNotificationMessage(statement);
        }


        try {
            sendEmail(statement.getUser().getEmail(), "Сповіщення про статус заявки", texOfEmail);
        } catch (Exception e) {
            System.err.println("Exception occurred while sending notification: " + e.getMessage());
            return false;
        }

        statement.getStatementInfo().setIsReady(true);

        statementRepository.save(statement);

        return true;
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

