package com.ldubgd.emailService.services;

import com.ldubgd.components.dao.ForgotPassword;
import com.ldubgd.components.dao.Statement;
import com.ldubgd.emailService.repositories.FileInfoRepository;
import com.ldubgd.emailService.repositories.ForgotPasswordRepository;
import com.ldubgd.emailService.repositories.StatementRepository;
import com.ldubgd.utils.CryptoTool;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Autowired
    private CryptoTool cryptoTool;

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @Autowired
    private StatementRepository statementRepository;

    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;

    /**
     * Метод для надсилання HTML-листа.
     */
    private void sendHtmlEmail(String toEmail, String subject, String htmlBody) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setFrom(emailFrom);
        helper.setTo(toEmail);
        helper.setSubject(subject);
        // true означає, що вміст повідомлення є HTML
        helper.setText(htmlBody, true);
        mailSender.send(mimeMessage);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean sendEmailAboutStatementStatus(Long idOfStatement) {

        Statement statement;
        try {
            statement = statementRepository.findById(idOfStatement)
                    .orElseThrow(() -> new RuntimeException("Statement not found"));
        } catch (Exception e) {
            System.err.println("Exception occurred while fetching statement: " + e.getMessage());
            return false;
        }

        String htmlContent;
        if (fileInfoRepository.existsByStatementId(statement.getId())) {
            String urlOfFile = new StringBuilder("dovidka.ldubgd.edu.ua/api/file/download?id=")
                    .append(cryptoTool.hashOf(idOfStatement))
                    .toString();
            htmlContent = generateHtmlMessageWithFile(statement, urlOfFile);
        } else {
            htmlContent = generateHtmlMessageForStatement(statement);
        }

        try {
            sendHtmlEmail(statement.getUser().getEmail(), "Сповіщення про статус заявки", htmlContent);
        } catch (MessagingException e) {
            System.err.println("Exception occurred while sending email: " + e.getMessage());
            return false;
        }

        statement.getStatementInfo().setIsReady(true);
        statementRepository.save(statement);

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean sendEmailAboutForgotPasswordStatus(Long idOfForgotPassword) {
        ForgotPassword forgotPassword;
        try {
            forgotPassword = forgotPasswordRepository.findById(idOfForgotPassword)
                    .orElseThrow(() -> new RuntimeException("ForgotPassword request not found"));
        } catch (Exception e) {
            System.err.println("Exception occurred while fetching forgot password request: " + e.getMessage());
            return false;
        }

        String htmlContent = generateHtmlMessageForForgotPassword(forgotPassword);

        try {
            sendHtmlEmail(forgotPassword.getUser().getEmail(), "Сповіщення про зміну паролю", htmlContent);
        } catch (MessagingException e) {
            System.err.println("Exception occurred while sending email: " + e.getMessage());
            return false;
        }

        forgotPassword.getForgotPasswordInfo().setIsReady(true);
        forgotPasswordRepository.save(forgotPassword);
        return true;
    }

    /**
     * Генерує HTML повідомлення для заявки без посилання на файл.
     */
    private String generateHtmlMessageForStatement(Statement statement) {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html><body>")
                .append("<p>Ваша заявка: '")
                .append(statement.getTypeOfStatement())
                .append("' готова!</p>")
                .append("<p>Деталі заявки:<br>")
                .append("- Факультет: ").append(statement.getFaculty()).append("<br>")
                .append("- Група: ").append(statement.getGroup()).append("<br>")
                .append("- Рік народження: ").append(statement.getYearBirthday()).append("<br>")
                .append("- Контактний телефон: ").append(statement.getPhoneNumber())
                .append("</p>")
                .append("</body></html>");
        return htmlBuilder.toString();
    }

    /**
     * Генерує HTML повідомлення для заявки з посиланням на файл.
     */
    private String generateHtmlMessageWithFile(Statement statement, String url) {
        // Генеруємо базове повідомлення
        String baseHtml = generateHtmlMessageForStatement(statement);
        // Вставляємо посилання для скачування файлу перед закриваючим тегом </body>
        int closingBodyIndex = baseHtml.lastIndexOf("</body>");
        String fileLinkHtml = "<p>Файл заявки: <a href=\"" + url + "\" download>Скачати файл</a></p>";
        if (closingBodyIndex != -1) {
            return baseHtml.substring(0, closingBodyIndex) + fileLinkHtml + baseHtml.substring(closingBodyIndex);
        } else {
            // Якщо з якоїсь причини не знайдено </body>, просто додаємо посилання в кінець
            return baseHtml + fileLinkHtml;
        }
    }

    /**
     * Генерує HTML повідомлення для заявки на відновлення паролю.
     */
    private String generateHtmlMessageForForgotPassword(ForgotPassword forgotPassword) {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html><body>")
                .append("<p>Ваша заявка: '")
                .append(forgotPassword.getTypeOfForgotPassword())
                .append("' готова!</p>")
                .append("<p>Деталі заявки:<br>")
                .append("- Логін: ").append(forgotPassword.getLogin()).append("<br>")
                .append("- Пароль: ").append(forgotPassword.getPassword())
                .append("</p>")
                .append("</body></html>");
        return htmlBuilder.toString();
    }
}
