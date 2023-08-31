package com.kanban.authentication.security;

import com.kanban.authentication.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtTokenImpl implements JwtToken{

    /**
     * This method generator JSON web token (JWT) for give user
     *
     * @param user The User object
     * @return A Map containing JWT token and success message
     */
    @Override
    public Map<String, String> generateToken(User user) {

        final String SECRET_KEY = "secret-key";

        // Create a new map to hold the token and other information
        Map<String, String> tokenResponse = new HashMap<>();

        // User data for JWT claim
        Map<String, Object> userData = new HashMap<>();
        userData.put("email", user.getEmail());

        // Generate JWT token with user data
        String jwtToken = Jwts.builder()
                .setClaims(userData)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        tokenResponse.put("token", jwtToken);
        tokenResponse.put("message", "User login successful");

        return tokenResponse;
    }
}
