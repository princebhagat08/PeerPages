package com.example.enotes_api.service;

import com.example.enotes_api.dto.FavouriteNotesDto;
import com.example.enotes_api.dto.NotesDto;
import com.example.enotes_api.dto.NotesResponse;
import com.example.enotes_api.entity.FileDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface NotesService {

    Boolean saveNotes(String notes, MultipartFile file) throws Exception;

    List<NotesDto> getAllNotes();

    byte[] downloadFile(FileDetails fileDetails) throws Exception;

    FileDetails getFileDetails(Integer id) throws  Exception;

    NotesResponse getAllNotesByUser(Integer userId, Integer pageNo, Integer pageSize);

    void softDeleteNotes(Integer id) throws  Exception;

    void restoreNotes(Integer id) throws Exception;

    List<NotesDto> getUserRecycleBinNotes(Integer userId);

    void hardDeleteNotes(Integer id) throws Exception;

    void emptyRecycleBin(Integer userId) throws  Exception;

    void favouriteNotes(Integer notesId) throws  Exception;

    void unFavouriteNotes(Integer favouriteNotesId) throws  Exception;

    List<FavouriteNotesDto> getUserFavouriteNotes() throws Exception;


}
