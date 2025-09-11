package com.example.enotes_api.service.impl;

import com.example.enotes_api.entity.AccountStatus;
import com.example.enotes_api.entity.User;
import com.example.enotes_api.exception.ResourceNotFoundException;
import com.example.enotes_api.exception.SuccessException;
import com.example.enotes_api.repository.UserRepository;
import com.example.enotes_api.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceImpl implements HomeService {


    @Autowired
    private UserRepository userRepo;

    @Override
    public Boolean verifyAccount(Integer userId, String verificationCode) throws Exception{
        User user = userRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("Invalid user"));


        if(user.getStatus().getVerificationCode() == null){
            throw  new SuccessException("Account Already Verified");
        }


        if(user.getStatus().getVerificationCode().equals(verificationCode)){
            AccountStatus status = user.getStatus();
            status.setIsActive(true);
            status.setVerificationCode(null);

            userRepo.save(user);
            return true;
        }

        return false;

    }
}
