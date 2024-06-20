package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    void sendEmailTest() {
        emailService.sendEmail("tanmaydevikar99@gmail.com",
                "Testing Java mail sender",
                "Hey,\n\nHow you doing?\nThanks\n\nRegards,\nTanmay.");
    }
}
