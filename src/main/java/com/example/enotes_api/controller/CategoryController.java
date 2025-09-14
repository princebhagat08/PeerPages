package com.example.enotes_api.controller;

import com.example.enotes_api.dto.CategoryDto;
import com.example.enotes_api.dto.CategoryResponse;
import com.example.enotes_api.service.CategoryService;
import com.example.enotes_api.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;



    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto){
        Boolean isSaved = categoryService.saveCategory(categoryDto);
        return isSaved ?
                CommonUtil.createBuildResponseMessage("Saved Successfully",HttpStatus.CREATED):
                CommonUtil.createErrorResponseMessage("Not Saved",HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllCategory(){
        List<CategoryResponse> allCategory = categoryService.getAllCategory();
        if(CollectionUtils.isEmpty(allCategory)){
            return CommonUtil.createErrorResponseMessage("No category found",HttpStatus.NO_CONTENT);
        }
        return CommonUtil.createBuildResponse(allCategory,HttpStatus.OK);
    }



    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> getActiveCategory(){
        List<CategoryResponse> allCategory = categoryService.getActiveCategory();
        if(CollectionUtils.isEmpty(allCategory)){
            return CommonUtil.createErrorResponseMessage("No category found",HttpStatus.NO_CONTENT);
        }
        return CommonUtil.createBuildResponse(allCategory,HttpStatus.OK);
    }



    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id) throws Exception {
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        if(ObjectUtils.isEmpty(categoryDto)){
            return CommonUtil.createErrorResponseMessage("Internal server error",HttpStatus.NOT_FOUND);

        }
        return  CommonUtil.createBuildResponse(categoryDto,HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id){
        Boolean isDeleted = categoryService.deleteCategory(id);
        if(isDeleted){
            return CommonUtil.createBuildResponseMessage("Category deleted successfully",HttpStatus.OK);
        }
        return  CommonUtil.createErrorResponseMessage("Category not deleted",HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
