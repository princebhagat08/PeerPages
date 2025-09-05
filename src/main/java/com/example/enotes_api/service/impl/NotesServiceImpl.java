package com.example.enotes_api.service.impl;

import com.example.enotes_api.dto.CategoryDto;
import com.example.enotes_api.dto.NotesDto;
import com.example.enotes_api.entity.Notes;
import com.example.enotes_api.exception.ResourceNotFoundException;
import com.example.enotes_api.repository.CategoryRepository;
import com.example.enotes_api.repository.NotesRepository;
import com.example.enotes_api.service.NotesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
public class NotesServiceImpl implements NotesService {

    @Autowired
    private NotesRepository notesRepository;

   @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;


    @Override
    public Boolean saveNotes(NotesDto notesDto) throws Exception{

        // category validation
        checkCategoryExistence(notesDto.getCategory());

        Notes notes = mapper.map(notesDto, Notes.class);

        Notes saved = notesRepository.save(notes);

        if(!ObjectUtils.isEmpty(saved)){
            return true;
        }

        return false;

    }



    private void checkCategoryExistence(NotesDto.CategoryDto category) throws Exception {
         categoryRepository.findById(category.getId()).orElseThrow(
                 ()->new ResourceNotFoundException("Category not exist"));
    }


    @Override
    public List<NotesDto> getAllNotes() {
        return notesRepository.findAll().stream()
                .map(note->mapper.map(note,NotesDto.class)).toList();
    }
}
