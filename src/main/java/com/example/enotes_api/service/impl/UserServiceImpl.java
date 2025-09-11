package com.example.enotes_api.service.impl;

import com.example.enotes_api.dto.EmailRequest;
import com.example.enotes_api.dto.UserDto;
import com.example.enotes_api.entity.AccountStatus;
import com.example.enotes_api.entity.Role;
import com.example.enotes_api.entity.User;
import com.example.enotes_api.repository.RoleRepository;
import com.example.enotes_api.repository.UserRepository;
import com.example.enotes_api.service.EmailService;
import com.example.enotes_api.service.UserService;
import com.example.enotes_api.utils.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private Validation validation;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private EmailService emailService;

    @Override
    public boolean register(UserDto userDto,String url) throws Exception {

        validation.userValidation(userDto);

        User user = mapper.map(userDto, User.class);

        setRole(userDto,user);

        AccountStatus status = AccountStatus.builder()
                .isActive(false)
                .verificationCode(UUID.randomUUID().toString())
                .build();
        user.setStatus(status);
        User save = userRepo.save(user);

        if(!ObjectUtils.isEmpty(save)){
            emailSend(save,url);
            return  true;
        }

        return false;


    }

    private void emailSend(User user, String url) throws Exception{

        String message="Hi,</b>[[username]]<br> Your account register successfully <br>"
                +"<br> Click the below link and verify your account <br>"
                +"<a href='[[url]]'>Click Here</a> <br><br>"
                +"Thanks,<br> Notes.com";

        message = message.replace("[[username]]",user.getFirstName());
        message = message.replace("[[url]]",url+"/api/v1/home/verify?uid="+user.getId()+"&&code="+user.getStatus().getVerificationCode());


        EmailRequest emailRequest = EmailRequest.builder()
                .to(user.getEmail())
                .title("Email Confirmation")
                .subject("Account created successfully")
                .message(message)
                .build();

        emailService.send(emailRequest);
    }

    private void setRole(UserDto userDto,User user) {
        List<Integer> reqRoleId = userDto.getRoles().stream().map(r->r.getId()).toList();
        List<Role> roles = roleRepo.findAllById(reqRoleId);
        user.setRoles(roles);
    }

}
