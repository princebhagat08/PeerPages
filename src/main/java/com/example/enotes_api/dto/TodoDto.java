package com.example.enotes_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoDto {

    private Integer id;

    private String title;

    private StatusDto status;

    private Integer createdBy;

    private Date createdOn;

    private Integer updatedBy;

    private Date updatedOn;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StatusDto{
        private Integer id;
        private String name;
    }

}
