package net.engineeringdigest.journalApp.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.*;

@Document(collection = "user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private ObjectId id;
    @Indexed(unique = true)  //this will keep the userNames unique
    //Spring Boot will not do this indexing on its own. We need to do it manually by adding a command (spring.data.mongodb.auto-index-creation=true) in applications.properties!
    @NonNull  //lombok annotation to keep the field userName as Not Null. It will throw NullPointerException if the field found Null
    private String userName;
    @NonNull
    private String password;
    @DBRef   //We have used this to link a user collection to journal_entries collection (Just like a primary key and foreign key)
    //We are creating a reference of journal_entries in the user collection. This means this list named 'journalEntries' in the user will keep the reference (ObjectId) of journal entries present in journal_entries.
    private List<JournalEntry> journalEntries = new ArrayList<>();   //a list for a user which will contains the ids of the journal of that particular user.
    private List<String> roles;

    private String email;
    private boolean sentimentAnalysis;
    //Whoever will opt in for the sentimentAnalysis, we will send them the email at the end of every week.
}
