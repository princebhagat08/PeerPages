package com.example.enotes_api.service;

import com.example.enotes_api.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    public String generateToken(User user);

    String extractUserName(String token);

    Boolean validateToken(String token, UserDetails userDetails);

}
