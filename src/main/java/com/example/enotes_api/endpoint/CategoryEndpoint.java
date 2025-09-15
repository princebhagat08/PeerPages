package com.example.enotes_api.endpoint;

import com.example.enotes_api.dto.CategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import static com.example.enotes_api.utils.Constants.ROLE_ADMIN;
import static com.example.enotes_api.utils.Constants.ROLE_ADMIN_USER;

@RequestMapping("/api/v1/category")
public interface CategoryEndpoint {

    @PostMapping("/save")
    @PreAuthorize(ROLE_ADMIN)
    ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto);

    @GetMapping("/")
    @PreAuthorize(ROLE_ADMIN)
    ResponseEntity<?> getAllCategory();

    @GetMapping("/active")
    @PreAuthorize(ROLE_ADMIN_USER)
    ResponseEntity<?> getActiveCategory();

    @GetMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
    ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id) throws Exception;


    @DeleteMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
    ResponseEntity<?> deleteCategory(@PathVariable Integer id);

}
