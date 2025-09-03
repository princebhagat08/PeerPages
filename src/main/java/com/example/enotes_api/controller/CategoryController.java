package com.example.enotes_api.controller;

import com.example.enotes_api.dto.CategoryDto;
import com.example.enotes_api.dto.CategoryResponse;
import com.example.enotes_api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
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



    @GetMapping("/active")
    public ResponseEntity<?> getAllCategory(){
        List<CategoryResponse> allCategory = categoryService.getActiveCategory();
        if(CollectionUtils.isEmpty(allCategory)){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(allCategory,HttpStatus.OK);
    }



    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id){
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        if(ObjectUtils.isEmpty(categoryDto)){
            return  new ResponseEntity<>("Category not found with id=" + id, HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id){
        Boolean isDeleted = categoryService.deleteCategory(id);
        if(isDeleted){
            return  new ResponseEntity<>("Category Deleted Successfully", HttpStatus.OK);
        }
        return  new ResponseEntity<>("Category not deleted",HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
