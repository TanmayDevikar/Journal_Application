package net.engineeringdigest.journalApp.filter;

import net.engineeringdigest.journalApp.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {  //It will filter one request one time

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected  void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");   //refer 'authorization' in header in Postman.
        String username = null;
        String jwt = null;

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {  //As the authorization in header in postman starts with 'Bearer'.
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        if(username != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);  //userDetails is from db.

            if(jwtUtil.validateToken(jwt, userDetails.getUsername())) { //validating using db's username and jwt token
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                //Created an object of auth. Provided user details and its roles.
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
                //set auth in Security Context.
            }
        }
        response.addHeader("admin", "Tanmay");  //This is just for example. We are setting admin: Tanmay in header in response and this response we are sending for further filter in the next line.
        //This header that we have set in response, will go with all the responses.
        chain.doFilter(request, response);  //With chain, we have sent this request and response for the further filters if any.
    }
}
