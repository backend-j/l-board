package me.backendj.lboard.model;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {
    private String createdBy;
    private LocalDateTime createdDate;

    private String modifiedBy;
    private LocalDateTime modifiedDate;

}
