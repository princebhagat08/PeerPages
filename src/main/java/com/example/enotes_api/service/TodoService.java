package com.example.enotes_api.service;

import com.example.enotes_api.dto.TodoDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TodoService {

    boolean saveTodo(TodoDto todo) throws Exception;

    TodoDto getTodoById(Integer id) throws Exception;

    List<TodoDto> getTodoByUser();

}
