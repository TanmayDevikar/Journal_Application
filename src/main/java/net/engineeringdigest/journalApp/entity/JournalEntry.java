package net.engineeringdigest.journalApp.entity;

import lombok.*;
import net.engineeringdigest.journalApp.enums.Sentiment;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "journal_entries")    //it will map this class's entities with the db's 'journal_entries' collection
//Spring boot will create a collection named 'journal_entries' automatically

//@Getter   //--> Project Lombok features which generates Getters even without writing them
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
//@EqualsAndHashCode
//@Builder
@Data  //@Data gives the features of @Getter, @Setter, @RequiredArgsConstructors, @ToString, @EqualsAndHashCode, @Value
@NoArgsConstructor
public class JournalEntry {

    @Id        //it will set id as the primary key
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;
    private Sentiment sentiment;
}
