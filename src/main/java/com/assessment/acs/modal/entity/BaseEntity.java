package com.assessment.acs.modal.entity;

import com.assessment.acs.modal.enumeric.EntityState;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {

    @Enumerated(EnumType.STRING)
    @Column(name = "entity_state", nullable = false, length = 20)
    private EntityState entityState = EntityState.ACTIVE;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;

    @Version
    @Column(name = "version", nullable = false)
    private int version = 0;

}
