package com.example.enotes_api.schedular;


import com.example.enotes_api.entity.Notes;
import com.example.enotes_api.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class NotesSchedular {

    @Autowired
    private NotesRepository notesRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteNotesSchedular(){
        LocalDateTime cutOffDate = LocalDateTime.now().minusDays(7);
        List<Notes> deletdNotes = notesRepository.findByIsDeletedAndDeletedOn(true,cutOffDate);
        notesRepository.deleteAll(deletdNotes);
    }
}
