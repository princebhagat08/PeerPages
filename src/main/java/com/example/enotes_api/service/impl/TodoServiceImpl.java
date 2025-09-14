package com.example.enotes_api.service.impl;

import com.example.enotes_api.dto.TodoDto;
import com.example.enotes_api.entity.Todo;
import com.example.enotes_api.enums.TodoStatus;
import com.example.enotes_api.exception.ResourceNotFoundException;
import com.example.enotes_api.repository.TodoRepository;
import com.example.enotes_api.service.TodoService;
import com.example.enotes_api.utils.CommonUtil;
import com.example.enotes_api.utils.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;


@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private Validation validation;


    @Override
    public boolean saveTodo(TodoDto todoDto) throws Exception{

        //validate todo status
        validation.todoStatusValidation(todoDto);


        Todo todo = mapper.map(todoDto, Todo.class);
        todo.setStatusId(todoDto.getStatus().getId());
        Todo save = todoRepo.save(todo);

        if(!ObjectUtils.isEmpty(save)){
            return true;
        }
        return false;
    }



    @Override
    public TodoDto getTodoById(Integer id) throws  Exception{
        Todo todo = todoRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Invalid Id"));

        TodoDto todoDto = mapper.map(todo, TodoDto.class);
        setStatus(todoDto,todo);
        return todoDto;
    }

    private void setStatus(TodoDto todoDto, Todo todo) {

        for(TodoStatus st:TodoStatus.values()){
            if(todo.getStatusId().equals(st.getId())){
                TodoDto.StatusDto statusDto = new TodoDto.StatusDto();
                statusDto.setId(st.getId());
                statusDto.setName(st.getName());

                todoDto.setStatus(statusDto);
            }
        }
    }


    @Override
    public List<TodoDto> getTodoByUser() {
        Integer userId = CommonUtil.getLoggedInUser().getId();

        List<Todo> todoList = todoRepo.findByCreatedBy(userId);

        List<TodoDto> list = todoList.stream().map((todo) -> mapper.map(todo, TodoDto.class)).toList();

       for(int i = 0; i<list.size(); i++){
           setStatus(list.get(i),todoList.get(i));
       }

        return  list;
    }
}
