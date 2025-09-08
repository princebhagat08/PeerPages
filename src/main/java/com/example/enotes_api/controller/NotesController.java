package com.example.enotes_api.controller;

import com.example.enotes_api.dto.FavouriteNotesDto;
import com.example.enotes_api.dto.NotesDto;
import com.example.enotes_api.dto.NotesResponse;
import com.example.enotes_api.entity.FileDetails;
import com.example.enotes_api.service.NotesService;
import com.example.enotes_api.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {

    @Autowired
    private NotesService notesService;

    @PostMapping("/")
    public ResponseEntity<?> saveNotes(@RequestParam String notes,
                                       @RequestParam(required = false) MultipartFile file) throws Exception {
        Boolean saveNotes = notesService.saveNotes(notes,file);

        return saveNotes ? CommonUtil.createBuildResponseMessage("Notes saved success", HttpStatus.CREATED) :
                CommonUtil.createErrorResponseMessage("Notes not saved", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception{

        FileDetails fileDetails = notesService.getFileDetails(id);

        byte[] data = notesService.downloadFile(fileDetails);

        HttpHeaders httpHeaders  = new HttpHeaders();
        String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
        httpHeaders.setContentType(MediaType.parseMediaType(contentType));
        httpHeaders.setContentDispositionFormData("attachment",fileDetails.getOriginalFileName());

        return ResponseEntity.ok().headers(httpHeaders).body(data);
    }


    @GetMapping("/")
    public ResponseEntity<?> getAllNotes(){
        List<NotesDto> allNotes = notesService.getAllNotes();
        if(CollectionUtils.isEmpty(allNotes)){
            return ResponseEntity.noContent().build();
        }

        return CommonUtil.createBuildResponse(allNotes,HttpStatus.OK);
    }

    @GetMapping("/user-notes")
    public ResponseEntity<?> getAllNotesByUser(
            @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "3") Integer pageSize){
        Integer userId = 12;
        NotesResponse allNotes = notesService.getAllNotesByUser(userId,pageNo,pageSize);

        return CommonUtil.createBuildResponse(allNotes,HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception{
        notesService.softDeleteNotes(id);
        return CommonUtil.createBuildResponseMessage("Delete Success", HttpStatus.OK);
    }

    @GetMapping("/restore/{id}")
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception{

        notesService.restoreNotes(id);
        return CommonUtil.createBuildResponseMessage("Restore Success", HttpStatus.OK);
    }

    @GetMapping("/recycle-bin")
    public ResponseEntity<?> getUserRecycleBinNotes() throws Exception{
        Integer userId = 12;
        List<NotesDto> notes = notesService.getUserRecycleBinNotes(userId);

        if (CollectionUtils.isEmpty(notes)){
            return CommonUtil.createBuildResponseMessage("Empty recycle bin",HttpStatus.OK);
        }

        return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception{
        notesService.hardDeleteNotes(id);
        return CommonUtil.createBuildResponseMessage("Delete Success", HttpStatus.OK);
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<?> emptyRecycleBin() throws Exception{
        Integer userId = 12;
        notesService.emptyRecycleBin(userId);
        return CommonUtil.createBuildResponseMessage("Delete Success", HttpStatus.OK);
    }

   @GetMapping("/fav/{notesId}")
    public ResponseEntity<?> favouriteNotes(@PathVariable Integer notesId) throws  Exception{
        notesService.favouriteNotes(notesId);
        return CommonUtil.createBuildResponseMessage("Notes added to favourite",HttpStatus.CREATED);
   }

   @DeleteMapping("/un-fav-notes/{favNotesId}")
   public ResponseEntity<?> unFavouriteNotes(@PathVariable Integer favNotesId) throws  Exception{
        notesService.unFavouriteNotes(favNotesId);
        return CommonUtil.createBuildResponseMessage("Notes remove from favourite",HttpStatus.OK);
   }


    @GetMapping("/fav-notes")
    public ResponseEntity<?> getFavouriteNotes() throws  Exception{
        List<FavouriteNotesDto> userFavouriteNotes = notesService.getUserFavouriteNotes();
        if(CollectionUtils.isEmpty(userFavouriteNotes)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(userFavouriteNotes,HttpStatus.CREATED);
    }



}
