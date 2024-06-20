package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.services.UserDetailServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

//The main aim of using Mockito is that we don't want to start the whole application context and just want to use the stuffs related to this test.

//@SpringBootTest  --> we cannot use this here as it will start the complete spring boot application context, and we don't want that.
public class UserDetailsServiceImplTest {

    @InjectMocks  //This will inject mock in userDetailService. Coz actually it is null as application context is not starting
    private UserDetailServiceImpl userDetailService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);  //initialize mocks in this class and inject them as well.
    }
    //'this' is referring to this class here

    @Test
    @Disabled
    void loadUserByUserNameTest() {
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("ram").password("ram").roles(new ArrayList<>()).build());
        //here we are saying that whenever I ask for a user from the db, given me a dummy user with username and password as 'ram'.
        UserDetails user = userDetailService.loadUserByUsername("ram");
        Assertions.assertNotNull(user);
    }
}
