package net.engineeringdigest.journalApp.services;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //private static final Logger logger = LoggerFactory.getLogger(UserService.class); //Here, whichever class we'll mention, the same class will be named in the logs as the reference from which we can identify that these logs are from this specific class.
    //As we have used @Slf4j, it will inject the logging, and we can use 'log' directly instead of 'logger'


    public Boolean saveNewUser(User user) {
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(List.of("USER"));
            userRepository.save(user);
            return true;
        } catch(Exception e){
            log.trace("This is TRACE log");
            log.debug("This is DEBUG log");
            log.info("This is INFO log");
            log.warn("This is WARN log");
            //log.error("ERROR occurred for {}: ", user.getUserName(), e);  //placeholder --> value of user.getUserName() will get placed in place of {}
            log.error("This is ERROR log");
            return false;
        }
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER", "ADMIN"));
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> getById(ObjectId id) {  //Optional means it will return an object of type JournalEntry if the entry is found with the provided id, and if not it will return nothing and act like void return type
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
