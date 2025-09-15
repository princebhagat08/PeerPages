package com.example.enotes_api.endpoint;

import com.example.enotes_api.dto.PasswordResetRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/home")
public interface HomeEndpoint {

    @GetMapping("/verify")
    ResponseEntity<?> verifyUserAccount(@RequestParam Integer uid, @RequestParam String code)throws Exception;

    @GetMapping("/send-reset-email")
    ResponseEntity<?> sendResendEmail(@RequestParam String email, HttpServletRequest request) throws  Exception;


    @GetMapping("/verify-reset-pswd")
    ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid, @RequestParam String code) throws Exception;


    @PostMapping("/reset-pswd")
    ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest resetRequest) throws Exception;

}
