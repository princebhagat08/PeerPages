package com.example.enotes_api.repository;

import com.example.enotes_api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    public List<Category> findByIsActiveTrue();
}
