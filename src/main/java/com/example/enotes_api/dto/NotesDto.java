package com.example.enotes_api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotesDto {

    private Integer id;

    private String title;

    private String description;

    private CategoryDto category;

    private Integer createdBy;

    private Date createdOn;

    private Integer updatedBy;

    private Date updatedOn;

    private FileDetailsDto file;

    private Boolean isDeleted;

    private LocalDateTime deletedOn;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FileDetailsDto{

        private Integer id;

        private String originalFileName;

        private String displayFileName;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryDto{

        private Integer id;

        private String name;
    }


}
