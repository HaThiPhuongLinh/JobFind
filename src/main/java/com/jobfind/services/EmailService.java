package com.jobfind.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtpEmail(String to, String otp) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject("Xác thực email - Mã OTP");
        helper.setText(
            "<h3>Xác thực email của bạn</h3>" +
            "<p>Mã OTP của bạn là: <strong>" + otp + "</strong></p>" +
            "<p>Mã này có hiệu lực trong 10 phút.</p>" +
            "<p>Vui lòng không chia sẻ mã này với bất kỳ ai.</p>",
            true
        );

        mailSender.send(message);
    }
}