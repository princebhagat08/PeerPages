package com.example.enotes_api.repository;

import com.example.enotes_api.dto.CategoryDto;
import com.example.enotes_api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    List<Category> findByIsActiveTrueAndIsDeletedFalse();

    Optional<Category> findByIdAndIsDeletedFalse(Integer id);

   Boolean existsByName(String name);

    List<Category> findByIsDeletedFalse();
}
