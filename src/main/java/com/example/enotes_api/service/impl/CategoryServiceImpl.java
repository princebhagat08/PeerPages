package com.example.enotes_api.service.impl;

import com.example.enotes_api.entity.Category;

import com.example.enotes_api.repository.CategoryRepository;
import com.example.enotes_api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Boolean saveCategory(Category category) {
       category.setIsDeleted(true);
       category.setCreatedBy(11);
        Category savedCategory = categoryRepository.save(category);
        return !ObjectUtils.isEmpty(savedCategory);
    }

    @Override
    public List<Category> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();

        return categories;
    }
}
