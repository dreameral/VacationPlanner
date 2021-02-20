package com.vacationplanner.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
public class BasicEntity {

    @CreationTimestamp
    private Timestamp createdAt;

    @CreationTimestamp
    private Timestamp updatedAt;

    private String createdBy;

    private String updatedBy;

    private boolean isDeleted;

}
