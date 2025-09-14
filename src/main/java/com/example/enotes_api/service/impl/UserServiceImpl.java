package com.example.enotes_api.service.impl;

import com.example.enotes_api.dto.PasswordChangeRequest;
import com.example.enotes_api.entity.User;
import com.example.enotes_api.repository.UserRepository;
import com.example.enotes_api.service.UserService;
import com.example.enotes_api.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository userRepo;

    @Override
    public void changePassword(PasswordChangeRequest changeRequest) {
        User user = CommonUtil.getLoggedInUser();

        if(!encoder.matches(changeRequest.getOldPassword(),user.getPassword())){
            throw new IllegalArgumentException("Your old password is incorrect !!");
        }

        String encodedPassword = encoder.encode(changeRequest.getNewPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user);

    }
}
