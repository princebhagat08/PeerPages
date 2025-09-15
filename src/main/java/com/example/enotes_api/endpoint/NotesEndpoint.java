package com.example.enotes_api.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v1/notes")
public interface NotesEndpoint {

    @PostMapping("/")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> saveNotes(@RequestParam String notes,
                                       @RequestParam(required = false) MultipartFile file) throws Exception;

    @GetMapping("/download/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception;

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> getAllNotes();




    @GetMapping("/user-notes")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllNotesByUser(
            @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "3") Integer pageSize);


    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception;

    @GetMapping("/restore/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;

    @GetMapping("/recycle-bin")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserRecycleBinNotes() throws Exception;

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception;


    @DeleteMapping("/delete-all")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> emptyRecycleBin() throws Exception;

    @GetMapping("/fav/{notesId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> favouriteNotes(@PathVariable Integer notesId) throws  Exception;

    @DeleteMapping("/un-fav-notes/{favNotesId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> unFavouriteNotes(@PathVariable Integer favNotesId) throws  Exception;

    @GetMapping("/fav-notes")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getFavouriteNotes() throws  Exception;

}
