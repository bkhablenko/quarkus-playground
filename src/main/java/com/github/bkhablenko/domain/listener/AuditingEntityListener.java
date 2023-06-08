package com.github.bkhablenko.domain.listener;

import com.github.bkhablenko.domain.model.AbstractEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Setter;

import java.time.Clock;
import java.time.LocalDateTime;

@ApplicationScoped
public class AuditingEntityListener {

    @Setter
    private static Clock clock = Clock.systemUTC();

    @PrePersist
    public void touchForCreate(Object target) {
        if (!(target instanceof AbstractEntity entity)) {
            return;
        }
        final LocalDateTime timestamp = now();
        if (entity.getCreatedDate() == null) {
            entity.setCreatedDate(timestamp);
        }
        entity.setLastModifiedDate(timestamp);
    }

    @PreUpdate
    public void touchForUpdate(Object target) {
        if (!(target instanceof AbstractEntity entity)) {
            return;
        }
        entity.setLastModifiedDate(now());
    }

    private LocalDateTime now() {
        return LocalDateTime.now(clock);
    }
}
