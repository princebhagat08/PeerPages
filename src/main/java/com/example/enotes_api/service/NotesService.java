package com.example.enotes_api.service;

import com.example.enotes_api.dto.NotesDto;
import com.example.enotes_api.dto.NotesResponse;
import com.example.enotes_api.entity.FileDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NotesService {

    Boolean saveNotes(String notes, MultipartFile file) throws Exception;

    List<NotesDto> getAllNotes();

    byte[] downloadFile(FileDetails fileDetails) throws Exception;

    FileDetails getFileDetails(Integer id) throws  Exception;

    NotesResponse getAllNotesByUser(Integer userId, Integer pageNo, Integer pageSize);
}
