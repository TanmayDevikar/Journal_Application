package net.engineeringdigest.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.services.UserDetailServiceImpl;
import net.engineeringdigest.journalApp.services.UserService;
import net.engineeringdigest.journalApp.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private AuthenticationManager authenticationManager;    //We need to create its bean in SpringSecurity.java to access it.

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "Ok";
    }

    @PostMapping("/signup")
    public void createUser(@RequestBody User user) {
        userService.saveNewUser(user);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            //The above line will internally call UserDetailServiceImpl and the BCryptPasswordEncoder, defined in Spring Security. With the help of those two, it will verify the username and the encoded password.
            UserDetails userDetails = userDetailService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch(Exception e) {
            log.error("Exception occurred while creteAuthenticationToken", e);
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);

            //This jwt token will get stored in local storage of a client. Logout we can do from the client side, we need to remove that token from client local storage.
            //JWT is stateless, .i.e, it has no storage. That's why we can't store the token on the server side.

            //Jugaad - we can store the token in redis and blocklist it when expires.
        }
    }
}
