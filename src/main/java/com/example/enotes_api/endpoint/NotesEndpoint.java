package com.example.enotes_api.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.example.enotes_api.utils.Constants.*;

@RequestMapping("/api/v1/notes")
public interface NotesEndpoint {

    @PostMapping("/")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> saveNotes(@RequestParam String notes,@RequestParam(required = false) MultipartFile file) throws Exception;

    @GetMapping("/download/{id}")
    @PreAuthorize(ROLE_ADMIN_USER)
    ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception;

    @GetMapping("/")
    @PreAuthorize(ROLE_ADMIN)
    ResponseEntity<?> getAllNotes();




    @GetMapping("/user-notes")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getAllNotesByUser(
            @RequestParam(name = "pageNo", defaultValue = DEFAULT_PAGE_NO) Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize);


    @GetMapping("/delete/{id}")
    @PreAuthorize(ROLE_ADMIN_USER)
    ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception;

    @GetMapping("/restore/{id}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;

    @GetMapping("/recycle-bin")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getUserRecycleBinNotes() throws Exception;

    @DeleteMapping("/delete/{id}")
    @PreAuthorize(ROLE_ADMIN_USER)
    ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception;


    @DeleteMapping("/delete-all")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> emptyRecycleBin() throws Exception;

    @GetMapping("/fav/{notesId}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> favouriteNotes(@PathVariable Integer notesId) throws  Exception;

    @DeleteMapping("/un-fav-notes/{favNotesId}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> unFavouriteNotes(@PathVariable Integer favNotesId) throws  Exception;

    @GetMapping("/fav-notes")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getFavouriteNotes() throws  Exception;

    @GetMapping("/search")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> searchNotes( @RequestParam(name = "key",defaultValue = "") String key,
                                   @RequestParam(name = "pageNo",defaultValue = DEFAULT_PAGE_NO) Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize);

}
