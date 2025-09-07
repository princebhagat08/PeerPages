package com.example.enotes_api.controller;

import com.example.enotes_api.dto.NotesDto;
import com.example.enotes_api.service.NotesService;
import com.example.enotes_api.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/")
    public ResponseEntity<?> getAllNotes(){
        List<NotesDto> allNotes = notesService.getAllNotes();
        if(CollectionUtils.isEmpty(allNotes)){
            return ResponseEntity.noContent().build();
        }

        return CommonUtil.createBuildResponse(allNotes,HttpStatus.OK);
    }

}
