package com.example.enotes_api.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/cache")
public interface CacheEndpoint {

    @GetMapping("/")
    ResponseEntity<?> getAllCache();

    @GetMapping("/{cache_name}")
    ResponseEntity<?> getCache(@PathVariable String cache_name);

    @DeleteMapping("/remove-cache")
    ResponseEntity<?> removeAllCache();

}
