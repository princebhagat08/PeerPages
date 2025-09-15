package com.example.enotes_api.controller;

import com.example.enotes_api.dto.CategoryDto;
import com.example.enotes_api.dto.CategoryResponse;

import com.example.enotes_api.endpoint.CategoryEndpoint;

import com.example.enotes_api.dto.NotesResponse;

import com.example.enotes_api.service.CategoryService;
import com.example.enotes_api.service.NotesService;
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
public class CategoryController implements CategoryEndpoint {

    @Autowired
    private CategoryService categoryService;



    @Override
    public ResponseEntity<?> saveCategory(CategoryDto categoryDto){
        Boolean isSaved = categoryService.saveCategory(categoryDto);
        return isSaved ?
                CommonUtil.createBuildResponseMessage("Saved Successfully",HttpStatus.CREATED):
                CommonUtil.createErrorResponseMessage("Not Saved",HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    public ResponseEntity<?> getAllCategory(){
        List<CategoryResponse> allCategory = categoryService.getAllCategory();
        if(CollectionUtils.isEmpty(allCategory)){
            return CommonUtil.createErrorResponseMessage("No category found",HttpStatus.NO_CONTENT);
        }
        return CommonUtil.createBuildResponse(allCategory,HttpStatus.OK);
    }



    @Override
    public ResponseEntity<?> getActiveCategory(){
        List<CategoryResponse> allCategory = categoryService.getActiveCategory();
        if(CollectionUtils.isEmpty(allCategory)){
            return CommonUtil.createErrorResponseMessage("No category found",HttpStatus.NO_CONTENT);
        }
        return CommonUtil.createBuildResponse(allCategory,HttpStatus.OK);
    }


    @Override
    public ResponseEntity<?> getCategoryDetailsById( Integer id) throws Exception {
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        if(ObjectUtils.isEmpty(categoryDto)){
            return CommonUtil.createErrorResponseMessage("Internal server error",HttpStatus.NOT_FOUND);

        }
        return  CommonUtil.createBuildResponse(categoryDto,HttpStatus.OK);
    }


    @Override
    public ResponseEntity<?> deleteCategory( Integer id){
        Boolean isDeleted = categoryService.deleteCategory(id);
        if(isDeleted){
            return CommonUtil.createBuildResponseMessage("Category deleted successfully",HttpStatus.OK);
        }
        return  CommonUtil.createErrorResponseMessage("Category not deleted",HttpStatus.INTERNAL_SERVER_ERROR);
    }




}
