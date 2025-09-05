package com.example.enotes_api.service;

import com.example.enotes_api.dto.NotesDto;

import java.util.List;

public interface NotesService {

    public Boolean saveNotes(NotesDto notesDto) throws Exception;

    public List<NotesDto> getAllNotes();

}
