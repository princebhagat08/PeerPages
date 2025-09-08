package com.example.enotes_api.dto;


import com.example.enotes_api.entity.Notes;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavouriteNotesDto {

    private Integer id;

    private NotesDto notes;

    private Integer userId;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NotesDto{
        private Integer id;
        private String title;
        private String description;
        private CategoryDto category;
    }



}
