package com.example.enotes_api.service;
import java.util.List;
import com.example.enotes_api.entity.Category;

public interface CategoryService {

    public Boolean saveCategory(Category category);

    public List<Category> getAllCategory();
}
