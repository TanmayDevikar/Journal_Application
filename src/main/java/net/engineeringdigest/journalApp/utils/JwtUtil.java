package net.engineeringdigest.journalApp.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private String SECRET_KEY = "TaK+HaV^uvCHEFsEVfypW#7g9^k*Z8$V";  //secret key should be anything random, but greater than 32 bytes.

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public String extractClaims(String token, String claim) {
        return extractAllClaims(token).get(claim, String.class);
    }

    public Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
        //token is expired if the expiration date (i.e., extractExpiration(token)) is before the today's date.
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        //claims we send in payload. If we need to send some extra information, we send it in claims. For e.g., if we need to send user email as well with username, we'll send it in claims.
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder()
                .claims(claims)
                .subject(subject) //subject is the identifier. In our case, it is username, which is unique for all users and through which we can identify the user.
                .header().empty().add("typ", "JWT")
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))  //5 minutes
                .signWith(getSigningKey())
                .compact();
    }

    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));

        //username is coming from db and extractedUsername is from token. If both usernames are same and the token is not expired, then the token is validated.
    }

}
