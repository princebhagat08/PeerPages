package com.example.enotes_api.endpoint;

import com.example.enotes_api.dto.TodoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.example.enotes_api.utils.Constants.ROLE_USER;

@RequestMapping("/api/v1/todo")
public interface TodoEndpoint {

    @PostMapping("/")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> saveTodo(@RequestBody TodoDto todoDto) throws Exception;


    @GetMapping("/{id}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getTodo(@PathVariable Integer id) throws  Exception;

    @GetMapping("/list")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getTodoByUser() throws  Exception;
}
