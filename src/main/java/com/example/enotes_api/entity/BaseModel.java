package com.example.enotes_api.entity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseModel {


    private Integer createdBy;

    @CreationTimestamp
    @Column(name = "created_on", updatable = false)
    private Date createdOn;


    private Integer updatedBy;


    private Date updatedOn;



}
