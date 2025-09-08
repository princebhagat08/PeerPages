package com.example.enotes_api.utils;

import com.example.enotes_api.dto.CategoryDto;
import com.example.enotes_api.dto.TodoDto;
import com.example.enotes_api.enums.TodoStatus;
import com.example.enotes_api.exception.ResourceNotFoundException;
import com.example.enotes_api.exception.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class Validation {

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

}
