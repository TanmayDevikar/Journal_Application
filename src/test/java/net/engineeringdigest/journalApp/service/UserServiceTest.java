package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.services.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest  /* @SpringBootTest will start the Spring Application Context. Without this, we will not be able to Autowire anything.
Because beans get created when we start the application and Spring Application Context get started.
So, SpringBootTest will start the application for our test temporary until the test has been done (so that the bean and all get created properly) and then turn it off. */
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

//    @Test
//    public void testAdd() {
//        assertEquals(4, 2+2);
//    }

    @Disabled
    @Test
    public void testFindByUserName() {
        User user = userRepository.findByUserName("Sanik");
        assertNotNull(user);
        assertTrue(!user.getJournalEntries().isEmpty());
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,2,4",
            "3,4,89"
    })
    public void test(int a, int b, int expected) {
        assertEquals(expected, a+b);
    }


    @Disabled
    @ParameterizedTest
    @ValueSource(strings = {
            "Tanmay",
            "Sanik",
            "Abha"
    })
    public void testFindByUserNameWithParameters(String name) {
        assertNotNull(userRepository.findByUserName(name));
    }

    @Disabled
    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testSaveNewUser(User user) {
        assertTrue(userService.saveNewUser(user));
    }
}
