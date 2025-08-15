package com.phxnnam.Api_UserManagement.entity;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class AbstractEntity {
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    LocalDate createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    LocalDate updatedAt;
}
