package com.example.enotes_api.entity;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseModel {


    private Integer createdBy;


    private Date createdOn;


    private Integer updatedBy;


    private Date updatedOn;


}
