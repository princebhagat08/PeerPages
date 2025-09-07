package com.example.enotes_api.service;

import com.example.enotes_api.dto.NotesDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NotesService {

    Boolean saveNotes(String notes, MultipartFile file) throws Exception;

    List<NotesDto> getAllNotes();

}
