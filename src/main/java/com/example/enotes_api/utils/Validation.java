package com.example.enotes_api.utils;

import com.example.enotes_api.dto.CategoryDto;
import com.example.enotes_api.dto.TodoDto;
import com.example.enotes_api.dto.UserRequest;
import com.example.enotes_api.enums.TodoStatus;
import com.example.enotes_api.exception.ExistDataException;
import com.example.enotes_api.exception.ResourceNotFoundException;
import com.example.enotes_api.exception.ValidationException;
import com.example.enotes_api.repository.RoleRepository;
import com.example.enotes_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class Validation {

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private UserRepository userRepo;

    public void categoryValidation(CategoryDto categoryDto) {

        Map<String, Object> error = new LinkedHashMap<>();

        if (ObjectUtils.isEmpty(categoryDto)) {
            throw new IllegalArgumentException("Category object shouldn't be null or empty");
        } else {

            // Validating name field
            if (ObjectUtils.isEmpty(categoryDto.getName())) {
                error.put("name", "name field cannot be null or empty");
            } else {
                if (categoryDto.getName().length() < 3) {
                    error.put("name", "name length min 3");
                }
                if (categoryDto.getName().length() > 100) {
                    error.put("name", "name length max 100");
                }
            }


            // Validation description
            if (ObjectUtils.isEmpty(categoryDto.getDescription())) {
                error.put("description", "description field cannot be null or empty");
            }

            // Validation isActive
            if (ObjectUtils.isEmpty(categoryDto.getIsActive())) {
                error.put("isActive", "isActive field cannot be null or empty");
            } else {
                if (categoryDto.getIsActive() != true && categoryDto.getIsActive() != false) {
                    error.put("isActive","Invalid isActive field value");
                }
            }


        }

        if(!error.isEmpty()){
            throw new ValidationException(error);
        }

    }


    public void todoStatusValidation(TodoDto todoDto) throws Exception {
        TodoDto.StatusDto statusDto = todoDto.getStatus();

        Boolean statusFound = false;

        for(TodoStatus st:TodoStatus.values()){
            if(statusDto.getId().equals(st.getId())){
                statusFound = true;
            }
        }

        if(!statusFound){
            throw new ResourceNotFoundException("Invalid status");
        }
    }


    public void userValidation(UserRequest userRequest){

        if(!StringUtils.hasText(userRequest.getFirstName())){
            throw new IllegalArgumentException("firstName is invalid");
        }

        if(!StringUtils.hasText(userRequest.getLastName())){
            throw new IllegalArgumentException("lastName is invalid");
        }

        if(!StringUtils.hasText(userRequest.getEmail()) ||
                !userRequest.getEmail().matches(Constants.EMAIL_REGEX)){
            throw new IllegalArgumentException("email is invalid");
        }else{
            Boolean exists = userRepo.existsByEmail(userRequest.getEmail());
            if(exists){
                throw new ExistDataException("Email already exist");
            }
        }


        if(!StringUtils.hasText(userRequest.getMobNo()) ||
                !userRequest.getMobNo().matches(Constants.MOBNO_REGEX)){
            throw new IllegalArgumentException("mobile number is invalid");
        }

        if(CollectionUtils.isEmpty(userRequest.getRoles())){
            throw new IllegalArgumentException("role is invalid");
        }else {
            List<Integer> rolesId = roleRepo.findAll().stream().map((role)-> role.getId()).toList();

            List<Integer> invalidReqRoleIds = userRequest.getRoles().stream()
                    .map((roleDto)-> roleDto.getId())
                    .filter(id->!rolesId.contains(id)).toList();

            if(!CollectionUtils.isEmpty(invalidReqRoleIds)){
                throw new IllegalArgumentException("role is invalid" + invalidReqRoleIds);
            }

        }


    }


}
