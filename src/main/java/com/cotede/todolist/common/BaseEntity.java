package com.cotede.todolist.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@RequiredArgsConstructor
@SuperBuilder(toBuilder = true)
public class BaseEntity {

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @PrePersist
    public void prePersist() {
        this.updatedAt = null;
        this.createdBy = getUserName();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedBy = getUserName();
        this.updatedAt = LocalDateTime.now();
    }

    private String getUserName() {
        return createdBy;

    }

}