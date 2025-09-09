package com.example.enotes_api.service.impl;

import com.example.enotes_api.dto.UserDto;
import com.example.enotes_api.entity.Role;
import com.example.enotes_api.entity.User;
import com.example.enotes_api.repository.RoleRepository;
import com.example.enotes_api.repository.UserRepository;
import com.example.enotes_api.service.UserService;
import com.example.enotes_api.utils.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

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

    @Override
    public boolean register(UserDto userDto) {

        validation.userValidation(userDto);

        User user = mapper.map(userDto, User.class);

        setRole(userDto,user);

        User save = userRepo.save(user);

        if(!ObjectUtils.isEmpty(save)){
            return  true;
        }

        return false;


    }

    private void setRole(UserDto userDto,User user) {
        List<Integer> reqRoleId = userDto.getRoles().stream().map(r->r.getId()).toList();
        List<Role> roles = roleRepo.findAllById(reqRoleId);
        user.setRoles(roles);
    }

}
