package com.cotede.todolist.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@MappedSuperclass
@Slf4j
@SuperBuilder(toBuilder = true)
public class BaseEntity {


    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

    @PrePersist
    public void prePersist() {
        log.info("Persist entity: " + this.getClass().getSimpleName());
        this.updatedAt = null;
        this.createdBy = getUserName();
    }

    @PreUpdate
    public void preUpdate() {
        log.info("Update entity: " + this.getClass().getSimpleName());
        this.updatedBy = getUserName();
        this.updatedAt = LocalDateTime.now();
    }
    private String getUserName() {
        return createdBy;
    }
}
