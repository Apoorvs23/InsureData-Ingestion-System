package com.plum.demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@MappedSuperclass
public class BaseEntity implements Serializable {
    @Column(name = "created_at")
    protected LocalDateTime createdAt;
    @Column(name = "updated_at")
    protected LocalDateTime updatedAt;
}
