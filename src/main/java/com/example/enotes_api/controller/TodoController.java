package com.example.enotes_api.controller;

import com.example.enotes_api.dto.TodoDto;
import com.example.enotes_api.service.TodoService;
import com.example.enotes_api.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping("/")
    public ResponseEntity<?> saveTodo(@RequestBody TodoDto todoDto) throws Exception{
        boolean saveTodo = todoService.saveTodo(todoDto);
        if(saveTodo){
            return CommonUtil.createBuildResponseMessage("Todo Save Success", HttpStatus.CREATED);
        }else {
            return CommonUtil.createErrorResponseMessage("Todo not saved",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTodo(@PathVariable Integer id) throws  Exception{
        TodoDto todoById = todoService.getTodoById(id);
        if(!ObjectUtils.isEmpty(todoById)){
            return CommonUtil.createBuildResponse(todoById, HttpStatus.OK);
        }else {
            return ResponseEntity.noContent().build();
        }
    }


    @GetMapping("/list")
    public ResponseEntity<?> getTodoByUser() throws  Exception{
        List<TodoDto> todoByUser = todoService.getTodoByUser();
        if(!CollectionUtils.isEmpty(todoByUser)){
            return CommonUtil.createBuildResponse(todoByUser, HttpStatus.OK);
        }else {
            return ResponseEntity.noContent().build();
        }
    }





}
