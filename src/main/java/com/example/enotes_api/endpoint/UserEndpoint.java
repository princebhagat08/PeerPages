package com.example.enotes_api.endpoint;

import com.example.enotes_api.dto.PasswordChangeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/user")
public interface UserEndpoint {

    @GetMapping("/profile")
    ResponseEntity<?> getProfile();

    @PostMapping("/chng-pswd")
    ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest changeRequest);

}
