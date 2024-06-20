package net.engineeringdigest.journalApp.Schedular;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.enums.Sentiment;
import net.engineeringdigest.journalApp.repository.UserRepositoryImpl;
import net.engineeringdigest.journalApp.services.EmailService;
import net.engineeringdigest.journalApp.services.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserSchedular {

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Autowired
    private EmailService emailService;



    //To run @Scheduled methods, we need to tell Spring Boot that we have some methods with @Scheduled annotations in the application, by putting @EnableScheduling in the main method.
    @Scheduled(cron = "0 0 9 * * SUN")       //@Scheduled will run this method automatically in the period that provided in 'cron=""' (in our case every Sunday 9 am).
    //The first field in cron is seconds, second field in minutes, third field is hours, fourth field is day of the month, fifth field is month, and the sixth field is Day of the week.
    public void fetchUsersAndSendSaMail() {
        List<User> users = userRepositoryImpl.getUsersForSA();
        for (User user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
            //In the above expression, we tried to get the sentiments from the journal entries of user for the last 7 days.
            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();   //Map to store the count of each sentiment.
            for (Sentiment sentiment : sentiments) {
                if(sentiment != null) {
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
                }
                Sentiment mostFrequentSentiment = null;
                int maxCount = 0;
                for(Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                    if(entry.getValue() > maxCount) {
                        maxCount = entry.getValue();
                        mostFrequentSentiment = entry.getKey();
                    }
                }
                //From the above logic, we are counting the number of times each sentiment has appeared for the user. And whichever sentiment is most frequent, we are sending that sentiment in the body of the mail.
                if(mostFrequentSentiment != null) {
                    emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days", mostFrequentSentiment.toString());
                }
            }



        }
        

    }
}
