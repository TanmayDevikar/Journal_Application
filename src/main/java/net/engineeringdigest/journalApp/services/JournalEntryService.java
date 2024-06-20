package net.engineeringdigest.journalApp.services;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {
        try{
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            //user.setUserName(null);   //-->An error to test @Transactional with Mongodb Atlas. When these error occur, all the above lines execution will get roll back.
            userService.saveUser(user);
        } catch (Exception e){
            log.error("Exception ", e);
            throw new RuntimeException("An error occurred while saving journal entry", e);
        }
    }

    public void saveEntry(JournalEntry journalEntry) {
        try{
            journalEntryRepository.save(journalEntry);
        } catch (Exception e){
            log.error("Exception ", e);
        }
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getById(ObjectId id) {  //Optional means it will return an object of type JournalEntry if the entry is found with the provided id, and if not it will return nothing and act like void return type
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName) {
        boolean removed = false;
        try {
            User user = userService.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));//this will run a loop inside 'removeIf' to check every id and remove the id which will equals the id coming from the function.
            if(removed) {
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
        } catch (Exception e){
            log.error("Error", e);
            throw new RuntimeException("An error occurred while deleting the entry ", e);
        }

        return removed;
    }
}
