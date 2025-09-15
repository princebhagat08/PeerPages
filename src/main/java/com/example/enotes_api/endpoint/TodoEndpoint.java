package com.example.enotes_api.endpoint;

import com.example.enotes_api.dto.TodoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/todo")
public interface TodoEndpoint {

    @PostMapping("/")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> saveTodo(@RequestBody TodoDto todoDto) throws Exception;


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> getTodo(@PathVariable Integer id) throws  Exception;

    @GetMapping("/list")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> getTodoByUser() throws  Exception;
}
