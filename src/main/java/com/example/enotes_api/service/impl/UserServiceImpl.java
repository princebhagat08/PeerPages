package com.example.enotes_api.service.impl;

import com.example.enotes_api.dto.EmailRequest;
import com.example.enotes_api.dto.PasswordChangeRequest;
import com.example.enotes_api.dto.PasswordResetRequest;
import com.example.enotes_api.entity.User;
import com.example.enotes_api.exception.ResourceNotFoundException;
import com.example.enotes_api.exception.SuccessException;
import com.example.enotes_api.repository.UserRepository;
import com.example.enotes_api.service.EmailService;
import com.example.enotes_api.service.UserService;
import com.example.enotes_api.utils.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private EmailService emailService;

    @Override
    public void changePassword(PasswordChangeRequest changeRequest) {
        User user = CommonUtil.getLoggedInUser();

        if(!passwordEncoder.matches(changeRequest.getOldPassword(),user.getPassword())){
            throw new IllegalArgumentException("Your old password is incorrect !!");
        }

        String encodedPassword = passwordEncoder.encode(changeRequest.getNewPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user);

    }

    @Override
    public void sendPasswordResetEmail(String email, HttpServletRequest request) throws Exception{
        User user = userRepo.findByEmail(email);
        if(ObjectUtils.isEmpty(user)){
            throw new ResourceNotFoundException("Invalid email id");
        }

        // Generate unique password reset token
        String passwordResetToken = UUID.randomUUID().toString();
        user.getStatus().setPasswordResetToken(passwordResetToken);
        User updateUser = userRepo.save(user);

        String url = CommonUtil.getUrl(request);
        sendEmailRequest(updateUser,url);

    }


    private void sendEmailRequest(User user,String url) throws Exception {

        String message = "Hi <b>[[username]]</b> "
                +"<br><p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=[[url]]>Change my password</a></p>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p><br>"
                + "Thanks,<br>Enotes.com";

        message = message.replace("[[username]]", user.getFirstName());
        message = message.replace("[[url]]", url + "/api/v1/home/verify-reset-pswd?uid=" + user.getId() + "&&code="
                + user.getStatus().getPasswordResetToken());

        EmailRequest emailRequest = EmailRequest.builder().to(user.getEmail())
                .title("Password Reset").subject("Password Reset link").message(message).build();

        // send password reset email to user
        emailService.send(emailRequest);
    }


    @Override
    public void verifyPasswordResetLink(Integer uid, String code) throws Exception {
        User user = userRepo.findById(uid).orElseThrow(() -> new ResourceNotFoundException("Invalid user"));
        verifyPasswordResetCode(user.getStatus().getPasswordResetToken(),code);
    }


    private void verifyPasswordResetCode(String existingToken, String requestedToken) {

        if(StringUtils.hasText(requestedToken)){

            if(!StringUtils.hasText(existingToken)){
                throw new SuccessException("Password already reset");
            }

            if(!existingToken.equals(requestedToken)){
                throw new IllegalArgumentException("Invalid url");
            }


        }else {
            throw new IllegalArgumentException("Invalid token");
        }

    }

    @Override
    public void resetPassword(PasswordResetRequest resetRequest) throws Exception{
        User user = userRepo.findById(resetRequest.getUid()).orElseThrow(
                () -> new ResourceNotFoundException("Invalid User id"));

        String newPassword = passwordEncoder.encode(resetRequest.getNewPassword());
        user.setPassword(newPassword);
        user.getStatus().setPasswordResetToken(null);
        userRepo.save(user);

    }



}
