package com.programmingtechie.productservice.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EnableJpaAuditing
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
        @CreatedDate
        @Column(name = "created_at")
        private Instant created_at = Instant.now();

        @CreatedDate
        @Column(name = "updated_at")
        private Instant updated_at = Instant.now();
}

