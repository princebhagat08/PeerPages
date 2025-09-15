package com.example.enotes_api.controller;

import com.example.enotes_api.dto.FavouriteNotesDto;
import com.example.enotes_api.dto.NotesDto;
import com.example.enotes_api.dto.NotesResponse;
import com.example.enotes_api.endpoint.NotesEndpoint;
import com.example.enotes_api.entity.FileDetails;
import com.example.enotes_api.service.NotesService;
import com.example.enotes_api.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class NotesController implements NotesEndpoint {

    @Autowired
    private NotesService notesService;

    @Override
    public ResponseEntity<?> saveNotes(@RequestParam String notes,
                                       @RequestParam(required = false) MultipartFile file) throws Exception {
        Boolean saveNotes = notesService.saveNotes(notes,file);

        return saveNotes ? CommonUtil.createBuildResponseMessage("Notes saved success", HttpStatus.CREATED) :
                CommonUtil.createErrorResponseMessage("Notes not saved", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception{

        FileDetails fileDetails = notesService.getFileDetails(id);

        byte[] data = notesService.downloadFile(fileDetails);

        HttpHeaders httpHeaders  = new HttpHeaders();
        String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
        httpHeaders.setContentType(MediaType.parseMediaType(contentType));
        httpHeaders.setContentDispositionFormData("attachment",fileDetails.getOriginalFileName());

        return ResponseEntity.ok().headers(httpHeaders).body(data);
    }


    @Override
    public ResponseEntity<?> getAllNotes(){
        List<NotesDto> allNotes = notesService.getAllNotes();
        if(CollectionUtils.isEmpty(allNotes)){
            return ResponseEntity.noContent().build();
        }

        return CommonUtil.createBuildResponse(allNotes,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllNotesByUser(
            @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "3") Integer pageSize){

        NotesResponse allNotes = notesService.getAllNotesByUser(pageNo,pageSize);

        return CommonUtil.createBuildResponse(allNotes,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception{
        notesService.softDeleteNotes(id);
        return CommonUtil.createBuildResponseMessage("Delete Success", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception{

        notesService.restoreNotes(id);
        return CommonUtil.createBuildResponseMessage("Restore Success", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getUserRecycleBinNotes() throws Exception{

        List<NotesDto> notes = notesService.getUserRecycleBinNotes();

        if (CollectionUtils.isEmpty(notes)){
            return CommonUtil.createBuildResponseMessage("Empty recycle bin",HttpStatus.OK);
        }

        return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception{
        notesService.hardDeleteNotes(id);
        return CommonUtil.createBuildResponseMessage("Delete Success", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> emptyRecycleBin() throws Exception{

        notesService.emptyRecycleBin();
        return CommonUtil.createBuildResponseMessage("Delete Success", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> favouriteNotes(@PathVariable Integer notesId) throws  Exception{
        notesService.favouriteNotes(notesId);
        return CommonUtil.createBuildResponseMessage("Notes added to favourite",HttpStatus.CREATED);
   }

    @Override
   public ResponseEntity<?> unFavouriteNotes(@PathVariable Integer favNotesId) throws  Exception{
        notesService.unFavouriteNotes(favNotesId);
        return CommonUtil.createBuildResponseMessage("Notes remove from favourite",HttpStatus.OK);
   }


    @Override
    public ResponseEntity<?> getFavouriteNotes() throws  Exception{
        List<FavouriteNotesDto> userFavouriteNotes = notesService.getUserFavouriteNotes();
        if(CollectionUtils.isEmpty(userFavouriteNotes)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(userFavouriteNotes,HttpStatus.CREATED);
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> searchNotes(@RequestParam(name = "key",defaultValue = "") String key,
                                         @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                         @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        NotesResponse notes = notesService.getNotesByUserSearch(pageNo, pageSize,key);
        return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
    }



}
