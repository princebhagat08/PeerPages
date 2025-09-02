package com.example.enotes_api.controller;

import com.example.enotes_api.dto.CategoryDto;
import com.example.enotes_api.dto.CategoryResponse;
import com.example.enotes_api.entity.Category;
import com.example.enotes_api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;



    @PostMapping("/save-category")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto){
        Boolean isSaved = categoryService.saveCategory(categoryDto);
        return isSaved ? new ResponseEntity<>("Saved", HttpStatus.CREATED) :
                            new ResponseEntity<>("Not Save",HttpStatus.INTERNAL_SERVER_ERROR);
    }



    @GetMapping("/category")
    public ResponseEntity<?> getAllCategory(){
        List<CategoryResponse> allCategory = categoryService.getActiveCategory();

        if(CollectionUtils.isEmpty(allCategory)){
            return ResponseEntity.noContent().build();
        }

        return new ResponseEntity<>(allCategory,HttpStatus.OK);
    }

}
