package br.com.treinaweb.spring_mail;

import java.util.Map;

import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final ResourceLoader resourceLoader;

    public void sendSimpleMail(String to, String subject, String body) {
        var message = new SimpleMailMessage();

        message.setTo(to);
        message.setFrom("no-reply@treinaweb.com.br");
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public void sendTemplatedMail(String to, String subject, String template, Map<String, Object> props) throws MessagingException {
        var mimeMessage = mailSender.createMimeMessage();
        var mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        var context = new Context();
        context.setVariables(props);

        var htmlContent = templateEngine.process(template, context);

        mimeMessageHelper.setFrom("no-reply@treinaweb.com.br");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(htmlContent, true);

        mailSender.send(mimeMessage);
    }
    
    public void sendTemplatedMail(String to, String subject, String template, Map<String, Object> props, String attachment) throws MessagingException {
        var mimeMessage = mailSender.createMimeMessage();
        var mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        var context = new Context();
        context.setVariables(props);

        var htmlContent = templateEngine.process(template, context);

        mimeMessageHelper.setFrom("no-reply@treinaweb.com.br");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(htmlContent, true);

        var resource = resourceLoader.getResource(attachment);
        mimeMessageHelper.addAttachment(resource.getFilename(), resource);

        mailSender.send(mimeMessage);
    }
    
}
